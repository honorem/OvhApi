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
package com.shareif.ovhapi.cloud;

import com.shareif.ovhapi.auth.Credential;
import com.shareif.ovhapi.common.Method;
import com.shareif.ovhapi.common.RequestBuilder;
import com.shareif.ovhapi.common.Response;
import com.shareif.ovhapi.exception.RequestException;
import java.io.IOException;
import org.json.JSONArray;
import rx.Observable;

/**
 *
 * @author cambierr
 */
public class Region {

    private final String name;

    private Region(String _name) {
        this.name = _name;
    }

    protected static Region byName(String _name) {
        return new Region(_name);
    }

    public static Observable<Region> byProject(Credential _credentials, Project _project) {
        return byProject(_credentials, _project.getId());
    }

    public static Observable<Region> byProject(Credential _credentials, String _project) {
        return new RequestBuilder("/cloud/project/" + _project + "/region", Method.GET, _credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        final JSONArray regions = t1.jsonArray();
                        return Observable.range(0, regions.length())
                        .map((Integer t2) -> new Region(regions.getString(t2)));

                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });

    }

    public String getName() {
        return name;
    }

}
