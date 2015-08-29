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
import com.github.cambierr.ovhapi.common.OvhApi;
import com.github.cambierr.ovhapi.common.RequestBuilder;
import com.github.cambierr.ovhapi.common.Response;
import com.github.cambierr.ovhapi.exception.RequestException;
import java.io.IOException;
import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import rx.Observable;

/**
 *
 * @author cambierr
 */
public class Instance {

    private Status status;
    private final Region region;
    private String name;
    private final Image image;
    private final long creationDate;
    private final Flavor flavor;
    private final SshKey sshKey;
    private final String id;

    private final Project project;

    private Instance(Project _project, Status _status, Region _region, String _name, Image _image, long _creationDate, Flavor _flavor, SshKey _sshKey, String _id) {
        project = _project;
        status = _status;
        region = _region;
        name = _name;
        image = _image;
        creationDate = _creationDate;
        flavor = _flavor;
        sshKey = _sshKey;
        id = _id;
    }

    public static Observable<Instance> list(Project _project, Region _region) {
        return new RequestBuilder("/cloud/project/" + _project.getId() + "/instance?region=" + ((_region == null) ? "" : _region.getName()), Method.GET, _project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        final JSONArray instances = t1.jsonArray();
                        return Observable.range(0, instances.length())
                        .flatMap((Integer t2) -> {
                            JSONObject instance = instances.getJSONObject(t2);
                            try {
                                return Observable.just(new Instance(_project, Status.valueOf(instance.getString("status")), Region.byName(_project, instance.getString("region")), instance.getString("name"), Image.byId(_project, instance.getString("imageId"), Region.byName(_project, instance.getString("region"))), OvhApi.dateToTime(instance.getString("created")), Flavor.byId(_project, instance.getString("flavorId"), Region.byName(_project, instance.getString("region"))), SshKey.byIdPartial(_project, instance.getString("sshKeyId")), instance.getString("id")));
                            } catch (Exception ex) {
                                return Observable.error(ex);
                            }
                        });
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    public static Observable<Instance> byId(Project _project, String _id) {
        return new RequestBuilder("/cloud/project/" + _project.getId() + "/instance/" + _id, Method.GET, _project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        final JSONObject instance = t1.jsonObject();
                        return Observable.just(new Instance(_project, Status.valueOf(instance.getString("status")), Region.byName(_project, instance.getString("region")), instance.getString("name"), Image.byId(_project, instance.getString("imageId"), Region.byName(_project, instance.getString("region"))), OvhApi.dateToTime(instance.getString("created")), Flavor.byId(_project, instance.getString("flavorId"), Region.byName(_project, instance.getString("region"))), SshKey.byIdPartial(_project, instance.getString("sshKeyId")), instance.getString("id")));
                    } catch (IOException | ParseException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    public Status getStatus() {
        return status;
    }

    public Region getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public SshKey getSshKey() {
        return sshKey;
    }

    public String getId() {
        return id;
    }

    public enum Status {

        ACTIVE,
        BUILDING,
        DELETED,
        ERROR,
        HARD_REBOOT,
        PASSWORD,
        PAUSED,
        REBOOT,
        REBUILD,
        RESCUED,
        RESIZED,
        REVERT_RESIZE,
        SOFT_DELETED,
        STOPPED,
        SUSPENDED,
        UNKNOWN,
        VERIFY_RESIZE,
        MIGRATING,
        RESIZE,
        BUILD,
        SHUTOFF,
        RESCUE,
        SHELVED,
        SHELVED_OFFLOADED
    }

    public Observable<Instance> kill() {
        return new RequestBuilder("/cloud/project/" + project.getId() + "/instance/" + id, Method.DELETE, project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        return Observable.just(this);
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    public static Observable<Instance> create(Project _project, Flavor _flavor, Image _image, Region _region, SshKey _key, String _name) {
        return new RequestBuilder("/cloud/project/" + _project.getId() + "/instance", Method.POST, _project.getCredentials())
                .body(new JSONObject()
                        .put("flavorId", _flavor.getId())
                        .put("imageId", _image.getId())
                        .put("name", _name)
                        .put("region", _region.getName())
                        .put("sshKeyId", (_key == null) ? "" : _key.getId())
                        .toString()
                )
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        final JSONObject instance = t1.jsonObject();
                        return Observable.just(new Instance(_project, Status.valueOf(instance.getString("status")), Region.byName(_project, instance.getString("region")), instance.getString("name"), Image.byId(_project, instance.getString("imageId"), Region.byName(_project, instance.getString("region"))), OvhApi.dateToTime(instance.getString("created")), Flavor.byId(_project, instance.getString("flavorId"), Region.byName(_project, instance.getString("region"))), SshKey.byIdPartial(_project, instance.getString("sshKeyId")), instance.getString("id")));
                    } catch (IOException | ParseException ex) {
                        return Observable.error(ex);
                    }
                });
    }
}
