/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.cloud;

/**
 *
 * @author honorem
 */
public class IpAddress {

    String ip;
    Type type;

    public IpAddress(String _ip, Type _type) {
        ip = _ip;
        type = _type;
    }

    public String getIp() {
        return ip;
    }

    public Type getType() {
        return type;
    }

    public enum Type {

        PUBLIC("public"),
        PRIVATE("private");

        String type;

        Type(String _type) {
            type = _type;
        }
        
        public static Type getType(String _type) {
            for (Type t : Type.values()) {
                if (t.type.equals(_type)) {
                    return t;
                }
            }
            return null;
        }
    }

}
