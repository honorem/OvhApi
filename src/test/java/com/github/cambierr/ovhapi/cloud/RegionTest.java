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
public class RegionTest {
    
    static Project project;
    
    public RegionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        AccessRules _rules = new AccessRules();
        _rules.addRule("/*");

        credential = Credential.build(Settings.applicationKey, Settings.applicationSecret, Settings.consumerKey).toBlocking().single();

        project = Project.byId(credential, Settings.projectId).toBlocking().single();
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
    public void testGetName() {
        System.out.println("getName");
        Region instance = Region.byName(project, Settings.defaultRegionName);
        String expResult = Settings.defaultRegionName;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    @Test
    public void testByName() {
        System.out.println("byName");
        Project _project = project;
        String _name = Settings.defaultRegionName;
        Region result = Region.byName(_project, _name);
        
        assertNotNull(result);
    }

    @Test
    public void testList() {
        System.out.println("list");
        Project _project = project;
        List<Region> result = Region.list(_project).toList().toBlocking().single();
        
        assertNotNull(result);
        assertNotNull(result.get(0));
        
    }
    
}
