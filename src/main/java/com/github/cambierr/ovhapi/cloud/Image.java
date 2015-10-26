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
import com.github.cambierr.ovhapi.exception.PartialObjectException;
import com.github.cambierr.ovhapi.exception.RequestException;
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

    private String visibility;
    private long creationDate;
    private String status;
    private final Region region;
    private String name;
    private String type;
    private final String id;
    private int minDisk;

    private boolean partial = false;
    private final Project project;

    private Image(Project _project, String _id, String _visibility, long _creationDate, String _status, Region _region, String _name, String _type, int _minDisk) {
        visibility = _visibility;
        creationDate = _creationDate;
        status = _status;
        region = _region;
        name = _name;
        type = _type;
        id = _id;
        minDisk = _minDisk;
        project = _project;
    }

    /**
     * For internal use only
     *
     * @param _project the project to request from
     * @param _id the image id
     * @param _region the region of this image
     *
     * @return an Image object
     */
    protected static Image byId(Project _project, String _id, Region _region) {
        Image temp = new Image(_project, _id, null, -1, null, _region, null, null, -1);
        temp.partial = true;
        return temp;
    }

    /**
     * Completes a partial Image object
     *
     * @return the Observable completed Image object
     */
    public Observable<Image> complete() {
        if (!partial) {
            return Observable.just(this);
        }

        return byId(project, id)
                .map((Image t1) -> {
                    this.visibility = t1.visibility;
                    this.creationDate = t1.creationDate;
                    this.status = t1.status;
                    this.name = t1.name;
                    this.type = t1.type;
                    this.minDisk = t1.minDisk;
                    this.partial = false;
                    return this;
                });
    }

    /**
     * Checks if this Image is partially loaded or not
     *
     * @return true if partially loaded, or false
     */
    public boolean isPartial() {
        return partial;
    }

    /**
     * Lists all images availables in a project and in a region (of provided)
     *
     * @param _project The project to list images of
     * @param _region The region to list images from (null = all regions)
     * @param _flavor The flavor to be compatible with (null = no compatibility requirement)
     * @param _osType The OS type of the image (null = no requirement)
     *
     * @return Zero to several observable Image objects
     */
    public static Observable<Image> list(Project _project, Region _region, Flavor _flavor, String _osType) {
        String args = "";
        if (_region != null) {
            args += "region=" + _region.getName() + "&";
        }
        if (_flavor != null) {
            args += "flavorType=" + _flavor.getId() + "&";
        }
        if (_osType != null) {
            args += "osType=" + _osType + "&";
        }

        return new RequestBuilder("/cloud/project/" + _project.getId() + "/image?" + args, Method.GET, _project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                        return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.body()));
                    }
                    final JSONArray images = t1.jsonArray();
                    return Observable
                    .range(0, images.length())
                    .flatMap((Integer t2) -> Observable.create((Subscriber<? super Image> t3) -> {
                        try {
                            Image image = new Image(_project,
                                    images.getJSONObject(t2).getString("id"),
                                    images.getJSONObject(t2).getString("visibility"),
                                    OvhApi.dateToTime(images.getJSONObject(t2).getString("creationDate")),
                                    images.getJSONObject(t2).getString("status"),
                                    Region.byName(_project, images.getJSONObject(t2).getString("region")),
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
                });
    }

    /**
     * Loads an Image by its id
     *
     * @param _project the project to load the Image from
     * @param _id the Image id
     *
     * @return an observable Image object
     */
    public static Observable<Image> byId(Project _project, String _id) {
        return new RequestBuilder("/cloud/project/" + _project.getId() + "/image/" + _id, Method.GET, _project.getCredentials())
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            System.out.println(t1.responseMessage());
                            System.out.println(t1.body());
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.body()));
                        }
                        final JSONObject image = t1.jsonObject();

                        return Observable.just(new Image(_project,
                                        image.getString("id"),
                                        image.getString("visibility"),
                                        OvhApi.dateToTime(image.getString("creationDate")),
                                        image.getString("status"),
                                        Region.byName(_project, image.getString("region")),
                                        image.getString("name"),
                                        image.getString("type"),
                                        image.getInt("minDisk")
                                ));

                    } catch (ParseException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    /**
     * Returns the visibility of this image
     *
     * @return the visibility of this image
     *
     * @throws PartialObjectException if this object is partially loaded
     */
    public String getVisibility() {
        if (partial) {
            throw new PartialObjectException();
        }
        return visibility;
    }

    /**
     * Returns the creation date of this image
     *
     * @return the creation date of this image
     *
     * @throws PartialObjectException if this object is partially loaded
     */
    public long getCreationDate() {
        if (partial) {
            throw new PartialObjectException();
        }
        return creationDate;
    }

    /**
     * Returns the status of this image
     *
     * @return the status of this image
     *
     * @throws PartialObjectException if this object is partially loaded
     */
    public String getStatus() {
        if (partial) {
            throw new PartialObjectException();
        }
        return status;
    }

    /**
     * Returns the region of this image
     *
     * @return the region of this image
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Returns the bame of this image
     *
     * @return the name of this image
     *
     * @throws PartialObjectException if this object is partially loaded
     */
    public String getName() {
        if (partial) {
            throw new PartialObjectException();
        }
        return name;
    }

    /**
     * Returns the type of this image
     *
     * @return the type of this image
     *
     * @throws PartialObjectException if this object is partially loaded
     */
    public String getType() {
        if (partial) {
            throw new PartialObjectException();
        }
        return type;
    }

    /**
     * Returns the id of this image
     *
     * @return the id of this image
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the disk space requirement of this image
     *
     * @return the disk space requirement of this image
     *
     * @throws PartialObjectException if this object is partially loaded
     */
    public int getMinDisk() {
        if (partial) {
            throw new PartialObjectException();
        }
        return minDisk;
    }

}
