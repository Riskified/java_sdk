package com.riskified.samples.notificationServer;

/**
 * Reads the Riskified auth token for the notification samples from the environment.
 *
 * The samples used to carry the token as a source literal. A sample is published in a public
 * repository, so a literal there is a disclosed credential - and anyone holding the token can
 * forge a notification that passes HMAC verification. Supply it at run time instead:
 *
 * <pre>
 *   export RISKIFIED_AUTH_TOKEN='&lt;the auth token from Settings in the Riskified web app&gt;'
 * </pre>
 */
public final class SampleAuthToken {

    private static final String ENV_VAR = "RISKIFIED_AUTH_TOKEN";

    private SampleAuthToken() {
    }

    /**
     * @return the auth token from the RISKIFIED_AUTH_TOKEN environment variable
     * @throws IllegalStateException if the variable is unset or empty, rather than starting a
     *         server that would reject every notification it receives
     */
    public static String fromEnvironment() {
        String token = System.getenv(ENV_VAR);
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalStateException(
                    ENV_VAR + " is not set. Export your Riskified auth token (Settings tab in the "
                            + "Riskified web app) before starting this sample.");
        }
        return token;
    }
}
