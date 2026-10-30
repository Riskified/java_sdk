package com.riskified;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RiskifiedClientBuilderTest {

    @Test
    public void builderSetsScreenBaseUrlForEveryEnvironment() throws Exception {
        for (Environment environment : Environment.values()) {
            RiskifiedClient client = new RiskifiedClient.RiskifiedClientBuilder("shop.example.com", "auth-key", environment).build();

            assertNotNull("screen URL for " + environment, client.getScreenBaseUrl());
            assertEquals("screen URL for " + environment,
                    Utils.getScreenBaseFromEnvironment(environment), client.getScreenBaseUrl());
        }
    }

    @Test
    public void builderMatchesEnvironmentConstructor() throws Exception {
        for (Environment environment : Environment.values()) {
            RiskifiedClient built = new RiskifiedClient.RiskifiedClientBuilder("shop.example.com", "auth-key", environment).build();
            RiskifiedClient constructed = new RiskifiedClient("shop.example.com", "auth-key", environment);

            assertEquals("screen URL for " + environment, constructed.getScreenBaseUrl(), built.getScreenBaseUrl());
        }
    }

    @Test
    public void legacyWireFormatIsOffByDefault() throws Exception {
        RiskifiedClient built = new RiskifiedClient.RiskifiedClientBuilder("shop.example.com", "auth-key", Environment.SANDBOX).build();
        RiskifiedClient constructed = new RiskifiedClient("shop.example.com", "auth-key", Environment.SANDBOX);

        assertFalse(built.isLegacyWireFormat());
        assertFalse(constructed.isLegacyWireFormat());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void builderTurnsOnLegacyWireFormat() throws Exception {
        RiskifiedClient client = new RiskifiedClient.RiskifiedClientBuilder("shop.example.com", "auth-key", Environment.SANDBOX)
                .setLegacyWireFormat(true)
                .build();

        assertTrue(client.isLegacyWireFormat());
    }
}
