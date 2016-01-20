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
package com.github.cambierr.ovhapi.auth;

import com.github.cambierr.ovhapi.common.Method;
import com.github.cambierr.ovhapi.exception.InvalidConsumerKeyException;
import com.github.cambierr.ovhapi.exception.UnclaimedConsumerKeyException;
import com.mashape.unirest.request.HttpRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import rx.Observable;
import rx.Subscriber;

/**
 *
 * @author cambierr
 */
public class Credential {

    private final String applicationKey;
    private final String applicationSecret;
    private final String consumerKey;

    protected Credential(String _applicationKey, String _applicationSecret, String _consumerKey) {
        applicationKey = _applicationKey;
        applicationSecret = _applicationSecret;
        consumerKey = _consumerKey;
    }

    /**
     * Creates a credential object
     *
     * @param _applicationKey the OVH API application key
     * @param _applicationSecret the OVH API application secret
     * @param _consumerKey the OVH API consumer key
     *
     * @return an Observable Credential
     */
    public static Observable<Credential> build(String _applicationKey, String _applicationSecret, String _consumerKey) {
        return Observable
                .just(new Credential(_applicationKey, _applicationSecret, _consumerKey))
                .flatMap((Credential t) -> Observable.create((Subscriber<? super Credential> t1) -> {
                    try {
                        t.check();
                        t1.onNext(t);
                    } catch (UnclaimedConsumerKeyException | InvalidConsumerKeyException ex) {
                        t1.onError(ex);
                    }
                    t1.onCompleted();
                }));
    }

    /**
     * Signs an API request
     *
     * @param _request the request
     * @param _method the API request method
     * @param _body the request body (if any)
     */
    public void sign(HttpRequest _request, Method _method, String _body) {
        long time = System.currentTimeMillis() / 1000;

        String preHash = this.applicationSecret
                + "+" + this.consumerKey
                + "+" + _method.name()
                + "+" + _request.getUrl()
                + "+" + ((_body == null) ? "" : _body)
                + "+" + time;

        _request.header("X-Ovh-Timestamp", Long.toString(time));
        _request.header("X-Ovh-Signature", "$1$" + toSHA1(preHash));
        _request.header("X-Ovh-Application", this.applicationKey);
        _request.header("X-Ovh-Consumer", this.consumerKey);
    }

    private String toSHA1(String _preHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return bytesToHex(md.digest(_preHash.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String bytesToHex(byte[] _bytes) {
        String result = "";
        for (int i = 0; i < _bytes.length; i++) {
            result += Integer.toString((_bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    /**
     * Checks if this CK has been linked to an user account
     *
     * <p>
     * <strong>Currently, this method isn't implemented yet and will always return</strong></p>
     *
     * @throws com.github.cambierr.ovhapi.exception.UnclaimedConsumerKeyException if this CK hasn't been linked to an user account
     * @throws com.github.cambierr.ovhapi.exception.InvalidConsumerKeyException if this CK does not exists
     */
    public void check() throws UnclaimedConsumerKeyException, InvalidConsumerKeyException {
        /**
         * @pending: waiting for ovh
         */
    }

    public String getConsumerKey() {
        return this.consumerKey;
    }

}
