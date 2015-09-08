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

import com.github.cambierr.ovhapi.cloud.Region;
import com.github.cambierr.ovhapi.common.Method;
import com.github.cambierr.ovhapi.common.RequestBuilder;
import com.github.cambierr.ovhapi.common.Response;
import com.github.cambierr.ovhapi.exception.RequestException;
import com.github.cambierr.ovhapi.exception.TokenNotLinkedException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import rx.Observable;
import rx.functions.Func1;

/**
 *
 * @author cambierr
 */
public class CredentialRequest {

    private final String applicationKey;
    private final String applicationSecret;
    private final String consumerKey;
    private final String validationUrl;
    private boolean linked;

    public static Observable<CredentialRequest> build(String _applicationKey, String _applicationSecret, AccessRules _rules, String _redirection) {
        return new RequestBuilder("/auth/credential", Method.POST, _applicationKey)
                .body(new JSONObject().put("redirection", _redirection).put("accessRules", _rules.toJson()).toString())
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        JSONObject token = t1.jsonObject();

                        return Observable.just(new CredentialRequest(_applicationKey, _applicationSecret, token.getString("consumerKey"), token.getString("validationUrl")));
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    private CredentialRequest(String _applicationKey, String _applicationSecret, String _consumerKey, String _validationUrl) {
        applicationKey = _applicationKey;
        applicationSecret = _applicationSecret;
        consumerKey = _consumerKey;
        linked = false;
        validationUrl = _validationUrl;
    }

    public String getValidationUrl() {
        return validationUrl;
    }

    public boolean isLinked() {
        if (linked) {
            return true;
        }
        /**
         * @pending: waiting for ovh
         */
        linked = true;
        
        return linked;
    }

    public Credential getCredential() throws TokenNotLinkedException {
        if (!isLinked()) {
            throw new TokenNotLinkedException();
        }
        return new Credential(applicationKey, applicationSecret, consumerKey);
    }
}
