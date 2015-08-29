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
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import rx.Observable;

/**
 *
 * @author cambierr
 */
public class Project {

    private boolean unleash;
    private final long creationDate;
    private final String status;
    private String description;
    private final String id;
    private double balance = Double.MIN_VALUE;
    private Consumption consumption = null;

    private final Credential credentials;

    private Project(Credential _credentials, String _id, String _status, long _creationDate, boolean _unleash, String _description) {
        this.unleash = _unleash;
        this.creationDate = _creationDate;
        this.status = _status;
        this.description = _description;
        this.id = _id;
        this.credentials = _credentials;
    }

    public boolean isUnleashed() {
        return unleash;
    }
    
    public Credential getCredentials(){
        return credentials;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public static Observable<Project> byId(Credential _credentials, String _id) {
        return new RequestBuilder("/cloud/project/" + _id, Method.GET, _credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        JSONObject project = t1.jsonObject();
                        Project p = new Project(_credentials,
                                project.getString("project_id"),
                                project.getString("status"),
                                OvhApi.dateToTime(project.getString("creationDate")),
                                project.getBoolean("unleash"),
                                project.getString("description")
                        );
                        return Observable.just(p);
                    } catch (IOException | ParseException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    public static Observable<Project> list(Credential _credentials) {
        return new RequestBuilder("/cloud/project", Method.GET, _credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        return Observable.from(t1.jsonObject().keySet());
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                })
                .flatMap((String t1) -> byId(_credentials, t1));
    }

    private Project _setDescription(String _description) {
        this.description = _description;
        return this;
    }

    public Observable<Project> setDescription(String _description) {
        return new RequestBuilder("/cloud/project/" + this.id, Method.PUT, credentials)
                .body(new JSONObject().put("description", _description).toString())
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        return Observable.just(_setDescription(_description));
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    private Project _unleash() {
        unleash = true;
        return this;
    }

    public Observable<Project> unleash() {
        if (isUnleashed()) {
            return Observable.just(this);
        }
        return new RequestBuilder("/cloud/project/" + this.id + "/unleash", Method.POST, credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        return Observable.just(_unleash());
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    private double _setBalance(double _balance) {
        balance = _balance;
        return _balance;
    }

    public Observable<Double> getBalance(boolean _cached) {
        if (_cached && balance != Double.MIN_VALUE) {
            return Observable.just(balance);
        }
        return new RequestBuilder("/cloud/project/" + this.id + "/balance", Method.GET, credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        JSONObject output = t1.jsonObject();
                        return Observable.just(_setBalance(output.getDouble("currentTotal")));
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
    }

    private Consumption _setConsumption(Consumption _consumption) {
        consumption = _consumption;
        return _consumption;
    }

    public Observable<Consumption> getConsumption(boolean _cached) {
        if (_cached && consumption != null) {
            return Observable.just(consumption);
        }
        new RequestBuilder("/cloud/project/" + this.id + "/consumption", Method.GET, credentials)
                .build()
                .flatMap((Response t1) -> {
                    try {
                        if (t1.responseCode() < 200 || t1.responseCode() >= 300) {
                            return Observable.error(new RequestException(t1.responseCode(), t1.responseMessage(), t1.entity()));
                        }
                        JSONObject output = t1.jsonObject();
                        return Observable.just(_setConsumption(new Consumption(output)));
                    } catch (IOException ex) {
                        return Observable.error(ex);
                    }
                });
        return null;
    }

    public class Consumption {

        private final List<Service> services;
        private final Cost total;

        private Consumption(JSONObject _json) {
            services = new ArrayList<>();

            for (String key : _json.keySet()) {
                services.add(new Service(key, _json.getJSONObject(key)));
            }
            total = new Cost(_json.getJSONObject("total").getString("currencyCode"),
                        _json.getJSONObject("total").getString("text"),
                        _json.getJSONObject("total").getDouble("value"));
        }

        public List<Service> getServices() {
            return services;
        }
        
        public Cost getTotal() {
                return total;
            }

        public class Service {
            private final String name;
            private final Cost total;

            private Service(String _key, JSONObject _json) {
                name = _key;
                total = new Cost(_json.getJSONObject("total").getString("currencyCode"),
                        _json.getJSONObject("total").getString("text"),
                        _json.getJSONObject("total").getDouble("value"));
            }

            public Cost getTotal() {
                return total;
            }

            public String getName() {
                return name;
            }

            public class Item {

                public final JSONObject details;
                
                private Item(JSONObject _json) {
                    details = _json;
                }

            }

        }

        public class Cost {

            public final String currencyCode;
            public final String text;
            public final double value;

            private Cost(String _currencyCode, String _text, double _value) {
                currencyCode = _currencyCode;
                text = _text;
                value = _value;
            }

        }
    }

    
}
