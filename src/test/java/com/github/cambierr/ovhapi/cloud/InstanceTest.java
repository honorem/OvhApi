/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.cloud;

import com.github.cambierr.ovhapi.auth.Credential;
import static com.github.cambierr.ovhapi.cloud.ProjectTest.credential;
import static com.github.cambierr.ovhapi.cloud.StorageTest.project;
import com.github.cambierr.ovhapi.common.Settings;
import com.github.cambierr.ovhapi.exception.RequestException;
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
public class InstanceTest {

    static Project project;
    static Region region;
    static Flavor flavor;
    static Image image;
    static Instance iInstance;

    public InstanceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        credential = Credential.build(Settings.applicationKey, Settings.applicationSecret, Settings.consumerKey).toBlocking().single();

        project = Project.byId(credential, Settings.projectId).toBlocking().single();
        region = Region.byName(project, Settings.defaultRegionName);
        flavor = Flavor.byId(project, Settings.defaultInstanceFlavorId).toBlocking().single();
        image = Image.byId(project, Settings.defaultInstanceImageId).toBlocking().single();

        iInstance = Instance.create(project, flavor, image, region, null, Settings.defaultInstanceName).toBlocking().single();
        Settings.defaultInstanceId = iInstance.getId();
        
        iInstance = Instance.byId(project, Settings.defaultInstanceId).toBlocking().single();

    }

    @AfterClass
    public static void tearDownClass() {
        iInstance.kill().toBlocking().single();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of list method, of class Instance.
     */
    @Test
    public void testList() {
        System.out.println("list");
        Project _project = project;
        Region _region = region;

        List<Instance> result = Instance.list(_project, _region).toList().toBlocking().single();

        assertNotNull(result);
        assertNotNull(result.get(0));
        assertEquals(result.get(0).getClass(), Instance.class);
    }

    /**
     * Test of byId method, of class Instance.
     */
    @Test
    public void testById() {
        System.out.println("byId");
        Project _project = project;
        String _id = Settings.defaultInstanceId;

        Instance result = Instance.byId(_project, _id).toBlocking().single();
        assertNotNull(result);

    }

    /**
     * Test of getStatus method, of class Instance.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Instance instance = iInstance;

        Instance.Status result = instance.getStatus();
        assertNotNull(result);

    }

    /**
     * Test of getRegion method, of class Instance.
     */
    @Test
    public void testGetRegion() {
        System.out.println("getRegion");
        Instance instance = iInstance;

        Region result = instance.getRegion();
        assertEquals(Settings.defaultInstanceRegion, result.getName());

    }

    /**
     * Test of getName method, of class Instance.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Instance instance = iInstance;
        String expResult = Settings.defaultInstanceName;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getImage method, of class Instance.
     */
    @Test
    public void testGetImage() {
        System.out.println("getImage");
        Instance instance = iInstance;

        Image result = instance.getImage();
        assertEquals(Settings.defaultInstanceImageId, result.getId());

    }

    /**
     * Test of getCreationDate method, of class Instance.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Instance instance = iInstance;
        long expResult = System.currentTimeMillis();
        long result = instance.getCreationDate();

        assertTrue(result < expResult && result > expResult - 10000);

    }

    /**
     * Test of getFlavor method, of class Instance.
     */
    @Test
    public void testGetFlavor() {
        System.out.println("getFlavor");
        Instance instance = iInstance;

        Flavor result = instance.getFlavor();
        assertEquals(Settings.defaultInstanceFlavorId, result.getId());

    }

    /**
     * Test of getSshKey method, of class Instance.
     */
    @Test
    public void testGetSshKey() {
        System.out.println("getSshKey");
        Instance instance = iInstance;
        SshKey expResult = null;
        SshKey result = instance.getSshKey();
        assertEquals(expResult, result);

    }

    /**
     * Test of getId method, of class Instance.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Instance instance = iInstance;
        String expResult = Settings.defaultInstanceId;
        String result = instance.getId();
        assertEquals(expResult, result);

    }

    /**
     * Test of kill method, of class Instance.
     */
    @Test
    public void testKill() {
        System.out.println("kill");
        
        //test at teardown
    }

    /**
     * Test of create method, of class Instance.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        
        //test at setup
    }

    /**
     * Test of resize method, of class Instance.
     */
    @Test
    public void testResize() {
        System.out.println("resize");
        Flavor _flavor = Flavor.byId(project, Settings.defaultInstanceFlavorId).toBlocking().single();
        Instance instance = iInstance;

        try {
            Instance result = instance.resize(_flavor).toBlocking().single();
            assertNotNull(result);
        } catch (Exception ex) {

            if (ex.getCause() instanceof RequestException && ((RequestException) ex.getCause()).message().equals("ObjectAlreadyExists")) {

            } else {
                fail("Snapshot failed");
            }
        }

    }

    /**
     * Test of reinstall method, of class Instance.
     */
    @Test
    public void testReinstall() {
        System.out.println("reinstall");
        Image _image = Image.byId(project, Settings.defaultInstanceImageId).toBlocking().single();
        Instance instance = iInstance;

        try {
            Instance result = instance.reinstall(_image).toBlocking().single();
            assertNotNull(result);
        } catch (Exception ex) {

            if (ex.getCause() instanceof RequestException && ((RequestException) ex.getCause()).message().equals("ObjectAlreadyExists")) {

            } else {
                fail("Snapshot failed");
            }
        }
    }

    /**
     * Test of rename method, of class Instance.
     */
    @Test
    public void testRename() {
        System.out.println("rename");
        String _name = Settings.defaultInstanceName;
        Instance instance = iInstance;

        Instance result = instance.rename(_name).toBlocking().single();
        assertNotNull(result);

    }

    /**
     * Test of update method, of class Instance.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Instance instance = iInstance;

        Instance result = instance.update().toBlocking().single();
        assertNotNull(result);

    }

    /**
     * Test of reboot method, of class Instance.
     */
    @Test
    public void testReboot() {
        System.out.println("reboot");
        Instance.RebootType _reboot = Instance.RebootType.soft;
        Instance instance = iInstance;
        try {
            Instance result = instance.reboot(_reboot).toBlocking().single();
            assertNotNull(result);
        } catch (Exception ex) {

            if (ex.getCause() instanceof RequestException && ((RequestException) ex.getCause()).message().equals("ObjectAlreadyExists")) {

            } else {
                fail("Snapshot failed");
            }
        }

    }

    /**
     * Test of snapshot method, of class Instance.
     */
    @Test
    public void testSnapshot() {
        System.out.println("snapshot");
        String _name = "TEST123";
        Instance instance = iInstance;
        try {
            Instance result = instance.snapshot(_name).toBlocking().single();
            assertNotNull(result);
        } catch (Exception ex) {

            if (ex.getCause() instanceof RequestException && ((RequestException) ex.getCause()).message().equals("ObjectAlreadyExists")) {

            } else {
                fail("Snapshot failed");
            }
        }

    }

    /**
     * Test of createBulk method, of class Instance.
     */
    @Test
    public void testCreateBulk() {
        //if create succeed, don't need it
    }

    /**
     * Test of getIpAddresses method, of class Instance.
     */
    @Test
    public void testGetIpAddresses() {
        System.out.println("getIpAddresses");
        Instance instance = iInstance;
        List<IpAddress> result = instance.getIpAddresses();
        assertNotNull(result);
    }

}
