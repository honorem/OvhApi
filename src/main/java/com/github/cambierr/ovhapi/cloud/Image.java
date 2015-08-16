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
import com.github.cambierr.ovhapi.common.OvhApi;
import com.github.cambierr.ovhapi.common.RequestBuilder;
import com.github.cambierr.ovhapi.common.Response;
import com.github.cambierr.ovhapi.exception.RequestException;
import java.io.IOException;
import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import rx.Observable;
import rx.Subscriber;

/**
 *
 * @author cambierr
 */
public class Image {

    private final String visibility;
    private final long creationDate;
    private final String status;
    private final Region region;
    private final String name;
    private final String type;
    private final String id;
    private final int minDisk;

    private final Credential credentials;

    private Image(Credential _credentials, String _id, String _visibility, long _creationDate, String _status, Region _region, String _name, String _type, int _minDisk) {
        credentials = _credentials;
        visibility = _visibility;
        creationDate = _creationDate;
        status = _status;
        region = _region;
        name = _name;
        type = _type;
        id = _id;
        minDisk = _minDisk;
    }

    public static Observable<Image> list(Credential _credentials, Project _project) {
        return list(_credentials, _project.getId(), null, null, null);
    }

    public static Observable<Image> list(Credential _credentials, String _project) {
        return list(_credentials, _project, null, null, null);
    }

    public static Observable<Image> list(Credential _credentials, Project _project, Region _region) {
        return list(_credentials, _project.getId(), _region.getName(), null, null);
    }

    public static Observable<Image> list(Credential _credentials, Project _project, String _region) {
        return list(_credentials, _project.getId(), _region, null, null);
    }

    public static Observable<Image> list(Credential _credentials, Project _project, Region _region, String _flavor) {
        return list(_credentials, _project.getId(), _region.getName(), _flavor, null);
    }

    public static Observable<Image> list(Credential _credentials, Project _project, Region _region, Flavor _flavor) {
        return list(_credentials, _project.getId(), _region.getName(), _flavor.getOsType(), null);
    }

    public static Observable<Image> list(Credential _credentials, Project _project, Region _region, Flavor _flavor, String _osType) {
        return list(_credentials, _project.getId(), _region.getName(), _flavor.getType(), _osType);
    }

    public static Observable<Image> list(Credential _credentials, String _project, String _region, String _flavor, String _osType) {
        String args = "";
        if (_region != null) {
            args += "region=" + _region + "&";
        }
        if (_flavor != null) {
            args += "flavorType=" + _flavor + "&";
        }
        if (_osType != null) {
            args += "osType=" + _osType + "&";
        }

        return new RequestBuilder("/cloud/project/" + _project + "/image?" + args, Method.GET, _credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        final JSONArray images = t1.jsonArray();
                        return Observable
                        .range(0, images.length())
                        .flatMap((Integer t2) -> Observable.create((Subscriber<? super Image> t3) -> {
                            try {
                                Image image = new Image(_credentials,
                                        images.getJSONObject(t2).getString("id"),
                                        images.getJSONObject(t2).getString("visibility"),
                                        OvhApi.dateToTime(images.getJSONObject(t2).getString("creationDate")),
                                        images.getJSONObject(t2).getString("status"),
                                        Region.byName(images.getJSONObject(t2).getString("region")),
                                        images.getJSONObject(t2).getString("name"),
                                        images.getJSONObject(t2).getString("type"),
                                        images.getJSONObject(t2).getInt("minDisk")
                                );
                                t3.onNext(image);
                            } catch (ParseException ex) {
                                t3.onError(ex);
                            }
                            t3.onCompleted();
                        }));

                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }
    
    public static Observable<Image> byId(Credential _credentials, Project _project, String _id){
        return byId(_credentials, _project.getId(), _id);
    }
    
    public static Observable<Image> byId(Credential _credentials, String _project, String _id){
        return new RequestBuilder("/cloud/project/" + _project + "/image/" + _id, Method.GET, _credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        final JSONObject image = t1.jsonObject();
                        
                        return Observable.just(new Image(_credentials,
                                        image.getString("id"),
                                        image.getString("visibility"),
                                        OvhApi.dateToTime(image.getString("creationDate")),
                                        image.getString("status"),
                                        Region.byName(image.getString("region")),
                                        image.getString("name"),
                                        image.getString("type"),
                                        image.getInt("minDisk")
                                ));

                    } catch (IOException | ParseException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    public String getVisibility() {
        return visibility;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public String getStatus() {
        return status;
    }

    public Region getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public int getMinDisk() {
        return minDisk;
    }

}
