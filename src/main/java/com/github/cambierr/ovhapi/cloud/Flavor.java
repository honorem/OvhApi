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

import com.github.cambierr.ovhapi.auth.Credential;
import com.github.cambierr.ovhapi.common.Method;
import com.github.cambierr.ovhapi.common.RequestBuilder;
import com.github.cambierr.ovhapi.common.Response;
import com.github.cambierr.ovhapi.exception.RequestException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import rx.Observable;

/**
 *
 * @author cambierr
 */
public class Flavor {

    private final String id;
    private final int disk;
    private final Region region;
    private final String name;
    private final int vcpus;
    private final String type;
    private final String osType;
    private final int ram;

    private final Credential credentials;

    private Flavor(Credential _credentials, String _id, int _disk, Region _region, String _name, int _vcpus, String _type, String _osType, int _ram) {
        id = _id;
        disk = _disk;
        region = _region;
        name = _name;
        vcpus = _vcpus;
        type = _type;
        osType = _osType;
        ram = _ram;
        credentials = _credentials;
    }

    public static Observable<Flavor> list(Credential _credentials, Project _project) {
        return list(_credentials, _project.getId(), null);
    }

    public static Observable<Flavor> list(Credential _credentials, String _project) {
        return list(_credentials, _project, null);
    }

    public static Observable<Flavor> list(Credential _credentials, Project _project, Region _region) {
        return list(_credentials, _project.getId(), _region.getName());
    }

    public static Observable<Flavor> list(Credential _credentials, String _project, String _region) {
        return new RequestBuilder((_region == null) ? "/cloud/project/" + _project + "/flavor" : "/cloud/project/" + _project + "/flavor?region=" + _region, Method.GET, _credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        final JSONArray flavors = t1.jsonArray();
                        return Observable.range(0, flavors.length()).map((Integer t2) -> new Flavor(_credentials, flavors.getJSONObject(t2).getString("id"), flavors.getJSONObject(t2).getInt("disk"), Region.byName(flavors.getJSONObject(t2).getString("region")), flavors.getJSONObject(t2).getString("name"), flavors.getJSONObject(t2).getInt("vcpus"), flavors.getJSONObject(t2).getString("type"), flavors.getJSONObject(t2).getString("osType"), flavors.getJSONObject(t2).getInt("ram")));
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    public static Observable<Flavor> byId(Credential _credentials, Project _project, String _id) {
        return byId(_credentials, _project.getId(), _id);
    }

    public static Observable<Flavor> byId(Credential _credentials, String _project, String _id) {
        return new RequestBuilder("/cloud/project/" + _project + "/flavor/" + _id, Method.GET, _credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        JSONObject flavor = t1.jsonObject();
                        return Observable.just(
                                new Flavor(_credentials, flavor.getString("id"),
                                        flavor.getInt("disk"),
                                        Region.byName(flavor.getString("region")),
                                        flavor.getString("name"),
                                        flavor.getInt("vcpus"),
                                        flavor.getString("type"),
                                        flavor.getString("osType"),
                                        flavor.getInt("ram")
                                )
                        );
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    public String getId() {
        return id;
    }

    public int getDisk() {
        return disk;
    }

    public Region getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public int getVcpus() {
        return vcpus;
    }

    public String getType() {
        return type;
    }

    public String getOsType() {
        return osType;
    }

    public int getRam() {
        return ram;
    }

}
