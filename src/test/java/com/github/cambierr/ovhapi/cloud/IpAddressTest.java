/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.cloud;

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
public class IpAddressTest {

    public IpAddressTest() {
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
     * Test of getIp method, of class IpAddress.
     */
    @Test
    public void testGetIp() {
        System.out.println("getIp");
        IpAddress instance = new IpAddress("255.255.255.255", IpAddress.Type.PUBLIC);
        String expResult = "255.255.255.255";
        String result = instance.getIp();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class IpAddress.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        IpAddress instance = new IpAddress("255.255.255.255", IpAddress.Type.PUBLIC);
        IpAddress.Type expResult = IpAddress.Type.PUBLIC;
        IpAddress.Type result = instance.getType();
        assertEquals(expResult, result);
        
        IpAddress.Type notExpResult = IpAddress.Type.PRIVATE;
        assertNotSame(notExpResult, result);
        
    }
    
    @Test
    public void testType(){
        System.out.println("testType");
        
        IpAddress.Type expResult = IpAddress.Type.PUBLIC;
        IpAddress.Type result = IpAddress.Type.getType("public");
        
        assertEquals(expResult, result);
        
        assertNull(IpAddress.Type.getType("privat"));
    }

}
