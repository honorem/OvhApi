/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.cloud;

import com.github.cambierr.ovhapi.auth.AccessRules;
import com.github.cambierr.ovhapi.auth.Credential;
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
public class SshKeyTest {
    
    static Project project;
    static Region region;
    static SshKey sshKey;
    
    static String sshKeyId;
    
    public SshKeyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        AccessRules _rules = new AccessRules();
        _rules.addRule("/*");

        Credential credential = Credential.build(Settings.applicationKey, Settings.applicationSecret, Settings.consumerKey).toBlocking().single();

        project = Project.byId(credential, Settings.projectId).toBlocking().single();
        region = Region.byName(project, Settings.defaultRegionName);
        
        sshKey = SshKey.create(project, region, "test", Settings.publicKey).toBlocking().single();
        
        sshKeyId = sshKey.getId();
    }
    
    @AfterClass
    public static void tearDownClass() {
        sshKey.delete().toBlocking().single();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of list method, of class SshKey.
     */
    @Test
    public void testList() {
        System.out.println("list");
        Project _project = project;
        Region _region = region;
        List<SshKey> result = SshKey.list(_project, _region).toList().toBlocking().single();
        assertNotNull(result);
        
    }

    /**
     * Test of byId method, of class SshKey.
     */
    @Test
    public void testById() {
        System.out.println("byId");
        Project _project = project;
        String _id = sshKeyId;
        
        SshKey result = SshKey.byId(_project, _id).toBlocking().single();
        assertEquals(sshKeyId, result.getId());
    }

    /**
     * Test of getId method, of class SshKey.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        SshKey instance = sshKey;
        String expResult = sshKeyId;
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRegion method, of class SshKey.
     */
    @Test
    public void testGetRegion() {
        System.out.println("getRegion");
        SshKey instance = sshKey;
        Region expResult = region;
        Region result = instance.getRegion();
        assertEquals(expResult.getName(), result.getName());
    }

    /**
     * Test of getName method, of class SshKey.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        SshKey instance = sshKey;
        String expResult = "test";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPubKey method, of class SshKey.
     */
    @Test
    public void testGetPubKey() {
        System.out.println("getPubKey");
        SshKey instance = sshKey;
        String expResult = Settings.publicKey;
        String result = instance.getPubKey();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFingerPrint method, of class SshKey.
     */
    @Test
    public void testGetFingerPrint() {
        System.out.println("getFingerPrint");
        SshKey instance = sshKey;
        String result = instance.getFingerPrint();
        
        assertNotNull(result);
    }

    /**
     * Test of isPartial method, of class SshKey.
     */
    @Test
    public void testIsPartial() {
        System.out.println("isPartial");
        SshKey instance = sshKey;
        boolean expResult = false;
        boolean result = instance.isPartial();
        assertEquals(expResult, result);
    }

    /**
     * Test of complete method, of class SshKey.
     */
    @Test
    public void testComplete() {
        System.out.println("complete");
        SshKey instance = sshKey;

        SshKey result = instance.complete().toBlocking().single();
        assertEquals(sshKey, result);
    }

    /**
     * Test of create method, of class SshKey.
     */
    @Test
    public void testCreateAndTestDelete() {
        System.out.println("create");
        Project _project = project;
        Region _region = region;
        String _name = "test2";
        String _key = Settings.publicKey;
        SshKey result = SshKey.create(_project, _region, _name, _key).toBlocking().single();
        assertNotNull(result);

        result.delete().toBlocking().single();
    }

    /**
     * Test of create method, of class SshKey.
     */
    @Test
    public void testCreate() {
        //tested in TestCreateAndTestDelete
    }

    /**
     * Test of delete method, of class SshKey.
     */
    @Test
    public void testDelete() {
        //tested in TestCreateAndTestDelete
    }
    
}
