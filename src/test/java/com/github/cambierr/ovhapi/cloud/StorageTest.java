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
import com.github.cambierr.ovhapi.exception.PartialObjectException;
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
        credential = Credential.build(Settings.applicationKey, Settings.applicationSecret, Settings.consumerKey).toBlocking().single();

        project = Project.byId(credential, Settings.projectId).toBlocking().single();

        storage = Storage.create(project, Region.byName(project, Settings.defaultRegionName), Settings.defaultStorageName).toBlocking().single();

        Settings.defaultStorageId = storage.getId();
        
    }

    @AfterClass
    public static void tearDownClass() {
        storage.delete().toBlocking().single();
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
        assertEquals(result.getClass(), Storage.class);

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
        Storage instance = storage;
        long expResult = Settings.defaultStorageStoredBytes;
        long result = instance.getStoredBytes();
        assertEquals(expResult, result);

    }

    /**
     * Test of getId method, of class Storage.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Storage instance = storage;
        String expResult = Settings.defaultStorageId;
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStoredObjects method, of class Storage.
     */
    @Test
    public void testGetStoredObjects() {
        System.out.println("getStoredObjects");
        Storage instance = storage;
        long expResult = Settings.defaultStorageStoredObjects;
        long result = instance.getStoredObjects();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStaticUrl method, of class Storage.
     */
    @Test
    public void testGetStaticUrl() {
        System.out.println("getStaticUrl");
        Storage instance = storage;
        String expResult = Settings.defaultStorageStaticUrl;
        String result = instance.getStaticUrl();
        assertEquals(expResult, result);

    }

    /**
     * Test of isPublic method, of class Storage.
     */
    @Test
    public void testIsPublic() {
        System.out.println("isPublic");
        Storage instance = storage;
        boolean expResult = Settings.defaultStorageIsPublic;
        boolean result = instance.isPublic();
        assertEquals(expResult, result);
    }

    /**
     * Test of isPartial method, of class Storage.
     */
    @Test
    public void testIsPartial() {
        System.out.println("isPartial");
        Storage instance = storage;
        boolean expResult = true;
        boolean result = instance.isPartial();
        assertEquals(expResult, result);

        boolean expResult2 = true;
        boolean result2 = Storage.list(project).toList().toBlocking().single().get(0).isPartial();

        assertEquals(expResult2, result2);
        
        instance.complete().toBlocking().single();
        
        boolean expResult3 = false;
        boolean result3 = instance.isPartial();
        
        assertEquals(expResult3, result3);
    }

    /**
     * Test of update method, of class Storage.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Storage instance = storage;

        Storage result = instance.update().toBlocking().single();
        assertNotNull(result);
    }

    /**
     * Test of complete method, of class Storage.
     */
    @Test
    public void testComplete() {
        System.out.println("complete");
        Storage instance = storage;
        Storage result = instance.complete().toBlocking().single();
        assertNotNull(result);

        Storage instance2 = Storage.list(project).toList().toBlocking().single().get(0);
        try {
            assertNull(instance2.getStaticUrl());
        }catch(Exception ex){
            if(!(ex instanceof PartialObjectException)){
                fail("Should have thrown PartialObjectException");
            }
        }
        instance2.complete().toBlocking().single();
        assertEquals(instance2.getStaticUrl(), Settings.defaultStorageStaticUrl);

    }

    /**
     * Test of delete method, of class Storage.
     */
    @Test
    public void testCreateAndDelete() {
        System.out.println("create and delete");
        
        // tested while setup and teardown
        
    }

    /**
     * Test of cors method, of class Storage.
     */
    @Test
    public void testCors() {
        System.out.println("cors");
        String _cors = "https://example.com";
        Storage instance = storage;

        Storage result = instance.cors(_cors).toBlocking().single();
        assertNotNull(result);

        
        
    }

    /**
     * Test of delete method, of class Storage.
     */
    @Test
    public void testDelete() {
        //tested in testCreateAndDelete
    }

    /**
     * Test of create method, of class Storage.
     */
    @Test
    public void testCreate() {
        //tested in testCreateAndDelete
    }

}
