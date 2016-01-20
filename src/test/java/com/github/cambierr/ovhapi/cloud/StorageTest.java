/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.cloud;

import com.github.cambierr.ovhapi.auth.AccessRules;
import com.github.cambierr.ovhapi.auth.Credential;
import static com.github.cambierr.ovhapi.cloud.ProjectTest.credential;
import static com.github.cambierr.ovhapi.cloud.RegionTest.project;
import com.github.cambierr.ovhapi.common.Settings;
import java.util.List;
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
public class StorageTest {
    
    static Project project;
    static Storage storage;
    
    public StorageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        AccessRules _rules = new AccessRules();
        _rules.addRule("/*");

        credential = Credential.build(Settings.applicationKey, Settings.applicationSecret, Settings.consumerKey).toBlocking().single();

        project = Project.byId(credential, Settings.projectId).toBlocking().single();
        
        storage = Storage.byId(project, Settings.defaultStorageId).toBlocking().single();
        
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
     * Test of list method, of class Storage.
     */
    @Test
    public void testList() {
        System.out.println("list");
        Project _project = project;
        List<Storage> result = Storage.list(_project).toList().toBlocking().single();
        assertNotNull(result);
        
        assertNotNull(result.get(0));
        assertEquals(result.get(0).getClass(), Storage.class);
    }

    /**
     * Test of byId method, of class Storage.
     */
    @Test
    public void testById() {
        System.out.println("byId");
        Project _project = project;
        String _id = Settings.defaultStorageId;

        Storage result = Storage.byId(_project, _id).toBlocking().single();
        assertNotNull(result);

        
    }

    /**
     * Test of getRegion method, of class Storage.
     */
    @Test
    public void testGetRegion() {
        System.out.println("getRegion");
        Storage instance = storage;
        String expResult = Settings.defaultStorageRegion;
        Region result = instance.getRegion();
        assertEquals(expResult, result.getName());

    }

    /**
     * Test of getName method, of class Storage.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Storage instance = storage;
        String expResult = Settings.defaultStorageName;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStoredBytes method, of class Storage.
     */
    @Test
    public void testGetStoredBytes() {
        System.out.println("getStoredBytes");
        Storage instance = null;
        long expResult = 0L;
        long result = instance.getStoredBytes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Storage.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Storage instance = null;
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStoredObjects method, of class Storage.
     */
    @Test
    public void testGetStoredObjects() {
        System.out.println("getStoredObjects");
        Storage instance = null;
        long expResult = 0L;
        long result = instance.getStoredObjects();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStaticUrl method, of class Storage.
     */
    @Test
    public void testGetStaticUrl() {
        System.out.println("getStaticUrl");
        Storage instance = null;
        String expResult = "";
        String result = instance.getStaticUrl();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPublic method, of class Storage.
     */
    @Test
    public void testIsPublic() {
        System.out.println("isPublic");
        Storage instance = null;
        boolean expResult = false;
        boolean result = instance.isPublic();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPartial method, of class Storage.
     */
    @Test
    public void testIsPartial() {
        System.out.println("isPartial");
        Storage instance = null;
        boolean expResult = false;
        boolean result = instance.isPartial();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Storage.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Storage instance = null;
        Observable<Storage> expResult = null;
        Observable<Storage> result = instance.update();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of complete method, of class Storage.
     */
    @Test
    public void testComplete() {
        System.out.println("complete");
        Storage instance = null;
        Observable<Storage> expResult = null;
        Observable<Storage> result = instance.complete();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class Storage.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Storage instance = null;
        Observable<Storage> expResult = null;
        Observable<Storage> result = instance.delete();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cors method, of class Storage.
     */
    @Test
    public void testCors() {
        System.out.println("cors");
        String _cors = "";
        Storage instance = null;
        Observable<Storage> expResult = null;
        Observable<Storage> result = instance.cors(_cors);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
