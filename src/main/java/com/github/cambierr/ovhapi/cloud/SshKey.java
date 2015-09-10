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
package com.github.cambierr.ovhapi.cloud;

import com.github.cambierr.ovhapi.common.Method;
import com.github.cambierr.ovhapi.common.RequestBuilder;
import com.github.cambierr.ovhapi.common.Response;
import com.github.cambierr.ovhapi.exception.PartialObjectException;
import com.github.cambierr.ovhapi.exception.RequestException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import rx.Observable;

/**
 *
 * @author cambierr
 */
public class SshKey {

    private final String id;
    private Region region;
    private String name;
    private String pubKey;
    private String fingerPrint;

    private boolean partial = false;
    private final Project project;

    private SshKey(Project _project, String _id, Region _region, String _name, String _pubKey, String _fingerPrint) {
        project = _project;
        id = _id;
        region = _region;
        name = _name;
        pubKey = _pubKey;
        fingerPrint = _fingerPrint;
    }

    protected static SshKey byIdPartial(Project _project, String _id) {
        SshKey temp = new SshKey(_project, _id, null, null, null, null);
        temp.partial = true;
        return temp;
    }

    public static Observable<SshKey> list(Project _project, Region _region) {
        String args = "";
        if (_region != null) {
            args += "region=" + _region.getName() + "&";
        }

        return new RequestBuilder("/cloud/project/" + _project.getId() + "/sshkey?" + args, Method.GET, _project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                        return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.body()));
                    }
                    final JSONArray keys = t1.jsonArray();
                    return Observable
                    .range(0, keys.length())
                    .map((Integer t2) -> {
                        JSONObject key = keys.getJSONObject(t2);
                        return new SshKey(_project, key.getString("id"), Region.byName(_project, key.getJSONArray("regions").getString(0)), key.getString("name"), key.getString("publicKey"), null);
                    });
                });
    }

    public static Observable<SshKey> byId(Project _project, String _id) {
        return new RequestBuilder("/cloud/project/" + _project + "/sshkey/" + _id, Method.GET, _project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                        return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.body()));
                    }
                    final JSONObject key = t1.jsonObject();
                    return Observable.just(new SshKey(_project, key.getString("id"), Region.byName(_project, key.getJSONArray("regions").getString(0)), key.getString("name"), key.getString("publicKey"), key.getString("fingerPrint")));
                });
    }

    public String getId() {
        return id;
    }

    public Region getRegion() {
        return region;
    }

    public String getName() {
        if (partial) {
            throw new PartialObjectException();
        }
        return name;
    }

    public String getPubKey() {
        if (partial) {
            throw new PartialObjectException();
        }
        return pubKey;
    }

    public String getFingerPrint() {
        if (fingerPrint == null) {
            throw new PartialObjectException();
        }
        return fingerPrint;
    }

    public boolean isPartial() {
        return partial;
    }

    public Observable<SshKey> complete() {
        if (!partial) {
            return Observable.just(this);
        }

        return byId(project, id)
                .map((SshKey t1) -> {
                    this.fingerPrint = t1.fingerPrint;
                    this.name = t1.name;
                    this.pubKey = t1.pubKey;
                    this.partial = false;
                    return this;
                });
    }

}
