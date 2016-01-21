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
public class OvhApiTest {
    
    public OvhApiTest() {
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
     * Test of dateToTime method, of class OvhApi.
     */
    @Test
    public void testDateToTime() throws Exception {
        System.out.println("dateToTime");
        String _date = "1970-01-01T01:00:00+01:00";
        long expResult = 0L;
        long result = OvhApi.dateToTime(_date);
        assertEquals(expResult, result);

    }

    /**
     * Test of timeToDate method, of class OvhApi.
     */
    @Test
    public void testTimeToDate() {
        System.out.println("timeToDate");
        long _time = 0L;
        String expResult = "1970-01-01T01:00:00+01:00";
        String result = OvhApi.timeToDate(_time);
        assertEquals(expResult, result);

    }
    
}
