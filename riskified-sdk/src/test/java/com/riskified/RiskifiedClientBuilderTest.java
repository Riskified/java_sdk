package com.riskified;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
}
