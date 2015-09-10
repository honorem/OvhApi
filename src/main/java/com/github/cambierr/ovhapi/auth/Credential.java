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

import com.github.cambierr.ovhapi.exception.InvalidConsumerKeyException;
import com.github.cambierr.ovhapi.exception.UnclaimedConsumerKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HttpsURLConnection;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

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

    public void sign(HttpsURLConnection _link, String _body) throws NoSuchAlgorithmException {
        long time = System.currentTimeMillis() / 1000;

        String preHash = this.applicationSecret
                + "+" + this.consumerKey
                + "+" + _link.getRequestMethod()
                + "+" + _link.getURL()
                + "+" + ((_body == null) ? "" : _body)
                + "+" + time;

        _link.addRequestProperty("X-Ovh-Timestamp", Long.toString(time));
        _link.addRequestProperty("X-Ovh-Signature", "$1$"+toSHA1(preHash));
        _link.addRequestProperty("X-Ovh-Application", this.applicationKey);
        _link.addRequestProperty("X-Ovh-Consumer", this.consumerKey);
    }

    private String toSHA1(String _preHash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return bytesToHex(md.digest(_preHash.getBytes()));
    }

    private String bytesToHex(byte[] _bytes) {
        String result = "";
        for (int i = 0; i < _bytes.length; i++) {
            result += Integer.toString((_bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public void check() throws UnclaimedConsumerKeyException, InvalidConsumerKeyException {
        /**
         * @pending: waiting for ovh
         */
    }
    
    public String getConsumerKey(){
        return this.consumerKey;
    }

}
