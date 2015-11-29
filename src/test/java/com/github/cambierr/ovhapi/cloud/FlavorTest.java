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
import com.github.cambierr.ovhapi.exception.RequestException;
import java.util.concurrent.CountDownLatch;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rx.Observable;
import java.util.List;
import rx.Subscriber;

/**
 *
 * @author honorem
 */
public class FlavorTest {

    static Project project;
    static Region region;
    static Flavor flavor;

    public FlavorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        AccessRules _rules = new AccessRules();
        _rules.addRule("/*");

        credential = Credential.build(Settings.applicationKey, Settings.applicationSecret, Settings.consumerKey).toBlocking().single();

        project = Project.byId(credential, Settings.projectId).toBlocking().single();

        region = Region.byName(project, Settings.defaultRegionName);
        
        flavor = Flavor.byId(project, Settings.defaultFlavorId).toBlocking().single();

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
    public void testComplete() {
        System.out.println("complete");
        Flavor instance = Flavor.byId(project, Settings.defaultFlavorId, region);
        instance.complete();
    }

    @Test
    public void testIsPartial() {
        System.out.println("isPartial");
        Flavor instance = Flavor.byId(project, Settings.defaultFlavorId, region);
        boolean expResult = true;
        boolean result = instance.isPartial();
        assertEquals(expResult, result);
    }

    @Test
    public void testList() {
        System.out.println("list");
        Project _project = project;
        Region _region = null; //all region

        List<Flavor> result = Flavor.list(_project, _region).toList().toBlocking().single();
        assertNotNull(result);
        assertNotNull(result.get(0));
    }

    @Test
    public void testById() {
        System.out.println("byId");
        Project _project = project;
        String _id = Settings.defaultFlavorId;
        Flavor result = Flavor.byId(_project, _id).toBlocking().single();
        assertNotNull(result);

        String _incorrectId = "500";
        try {
            Exception ex = new Exception();
            CountDownLatch latch = new CountDownLatch(1);
            Flavor.byId(_project, _incorrectId).subscribe(new Subscriber<Flavor>() {

                @Override
                public void onCompleted() {
                    latch.countDown();
                }

                @Override
                public void onError(Throwable e) {
                    ex.initCause(e);
                    latch.countDown();
                }

                @Override
                public void onNext(Flavor t) {

                }
            });
            latch.await();
            if (ex.getCause() != null) {
                throw ex.getCause();
            }
        } catch (Throwable ex) {
            if (ex instanceof RequestException) {
                RequestException exx = (RequestException) ex;
                System.out.println(exx.code() + " " + exx.message() + " : " + exx.body());
            } else {
                fail("An error occured");
            }
        }

    }

    @Test
    public void testGetId() {
        System.out.println("getId");
        Flavor instance = flavor;
        String expResult = Settings.defaultFlavorId;
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDisk() {
        System.out.println("getDisk");
        Flavor instance = flavor;
        int expResult = Settings.defaultFlavorDisk;
        int result = instance.getDisk();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetRegion() {
        System.out.println("getRegion");
        Flavor instance = flavor;
        Region expResult = Region.byName(project, Settings.defaultFlavorRegion);
        Region result = instance.getRegion();
        assertNotNull(result);
        assertEquals(expResult.getName(), result.getName());
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        Flavor instance = flavor;
        String expResult = Settings.defaultFlavorName;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetVcpus() {
        System.out.println("getVcpus");
        Flavor instance = flavor;
        int expResult = Settings.defaultFlavorVcpus;
        int result = instance.getVcpus();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetType() {
        System.out.println("getType");
        Flavor instance = flavor;
        String expResult = Settings.defaultFlavorType;
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOsType() {
        System.out.println("getOsType");
        Flavor instance = flavor;
        String expResult = Settings.defaultFlavorOsType;
        String result = instance.getOsType();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetRam() {
        System.out.println("getRam");
        Flavor instance = flavor;
        int expResult = Settings.defaultFlavorRam;
        int result = instance.getRam();
        assertEquals(expResult, result);
    }

}
