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
import rx.Observable;

/**
 *
 * @author honorem
 */
public class SnapshotTest {
    
    public SnapshotTest() {
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
     * Test of complete method, of class Snapshot.
     */
    @Test
    public void testComplete() {
        System.out.println("complete");
        Snapshot instance = null;
        Observable<Snapshot> expResult = null;
        Observable<Snapshot> result = instance.complete();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPartial method, of class Snapshot.
     */
    @Test
    public void testIsPartial() {
        System.out.println("isPartial");
        Snapshot instance = null;
        boolean expResult = false;
        boolean result = instance.isPartial();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of list method, of class Snapshot.
     */
    @Test
    public void testList() {
        System.out.println("list");
        Project _project = null;
        Region _region = null;
        Flavor _flavor = null;
        Observable<Snapshot> expResult = null;
        Observable<Snapshot> result = Snapshot.list(_project, _region, _flavor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of byId method, of class Snapshot.
     */
    @Test
    public void testById() {
        System.out.println("byId");
        Project _project = null;
        String _id = "";
        Observable<Snapshot> expResult = null;
        Observable<Snapshot> result = Snapshot.byId(_project, _id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVisibility method, of class Snapshot.
     */
    @Test
    public void testGetVisibility() {
        System.out.println("getVisibility");
        Snapshot instance = null;
        String expResult = "";
        String result = instance.getVisibility();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreationDate method, of class Snapshot.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Snapshot instance = null;
        long expResult = 0L;
        long result = instance.getCreationDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatus method, of class Snapshot.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Snapshot instance = null;
        String expResult = "";
        String result = instance.getStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegion method, of class Snapshot.
     */
    @Test
    public void testGetRegion() {
        System.out.println("getRegion");
        Snapshot instance = null;
        Region expResult = null;
        Region result = instance.getRegion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Snapshot.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Snapshot instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class Snapshot.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Snapshot instance = null;
        String expResult = "";
        String result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Snapshot.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Snapshot instance = null;
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinDisk method, of class Snapshot.
     */
    @Test
    public void testGetMinDisk() {
        System.out.println("getMinDisk");
        Snapshot instance = null;
        int expResult = 0;
        int result = instance.getMinDisk();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class Snapshot.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Snapshot instance = null;
        Observable<Snapshot> expResult = null;
        Observable<Snapshot> result = instance.delete();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
