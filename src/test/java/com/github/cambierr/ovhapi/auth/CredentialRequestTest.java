/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.auth;

import com.github.cambierr.ovhapi.common.Settings;
import com.github.cambierr.ovhapi.exception.TokenNotLinkedException;
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
public class CredentialRequestTest {

    static CredentialRequest credentials;
    

    public CredentialRequestTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        AccessRules rules = new AccessRules();
        rules.addRule("/*");
        credentials = CredentialRequest.build(Settings.applicationKey, Settings.applicationSecret, rules, Settings.redirection).toBlocking().single();
        
        try {
            System.out.println("ConsumerKey : "+credentials.getCredential().getConsumerKey());
            System.out.println("ValidationUrl:"+credentials.getValidationUrl());
        } catch (TokenNotLinkedException ex) {
            ex.printStackTrace();
        }
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
    public void testBuild() throws InterruptedException {
        System.out.println("build");
        AccessRules _rules = new AccessRules();
        _rules.addRule("/*");

        CredentialRequest result = CredentialRequest.build(Settings.applicationKey, Settings.applicationSecret, _rules, Settings.redirection).toBlocking().single();
        assertNotNull(result);
    }

    @Test
    public void testGetValidationUrl() {
        System.out.println("getValidationUrl");
        CredentialRequest instance = credentials;
        String result = instance.getValidationUrl();
        
        assertNotNull(result);
        assertTrue(result.contains(Settings.validationUrl));

    }

    @Test
    public void testIsLinked() {
        System.out.println("isLinked");
        CredentialRequest instance = credentials;
        boolean expResult = true;
        boolean result = instance.isLinked();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCredential() throws Exception {
        System.out.println("getCredential");
        CredentialRequest instance = credentials;
        Credential result = instance.getCredential();
        
        assertNotNull(result);
    }

}
