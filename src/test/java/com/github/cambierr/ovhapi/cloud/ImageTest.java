/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.cloud;

import com.github.cambierr.ovhapi.auth.AccessRules;
import com.github.cambierr.ovhapi.auth.Credential;
import static com.github.cambierr.ovhapi.cloud.ProjectTest.credential;
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
public class ImageTest {
    
    static Project project;
    static Region region;
    static Flavor flavor;
    static Image image;
    
    public ImageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        AccessRules _rules = new AccessRules();
        _rules.addRule("/*");

        credential = Credential.build(Settings.applicationKey, Settings.applicationSecret, Settings.consumerKey).toBlocking().single();

        project = Project.byId(credential, Settings.projectId).toBlocking().single();
        
        region = Region.byName(project, Settings.defaultRegionName);
        
        flavor = Flavor.byId(project, Settings.defaultFlavorId).toBlocking().single();
        
        image = Image.byId(project, Settings.defaultImageId).toBlocking().single();
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
     * Test of complete method, of class Image.
     */
    @Test
    public void testComplete() {
        System.out.println("complete");
        Image instance = image;
        Image expResult = image;
        Image result = instance.complete().toBlocking().single();
        assertEquals(expResult, result);
    }

    /**
     * Test of isPartial method, of class Image.
     */
    @Test
    public void testIsPartial() {
        System.out.println("isPartial");
        Image instance = image;
        boolean expResult = false;
        boolean result = instance.isPartial();
        assertEquals(expResult, result);

    }

    /**
     * Test of list method, of class Image.
     */
    @Test
    public void testList() {
        System.out.println("list");
        Project _project = project;
        Region _region = region;
        Flavor _flavor = flavor;
        String _osType = "linux";
        List<Image> result = Image.list(_project, _region, _flavor, _osType).toList().toBlocking().single();
       
        
        assertNotNull(result);
        
        
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of byId method, of class Image.
     */
    @Test
    public void testById() {
        System.out.println("byId");
        Project _project = project;
        String _id = Settings.defaultImageId;

        Image result = Image.byId(_project, _id).toBlocking().single();
        assertNotNull(result);

        
    }

    /**
     * Test of getVisibility method, of class Image.
     */
    @Test
    public void testGetVisibility() {
        System.out.println("getVisibility");
        Image instance = image;
        String expResult = "public";
        String result = instance.getVisibility();
        assertEquals(expResult, result);

        
    }

    /**
     * Test of getCreationDate method, of class Image.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Image instance = image;
        long expResult = 1435054721000L;
        long result = instance.getCreationDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatus method, of class Image.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Image instance = image;
        String expResult = "active";
        String result = instance.getStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRegion method, of class Image.
     */
    @Test
    public void testGetRegion() {
        System.out.println("getRegion");
        Image instance = image;
        Region expResult = region;
        Region result = instance.getRegion();
        assertEquals(expResult.getName(), result.getName());

    }

    /**
     * Test of getName method, of class Image.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Image instance = image;
        String expResult = "Ubuntu 12.04";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class Image.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Image instance = image;
        String expResult = "linux";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getId method, of class Image.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Image instance = image;
        String expResult = Settings.defaultImageId;
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinDisk method, of class Image.
     */
    @Test
    public void testGetMinDisk() {
        System.out.println("getMinDisk");
        Image instance = image;
        int expResult = 0;
        int result = instance.getMinDisk();
        assertEquals(expResult, result);
    }

   
    
}
