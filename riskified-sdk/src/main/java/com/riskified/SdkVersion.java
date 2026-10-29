package com.riskified;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The SDK version, read from sdk.properties, which Maven fills in from the pom at build time.
 */
final class SdkVersion {

    static final String UNKNOWN = "unknown";
    static final String VALUE = load();

    private SdkVersion() {
    }

    private static String load() {
        InputStream in = SdkVersion.class.getResourceAsStream("sdk.properties");
        if (in == null) {
            return UNKNOWN;
        }
        try {
            Properties properties = new Properties();
            properties.load(in);
            String version = properties.getProperty("version", "").trim();
            // An unfiltered placeholder means the resource wasn't processed by Maven (e.g. some IDE runs).
            return version.isEmpty() || version.startsWith("${") ? UNKNOWN : version;
        } catch (IOException e) {
            return UNKNOWN;
        } finally {
            try {
                in.close();
            } catch (IOException ignored) {
            }
        }
    }
}
