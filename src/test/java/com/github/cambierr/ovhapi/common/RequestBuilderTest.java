/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rx.Observable;

/**
 *
 * @author honorem
 */
public class RequestBuilderTest {

    public RequestBuilderTest() {
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
        String _body = "{\"message\":\"Success\"}";
        RequestBuilder instance;
        try {
            instance = new RequestBuilder("/cloud", Method.GET, Settings.applicationKey);
            instance.body(_body);
            fail("Should not be able to add body when method is set to GET.");
        } catch (IllegalArgumentException ex) {  
            RequestBuilder instance2 = new RequestBuilder("/cloud", Method.POST, Settings.applicationKey);
            instance2.body(_body).build().toBlocking().single();
        }

    }

    @Test
    public void testBuild() {
        System.out.println("build");
        RequestBuilder instance = new RequestBuilder("/cloud", Method.GET, Settings.applicationKey);

        instance.build();

    }

}
