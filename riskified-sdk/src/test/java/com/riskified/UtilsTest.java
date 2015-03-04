package com.riskified;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UtilsTest {
    @Test
    public void testGetBaseUrlFromEnvironment() throws Exception {
        Environment environment = null;
        String urlFromEnvironment = Utils.getBaseUrlFromEnvironment(environment);
        assertNull(urlFromEnvironment);

        environment = Environment.SANDBOX;
        urlFromEnvironment = Utils.getBaseUrlFromEnvironment(environment);
        assertEquals(Utils.SANDBOX_ENVIRONMENT, urlFromEnvironment);

        environment = Environment.PRODUCTION;
        urlFromEnvironment = Utils.getBaseUrlFromEnvironment(environment);
        assertEquals(Utils.PRODUCTION_ENVIRONMENT, urlFromEnvironment);

        environment = Environment.DEBUG;
        urlFromEnvironment = Utils.getBaseUrlFromEnvironment(environment);
        assertEquals(Utils.DEBUG_ENVIRONMENT, urlFromEnvironment);
    }
}