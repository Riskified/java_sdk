package com.riskified;

import com.riskified.validations.Validation;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class RiskifiedClientTest {
    private RiskifiedClient riskifiedClient;
    private static String shopUrl;
    private static String authKey;
    private static Validation validation;
    private static Environment environment;

    @BeforeClass
    public static void runOnceBeforeAnyTestCaseIsRun() throws IOException {
        Properties properties = new Properties();
        properties.load(RiskifiedClientTest.class.getClassLoader().getResourceAsStream("riskified_sdk.properties"));
        shopUrl = properties.getProperty("shopUrl", "test.pass.com");
        authKey = properties.getProperty("authKey", "ad6b6e6376fb1e3521e44ca28451d58b9605d932");
        String validationProperty = properties.getProperty("validation");
        validation = null;
        if (StringUtils.isNotEmpty(validationProperty)) {
            validation = Validation.valueOf(validationProperty.toUpperCase());
        }

        String environmentProperty = properties.getProperty("environment");
        if (StringUtils.isNotEmpty(environmentProperty)) {
            environment = Environment.valueOf(environmentProperty.toUpperCase());
        }
    }

    @Test
    public void testRiskifiedClientConstruction_withoutRequestAndConnectionTimeout() throws RiskifedError {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).build();
        assertNotNull(riskifiedClient);
        assertEquals("Invalid shop url", shopUrl, riskifiedClient.getShopUrl());
        assertEquals("Invalid request timeout ", 10000, riskifiedClient.getRequestTimeout());
        assertEquals("Invalid connection timeout", 5000, riskifiedClient.getConnectionTimeout());
        assertEquals(Validation.ALL, riskifiedClient.getValidation());
    }

    @Test
    public void testRiskifiedClientConstruction_withNonDefaultRequestTimeout_connectionTimeout_validationType() throws RiskifedError {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setRequestTimeout(7).setConnectionTimeout(11).setValidation(Validation.IGNORE_MISSING).build();
        assertNotNull(riskifiedClient);
        assertEquals("Invalid shop url", shopUrl, riskifiedClient.getShopUrl());
        assertEquals("Invalid request timeout ", 7, riskifiedClient.getRequestTimeout());
        assertEquals("Invalid connection timeout", 11, riskifiedClient.getConnectionTimeout());
        assertEquals(Validation.IGNORE_MISSING, riskifiedClient.getValidation());
    }

    @Test
    public void testRiskifiedClientConstructionWithoutAnyArguments() throws RiskifedError {
        riskifiedClient = new RiskifiedClient();
        assertEquals("Invalid request timeout ", 10000, riskifiedClient.getRequestTimeout());
        assertEquals("Invalid connection timeout", 5000, riskifiedClient.getConnectionTimeout());
        assertEquals("Invalid shop url", shopUrl, riskifiedClient.getShopUrl());
        assertEquals("Invalid validation", validation, riskifiedClient.getValidation());
        assertEquals("Invalid environment", environment, riskifiedClient.getEnvironment());
    }
}