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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author cambierr
 */
public class OvhApi {

    public final static String API_VERSION = "1.0";
    public final static String API_ENDPOINT = "eu.api.ovh.com";
    private static OvhApi instance = null;
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXX");

    private OvhApi() {
        System.setProperty("https.protocols", "TLSv1");
    }

    protected static OvhApi getInstance() {
        if (instance == null) {
            instance = new OvhApi();
        }
        return instance;
    }

    protected HttpsURLConnection getBase(String _path, Method _method) throws MalformedURLException, ProtocolException, IOException {
        HttpsURLConnection link = (HttpsURLConnection) new URL("https://" + API_ENDPOINT + "/" + API_VERSION + _path).openConnection();
        link.setRequestMethod(_method.name());
        link.setRequestProperty("Accept", "application/json");
        return link;
    }
    
    public static long dateToTime(String _date) throws ParseException{
        return dateFormat.parse(_date).getTime();
    }
    
    public static String timeToDate(long _time){
        return dateFormat.format(new Date(_time));
    }
}
