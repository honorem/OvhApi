/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.cloud;

import com.github.cambierr.ovhapi.auth.Credential;
import static com.github.cambierr.ovhapi.cloud.InstanceTest.project;
import static com.github.cambierr.ovhapi.cloud.ProjectTest.credential;
import com.github.cambierr.ovhapi.common.OvhApi;
import com.github.cambierr.ovhapi.common.Settings;
import java.text.ParseException;
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
public class SnapshotTest {
    
    static Project project;
    static Region region;
    static Flavor flavor;
    static Snapshot snapshot;
    
    public SnapshotTest() {
        credential = Credential.build(Settings.applicationKey, Settings.applicationSecret, Settings.consumerKey).toBlocking().single();

        project = Project.byId(credential, Settings.projectId).toBlocking().single();
        region = Region.byName(project, Settings.defaultRegionName);
        flavor = Flavor.byId(project, Settings.defaultFlavorId).toBlocking().single();
        
        snapshot = Snapshot.byId(project, Settings.defaultSnapshotId).toBlocking().single();
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
        Snapshot instance = snapshot;

        Snapshot result = instance.complete().toBlocking().single();
        assertNotNull(result);

    }

    /**
     * Test of isPartial method, of class Snapshot.
     */
    @Test
    public void testIsPartial() {
        System.out.println("isPartial");
        Snapshot instance = snapshot;
        boolean expResult = false;
        boolean result = instance.isPartial();
        assertEquals(expResult, result);
    }

    /**
     * Test of list method, of class Snapshot.
     */
    @Test
    public void testList() {
        System.out.println("list");
        Project _project = project;
        Region _region = region;
        Flavor _flavor = flavor;

        List<Snapshot> result = Snapshot.list(_project, _region, _flavor).toList().toBlocking().single();
        assertNotNull(result);
        
        assertNotNull(result.get(0));
        assertEquals(result.get(0).getClass(), Snapshot.class);
    }

    /**
     * Test of byId method, of class Snapshot.
     */
    @Test
    public void testById() {
        System.out.println("byId");
        Project _project = project;
        String _id = Settings.defaultSnapshotId;

        Snapshot result = Snapshot.byId(_project, _id).toBlocking().single();
        assertNotNull(result);

    }

    /**
     * Test of getVisibility method, of class Snapshot.
     */
    @Test
    public void testGetVisibility() {
        System.out.println("getVisibility");
        Snapshot instance = snapshot;
        String expResult = Settings.defaultSnapshotVisibility;
        String result = instance.getVisibility();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCreationDate method, of class Snapshot.
     */
    @Test
    public void testGetCreationDate() throws ParseException {
        System.out.println("getCreationDate");
        Snapshot instance = snapshot;
        long expResult = OvhApi.dateToTime("2016-01-21T08:04:43Z");
        long result = instance.getCreationDate();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStatus method, of class Snapshot.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Snapshot instance = snapshot;
        String expResult = "active";
        String result = instance.getStatus();
        assertEquals(expResult, result);

    }

    /**
     * Test of getRegion method, of class Snapshot.
     */
    @Test
    public void testGetRegion() {
        System.out.println("getRegion");
        Snapshot instance = snapshot;
        Region expResult = Region.byName(project, "GRA1");
        Region result = instance.getRegion();
        assertEquals(expResult.getName(), result.getName());

    }

    /**
     * Test of getName method, of class Snapshot.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Snapshot instance = snapshot;
        String expResult = "Test_Snapchot";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getType method, of class Snapshot.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Snapshot instance = snapshot;
        String expResult = "linux";
        String result = instance.getType();
        assertEquals(expResult, result);

    }

    /**
     * Test of getId method, of class Snapshot.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Snapshot instance = snapshot;
        String expResult = Settings.defaultSnapshotId;
        String result = instance.getId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getMinDisk method, of class Snapshot.
     */
    @Test
    public void testGetMinDisk() {
        System.out.println("getMinDisk");
        Snapshot instance = snapshot;
        int expResult = 10;
        int result = instance.getMinDisk();
        assertEquals(expResult, result);

    }

    /**
     * Test of delete method, of class Snapshot.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        
        //don't test it or snapshot will be deleted
    }
    
}
