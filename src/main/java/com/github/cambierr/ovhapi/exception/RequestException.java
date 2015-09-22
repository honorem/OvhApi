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
package com.github.cambierr.ovhapi.exception;

/**
 * Fired when something does wrong with an API call
 *
 * @author cambierr
 */
public class RequestException extends Exception {

    private final int code;
    private final String message;
    private final String body;

    public RequestException(int _code, String _message, String _body) {
        super(_message);
        code = _code;
        body = _body;
        message = _message;
    }

    /**
     * Returns the request status code
     *
     * @return the request status code
     */
    public int code() {
        return code;
    }

    /**
     * Returns the request status title
     *
     * @return the request status title
     */
    public String message() {
        return message;
    }

    /**
     * Returns the request response body
     *
     * @return the request response body
     */
    public String body() {
        return body;
    }
}
