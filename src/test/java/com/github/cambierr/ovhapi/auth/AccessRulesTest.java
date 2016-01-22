/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.auth;

import com.github.cambierr.ovhapi.common.Method;
import com.github.cambierr.ovhapi.common.Settings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author honorem
 */
public class AccessRulesTest {

    public AccessRulesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddRule_String_Method() {
        System.out.println("addRule");
        String _path = "/cloud/*";
        Method _method = Method.GET;
        AccessRules instance = new AccessRules();

        JSONArray expResult = new JSONArray();
        JSONObject rule = new JSONObject();
        rule.put("path", _path);
        rule.put("method", _method);
        expResult.put(rule);

        AccessRules result = instance.addRule(_path, _method);

        assertEquals(expResult.toString(), result.toJson().toString());
    }

    @Test
    public void testAddRule_String() {
        System.out.println("addRule");
        String _path = "/cloud/*";
        AccessRules instance = new AccessRules();

        JSONArray expResult = new JSONArray();
        JSONObject ruleGet = new JSONObject();
        ruleGet.put("path", _path);
        ruleGet.put("method", Method.GET);
        JSONObject rulePut = new JSONObject();
        rulePut.put("path", _path);
        rulePut.put("method", Method.PUT);
        JSONObject rulePost = new JSONObject();
        rulePost.put("path", _path);
        rulePost.put("method", Method.POST);
        JSONObject ruleDelete = new JSONObject();
        ruleDelete.put("path", _path);
        ruleDelete.put("method", Method.DELETE);
        
        
        expResult.put(ruleGet);
        expResult.put(rulePut);
        expResult.put(rulePost);
        expResult.put(ruleDelete);

        AccessRules result = instance.addRule(_path);
        
        assertEquals("Check for order of rules in jsonArray", expResult.toString(), result.toJson().toString());
    }

    @Test
    public void testToJson() {
        System.out.println("toJson");
        AccessRules instance = new AccessRules();
        JSONArray expResult = new JSONArray();
        JSONArray result = instance.toJson();
        
        assertEquals(expResult.toString(), result.toString());
        

    }

}
