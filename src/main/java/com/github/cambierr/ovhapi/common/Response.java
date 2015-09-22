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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cambierr
 */
public class Response {

    private final int status;
    private final String responseMsg;
    private final InputStream body;
    private final byte[] entity;

    /**
     * For internal use only
     *
     * @param _status the response status
     * @param _responseMsg the response message
     * @param _body the response body
     *
     * @throws IOException in case of I/O error
     */
    protected Response(int _status, String _responseMsg, InputStream _body) throws IOException {
        status = _status;
        body = _body;
        responseMsg = _responseMsg;

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read;
        byte[] data = new byte[1024];
        while ((read = body.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, read);
        }
        buffer.flush();
        entity = buffer.toByteArray();
    }

    /**
     * Returns the response body of the request, as a String
     *
     * @return the response body of the request, as a String
     */
    public String body() {
        return new String(entity());
    }

    /**
     * Returns the response body of the request, as a byte array
     *
     * @return the response body of the request, as a byte array
     */
    public byte[] entity() {
        return entity;
    }

    /**
     * Returns the response body of the request, as a JSON object
     *
     * @return the response body of the request, as a JSON object
     *
     * @throws JSONException if JSON is not valid
     */
    public JSONObject jsonObject() {
        return new JSONObject(body());
    }

    /**
     * Returns the response body of the request, as a JSON array
     *
     * @return the response body of the request, as a JSON array
     *
     * @throws JSONException if JSON is not valid
     */
    public JSONArray jsonArray() {
        return new JSONArray(body());
    }

    /**
     * Returns the response code of the request
     *
     * @return the response code of the request
     */
    public int responseCode() {
        return status;
    }

    /**
     * Returns the response message of the request
     *
     * @return the response message of the request
     */
    public String responseMessage() {
        return responseMsg;
    }

}
