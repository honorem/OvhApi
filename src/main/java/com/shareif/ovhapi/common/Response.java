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
package com.shareif.ovhapi.common;

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
    private String entity;

    protected Response(int _status, String _responseMsg, InputStream _body) {
        status = _status;
        body = _body;
        responseMsg = _responseMsg;
    }

    public InputStream body() {
        return body;
    }

    public String entity() throws IOException {
        if (entity == null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int read;
            byte[] data = new byte[1024];
            while ((read = body().read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }
            buffer.flush();
            entity = new String(buffer.toByteArray());
        }
        return entity;
    }
    
    public JSONObject jsonObject() throws IOException{
        return new JSONObject(entity());
    }
    
    public JSONArray jsonArray() throws IOException{
        return new JSONArray(entity());
    }

    public int responseCode(){
        return status;
    }
    
    public String responseMessage(){
        return responseMsg;
    }
    
}
