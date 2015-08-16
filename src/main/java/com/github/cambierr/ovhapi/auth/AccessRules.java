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
package com.github.cambierr.ovhapi.auth;

import com.github.cambierr.ovhapi.common.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cambierr
 */
public class AccessRules {
    
    private final List<Rule> rules;
    
    public AccessRules(){
        rules = new ArrayList<>();
    }
    
    public AccessRules addRule(String _path, Method _method){
        rules.add(new Rule(_path, _method));
        return this;
    }
    
    public AccessRules addRule(String _path){
        for(Method m:Method.values()){
            addRule(_path, m);
        }
        return this;
    }
    
    public JSONArray toJson(){
        JSONArray output = new JSONArray();
        
        rules.stream().forEach((r) -> {
            output.put(r.toJson());
        });
        
        return output;
    }
    
    
    
    private class Rule{
        
        private final String path;
        private final Method method;
        
        private Rule(String _path, Method _method){
            path = _path;
            method = _method;
        }
        
        private JSONObject toJson(){
            return new JSONObject().put("method", method.name()).put("path", path);
        }
        
    }
}
