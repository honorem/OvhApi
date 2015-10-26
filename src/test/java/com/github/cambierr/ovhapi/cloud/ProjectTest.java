/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.cloud;

import com.github.cambierr.ovhapi.auth.AccessRules;
import com.github.cambierr.ovhapi.auth.Credential;
import com.github.cambierr.ovhapi.auth.CredentialRequest;
import com.github.cambierr.ovhapi.auth.CredentialRequestTest;
import com.github.cambierr.ovhapi.cloud.Project.Consumption;
import com.github.cambierr.ovhapi.common.Settings;
import com.github.cambierr.ovhapi.exception.TokenNotLinkedException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ProjectTest {

    static Project project;
    static Credential credential;

    public ProjectTest() {
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
    public void testGetCredentials() {
        System.out.println("getCredentials");
        Project instance = project;

        Credential result = instance.getCredentials();
        assertEquals(Settings.consumerKey, result.getConsumerKey());
    }

    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Project instance = project;
        long expResult = Settings.projectCreationDate;
        long unexpectedResult = 0L;
        long result = instance.getCreationDate();
        assertNotNull(result);
        assertNotSame(unexpectedResult, result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Project instance = project;
        String expResult = Settings.projectStatus;
        String result = instance.getStatus();
        assertNotNull(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Project instance = project;
        String expResult = Settings.projectDescription;
        String result = instance.getDescription();
        assertNotNull(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetId() {
        System.out.println("getId");
        Project instance = project;
        String expResult = Settings.projectId;
        String result = instance.getId();
        assertNotNull(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testById() {
        System.out.println("byId");
        Credential _credentials = credential;
        String _id = Settings.projectId;

        Project result = Project.byId(_credentials, _id).toBlocking().single();
        assertNotNull(result);
    }

    @Test
    public void testList() {
        System.out.println("list");
        Credential _credentials = credential;
        List<Project> result = Project.list(_credentials).toList().toBlocking().single();

        assertNotNull(result);
        assertNotNull(result.get(0));
    }

    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String _description = Settings.projectDescription;
        Project instance = project;
        Project result = instance.setDescription(_description).toBlocking().single();
        assertNotNull(result);
    }

    @Test
    public void testGetConsumption() {
        System.out.println("getConsumption");
        
        long from = System.currentTimeMillis() - 86400000;
        long to = System.currentTimeMillis();
        
        Project instance = project;

        Consumption result = instance.getConsumption(from, to).toBlocking().single();
        assertNotNull(result);

        assertNotNull(result.getServices());

        assertNotNull(result.getTotal().currencyCode);
        assertNotNull(result.getTotal().text);
        assertNotNull(result.getTotal().value);

    }

    @Test
    public void testIsPartial() {
        System.out.println("isPartial");
        Project instance = project;
        boolean expResult = false;
        boolean result = instance.isPartial();
        assertEquals(expResult, result);

        List<Project> projects = Project.list(credential).toList().toBlocking().single();
        expResult = true;
        for (Project project1 : projects) {
            result = project1.isPartial();
            assertEquals(expResult, result);
        }

    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Project instance = project;
        Project expResult = project;
        Project result = instance.update().toBlocking().single();
        assertEquals(expResult, result);
    }

    @Test
    public void testComplete() {
        System.out.println("complete");
        Project instance = project;

        instance.complete().toBlocking().single();
        
        List<Project> projects = Project.list(credential).toList().toBlocking().single();

        projects.stream().forEach((project1) -> {
            project1.complete().toBlocking().single();
        });
        
        
    }

}
