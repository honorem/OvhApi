/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.auth;

import com.github.cambierr.ovhapi.common.Settings;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
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
public class CredentialTest {
    
    String applicationKey = Settings.applicationKey;
    String applicationSecret = Settings.applicationSecret;
    String consumerKey = Settings.consumerKey;
    
    Credential credential;
    
    public CredentialTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        credential = Credential.build(applicationKey, applicationSecret, consumerKey).toBlocking().single();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testBuild() {
        System.out.println("build");
        Credential result = Credential.build(applicationKey, applicationSecret, consumerKey).toBlocking().single();
        assertNotNull(result);
    }

    @Test
    public void testSign() throws Exception {
        System.out.println("sign");
        HttpsURLConnection _link = (HttpsURLConnection) new URL("https://example.com").openConnection();
        String _body = "testSignWithBody";
        Credential instance = credential;
        instance.sign(_link, _body);
        
        Map<String, List<String>> requestProperties = _link.getRequestProperties();
   
        
        assertNotNull(requestProperties.get("X-Ovh-Timestamp"));
        assertNotNull(requestProperties.get("X-Ovh-Application"));
        assertNotNull(requestProperties.get("X-Ovh-Consumer"));
        assertNotNull(requestProperties.get("X-Ovh-Signature"));
        
        assertEquals(requestProperties.get("X-Ovh-Consumer").get(0), consumerKey);
        
        Long time = System.currentTimeMillis()/1000;
        
        String linkSha1 = requestProperties.get("X-Ovh-Signature").get(0);
        String sha1;
        boolean test = false;
        for(int i = 0 ; i < 3; i++){
            sha1 = "$1$"+getSign(_link, time - i, _body);
            if(sha1.equals(linkSha1)){
                test = true;
                break;
            }
        }
        assertTrue("Sha1 was not correct after 3 trials.", test);        
    }

    @Test
    public void testCheck() throws Exception {
        System.out.println("check");
        Credential instance = credential;
        instance.check();
    }

    @Test
    public void testGetConsumerKey() {
        System.out.println("getConsumerKey");
        Credential instance = credential;
        String expResult = consumerKey;
        String result = instance.getConsumerKey();
        
        assertEquals(expResult, result);
        
    }
    
    
    private String getSign(HttpsURLConnection _link, Long time, String _body) throws Exception{
        
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        String preHash = this.applicationSecret
                + "+" + this.consumerKey
                + "+" + _link.getRequestMethod()
                + "+" + _link.getURL()
                + "+" + ((_body == null) ? "" : _body)
                + "+" + time;
        
        
        return bytesToHex(md.digest(preHash.getBytes()));
    }
    private String bytesToHex(byte[] _bytes) {
        String result = "";
        for (int i = 0; i < _bytes.length; i++) {
            result += Integer.toString((_bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
