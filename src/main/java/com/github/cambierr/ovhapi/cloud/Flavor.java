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
public class Flavor {

    private final String id;
    private int disk;
    private final Region region;
    private String name;
    private int vcpus;
    private String type;
    private String osType;
    private int ram;

    private boolean partial = false;
    private final Project project;

    private Flavor(Project _project, String _id, int _disk, Region _region, String _name, int _vcpus, String _type, String _osType, int _ram) {
        id = _id;
        disk = _disk;
        region = _region;
        name = _name;
        vcpus = _vcpus;
        type = _type;
        osType = _osType;
        ram = _ram;
        project = _project;
    }

    protected static Flavor byId(Project _project, String _id, Region _region) {
        Flavor temp = new Flavor(_project, _id, 0, _region, null, 0, null, null, 0);
        temp.partial = true;
        return temp;
    }

    public Observable<Flavor> complete() {
        if (!partial) {
            return Observable.just(this);
        }

        return byId(project, id)
                .map((Flavor t1) -> {
                    this.disk = t1.disk;
                    this.name = t1.name;
                    this.vcpus = t1.vcpus;
                    this.type = t1.type;
                    this.osType = t1.osType;
                    this.ram = t1.ram;
                    this.partial = false;
                    return this;
                });
    }

    public boolean isPartial() {
        return partial;
    }

    public static Observable<Flavor> list(Project _project, Region _region) {
        return new RequestBuilder("/cloud/project/" + _project + "/flavor?region=" + _region.getName(), Method.GET, _project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                        return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.body()));
                    }
                    final JSONArray flavors = t1.jsonArray();
                    return Observable.range(0, flavors.length()).map((Integer t2) -> new Flavor(_project, flavors.getJSONObject(t2).getString("id"), flavors.getJSONObject(t2).getInt("disk"), Region.byName(_project, flavors.getJSONObject(t2).getString("region")), flavors.getJSONObject(t2).getString("name"), flavors.getJSONObject(t2).getInt("vcpus"), flavors.getJSONObject(t2).getString("type"), flavors.getJSONObject(t2).getString("osType"), flavors.getJSONObject(t2).getInt("ram")));
                });
    }

    public static Observable<Flavor> byId(Project _project, String _id) {
        return new RequestBuilder("/cloud/project/" + _project.getId() + "/flavor/" + _id, Method.GET, _project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                        return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.body()));
                    }
                    JSONObject flavor = t1.jsonObject();
                    return Observable.just(
                            new Flavor(_project,
                                    flavor.getString("id"),
                                    flavor.getInt("disk"),
                                    Region.byName(_project, flavor.getString("region")),
                                    flavor.getString("name"),
                                    flavor.getInt("vcpus"),
                                    flavor.getString("type"),
                                    flavor.getString("osType"),
                                    flavor.getInt("ram")
                            )
                    );
                });
    }

    public String getId() {
        return id;
    }

    public int getDisk() {
        if(partial){
            throw new PartialObjectException();
        }
        return disk;
    }

    public Region getRegion() {
        return region;
    }

    public String getName() {
        if(partial){
            throw new PartialObjectException();
        }
        return name;
    }

    public int getVcpus() {
        if(partial){
            throw new PartialObjectException();
        }
        return vcpus;
    }

    public String getType() {
        if(partial){
            throw new PartialObjectException();
        }
        return type;
    }

    public String getOsType() {
        if(partial){
            throw new PartialObjectException();
        }
        return osType;
    }

    public int getRam() {
        if(partial){
            throw new PartialObjectException();
        }
        return ram;
    }

}
