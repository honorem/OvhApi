/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ResponseTest {

    public ResponseTest() {
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
    public void testBody() {
        System.out.println("body");
        String body = "{\"message\":\"Success\"}";

        Response instance;
        try {
            instance = new Response(200, "OK", new ByteArrayInputStream(body.getBytes()));
            
            String Result = instance.body();
            
            assertEquals(body, Result);
        } catch (IOException ex) {
            fail("Could not read body");
        }

    }

    @Test
    public void testEntity() throws Exception {
        System.out.println("entity");
        String body = "{\"message\":\"Success\"}";

        Response instance = new Response(200, "OK", new ByteArrayInputStream(body.getBytes()));

        byte[] result = instance.entity();
        assertArrayEquals(body.getBytes(), result);
    }

    @Test
    public void testJsonObject() throws Exception {
        System.out.println("jsonObject");
        String body = "{\"message\":\"Success\"}";

        Response instance = new Response(200, "OK", new ByteArrayInputStream(body.getBytes()));
        JSONObject result = instance.jsonObject();

        assertEquals(body, result.toString());

    }

    @Test
    public void testJsonArray() throws Exception {
        System.out.println("jsonArray");
        String body = "[1,2,3]";

        Response instance = new Response(200, "OK", new ByteArrayInputStream(body.getBytes()));
        JSONArray result = instance.jsonArray();
        assertEquals(body, result.toString());

    }

    @Test
    public void testResponseCode() {
        try {
            System.out.println("responseCode");
            String body = "[1,2,3]";
            int expResult = 200;
            Response instance = new Response(expResult, "OK", new ByteArrayInputStream(body.getBytes()));
            
            int result = instance.responseCode();
            assertEquals(expResult, result);
        } catch (IOException ex) {
            fail("Could not read body");
        }

    }

    @Test
    public void testResponseMessage() {
        try {
            System.out.println("responseMessage");
            String body = "[1,2,3]";
            String expResult = "OK";
            Response instance = new Response(200, expResult, new ByteArrayInputStream(body.getBytes()));
            
            String result = instance.responseMessage();
            assertEquals(expResult, result);
        } catch (IOException ex) {
            fail("Could not read body");
        }

    }

}
