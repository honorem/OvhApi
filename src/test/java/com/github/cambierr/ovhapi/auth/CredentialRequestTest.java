/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.auth;

import com.github.cambierr.ovhapi.exception.RequestException;
import java.util.concurrent.CountDownLatch;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rx.Observable;
import rx.Subscriber;

/**
 *
 * @author honorem
 */
public class CredentialRequestTest {

    CredentialRequest credentials;
    
    String applicationKey = Settings.applicationKey;
    String applicationSecret = Settings.applicationSecret;
    String redirection = Settings.redirection;
    String validationUrl = Settings.validationUrl;

    public CredentialRequestTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        AccessRules rules = new AccessRules();
        credentials = CredentialRequest.build(applicationKey, applicationSecret, rules, redirection).toBlocking().single();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBuild() throws InterruptedException {
        System.out.println("build");
        AccessRules _rules = new AccessRules();
        _rules.addRule("/*");

        CredentialRequest result = CredentialRequest.build(applicationKey, applicationSecret, _rules, redirection).toBlocking().single();
        assertNotNull(result);
    }

    @Test
    public void testGetValidationUrl() {
        System.out.println("getValidationUrl");
        CredentialRequest instance = credentials;
        String result = instance.getValidationUrl();
        
        System.out.println(result);
        
        assertNotNull(result);
        assertTrue(result.contains(validationUrl));

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

        System.out.println(result.getConsumerKey());
        
        assertNotNull(result);
    }

}
