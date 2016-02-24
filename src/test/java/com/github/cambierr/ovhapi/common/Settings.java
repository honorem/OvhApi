/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cambierr.ovhapi.common;

/**
 *
 * @author honorem
 */
public class Settings {

    public static final String consumerKey = System.getenv("ovhCk");
    public static final String applicationKey = System.getenv("ovhAk");
    public static final String applicationSecret = System.getenv("ovhAs");

    public static final String dateString = System.getenv("epochString"); //replace with your GMT
    public static final Long dateLong = 0L;

    public static final String projectId = System.getenv("ovhProject"); //replace with your project test id
    public static final String defaultSnapshotId = System.getenv("ovhSnapshot"); //replace with an existing snapshot in the project

    /**
     * Internal test settings
     * You shouldn't need to update those variables
     */
    public static final String redirection = "http://example.com";
    public static final String validationUrl = "https://eu.api.ovh.com/auth/?credentialToken=";

    public static final String projectDescription = "test";
    public static final String projectStatus = "ok";
    public static final Long projectCreationDate = 1441875985000L;
    public static final Double projectBalance = 0.0;

    public static final String defaultRegionName = "GRA1";

    public static final String defaultFlavorId = "98c1e679-5f2c-4069-b4da-4a4f7179b758";
    public static final int defaultFlavorDisk = 10;
    public static final String defaultFlavorRegion = "GRA1";
    public static final String defaultFlavorName = "vps-ssd-1";
    public static final int defaultFlavorVcpus = 1;
    public static final String defaultFlavorType = "ovh.vps-ssd";
    public static final String defaultFlavorOsType = "linux";
    public static final int defaultFlavorRam = 2000;

    public static final String defaultImageId = "3bda2a66-5c24-4b1d-b850-83333b580674";

    public static final String publicKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQC4mpXtv+yNbpxBTYkVv79d6ukNr3kYtuAQmYaONkgj6EQbNwNcee5j0hhjVZNCn7lav3wSsBU3nHhlLZyULPsV6WDp1cAayklBBSuoLnftAZJT+pIEUM8QFE0BFXDns+QSkFvXHsZGk7GUpQKVNvzWTpltaj9AksVL6yFH+RC7SfRXbQeQyN9EeIFCLHYUNKyPua7AQz2GCWT0VQeTQcMIBRMqqwn8w9dCnlOb6cs88xPjQQGtHMNukly8ryhSANiBx+kYI2+YT0bOINQ+5yQtHd2udVJQKdHCMOkznEuogTQXwAJea4fLIzAaXhJV/rI2wMsEbT4k9agVSImTzlLS2FZJObNRsanWPhAoCgMDz+ro/AFOYXX+V/lp4pwHNJDhU3as0QXBTOoJDJ7dguN7xlYazIuG1rOhLC6Wvj7P2fOyshpEVO0d1naeIIumLCf/6ewaFFypvFESxs55pFzVrnv1iCWCjm+NjdTVnTzd9MAqeeLz9I1VWvxJTIeoSFqkgkBOOKLS7C1c3i2DNmVO6/2GQv02mkc7R8BfHRwhzsSdXpEHdZQT3u8y8Cd9P6DM2Z5/D+Ma67T0TrJu7v4uYOwzQ8B6FZNdFbCh9wOpUosH52IlJfTgLcnxGzY/mTmj11tKKYIXP88KqiJrDy8Bez4QiN4SyUroQyYIxj6qFw==";

    public static String defaultStorageId = ""; //this will be set during test
    public static final String defaultStorageRegion = "GRA1";
    public static final String defaultStorageName = "Test";
    public static final String defaultStorageStaticUrl = "Test.AUTH-" + projectId + ".storage.gra1.cloud.ovh.net";
    public static final Long defaultStorageStoredBytes = 0L;
    public static final Long defaultStorageStoredObjects = 0L;
    public static final Boolean defaultStorageIsPublic = false;

    public static String defaultInstanceId = ""; //this will be set during test
    public static final String defaultInstanceRegion = "GRA1";
    public static final String defaultInstanceName = "Test";
    public static final String defaultInstanceImageId = "3bda2a66-5c24-4b1d-b850-83333b580674";
    public static final String defaultInstanceFlavorId = "98c1e679-5f2c-4069-b4da-4a4f7179b758";

    public static final String defaultSnapshotVisibility = "private";

}
