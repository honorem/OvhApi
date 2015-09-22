/*
 * The MIT License
 *
 * Copyright 2015 cambierr.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.cambierr.ovhapi.common;

import com.github.cambierr.ovhapi.auth.Credential;
import javax.net.ssl.HttpsURLConnection;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 *
 * @author cambierr
 */
public class RequestBuilder {

    private final String path;
    private final Method method;
    private String body;
    private static final String userAgent = "Java " + System.getProperty("java.version") + " | com.github.cambierr.ovhapi wrapper (https://github.com/cambierr/OvhApi)";
    private final Credential credentials;
    private final String applicationKey;

    /**
     * Creates a RequestBuilder for CredentialRequest
     *
     * @param _path the path of the request
     * @param _method the method of the request
     * @param _applicatioKey the OVH API application key
     */
    public RequestBuilder(String _path, Method _method, String _applicatioKey) {
        path = _path;
        method = _method;
        credentials = null;
        applicationKey = _applicatioKey;
    }

    /**
     * Creates a RequestBuilder for API calls
     *
     * @param _path the path of the request
     * @param _method the method of the request
     * @param _credentials the Credentials to be used in the request
     */
    public RequestBuilder(String _path, Method _method, Credential _credentials) {
        path = _path;
        method = _method;
        credentials = _credentials;
        applicationKey = null;
    }

    /**
     * Appends a body to the request (only for PUT and POST requests)
     *
     * @param _body the body to be appended
     *
     * @return the updated RequestBuilder
     *
     * @throws IllegalArgumentException if called on a request that doesn't supports bodys
     */
    public RequestBuilder body(String _body) {
        if (!this.method.equals(Method.POST) && !this.method.equals(Method.PUT)) {
            throw new IllegalArgumentException("Body should only be appended to POST or PUT request");
        }
        body = _body;
        return this;
    }

    /**
     * Builds and execute the request, returning a response
     * @return an observable Resposne object
     */
    public Observable<Response> build() {
        return Observable.create((Subscriber<? super Response> t1) -> {
            try {
                HttpsURLConnection link = OvhApi.getInstance().getBase(path, method);
                if (credentials != null) {
                    credentials.sign(link, body);
                } else if (applicationKey != null) {
                    link.addRequestProperty("X-Ovh-Application", this.applicationKey);
                }
                link.setDoOutput(true);
                link.addRequestProperty("User-Agent", userAgent);
                if (body != null) {
                    link.addRequestProperty("Content-type", "application/json");
                    link.getOutputStream().write(body.getBytes());
                }
                t1.onNext(new Response(
                        link.getResponseCode(),
                        link.getResponseMessage(),
                        (link.getErrorStream() == null) ? link.getInputStream() : link.getErrorStream())
                );
                link.disconnect();

            } catch (Throwable t) {
                t1.onError(t);
            }
            t1.onCompleted();
        }).subscribeOn(Schedulers.io());
    }

}
