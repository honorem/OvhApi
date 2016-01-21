/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.common;

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
public class MethodTest {
    
    public MethodTest() {
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

    /**
     * Test of values method, of class Method.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        Method[] result = Method.values();
        assertEquals(result.length, 4);
    }

    /**
     * Test of valueOf method, of class Method.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "GET";
        Method expResult = Method.GET;
        Method result = Method.valueOf(name);
        assertEquals(expResult, result);
    }
    
}
