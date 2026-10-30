package com.riskified.golden;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.riskified.JSONFormater;
import com.riskified.JSONFormaterTestAccess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Compares the JSON the SDK sends for each {@link GoldenOrders} sample with the snapshot in
 * {@code src/test/resources/golden/<sample>.json}.
 *
 * <p>Run with {@code -Dgolden.update=true} to regenerate the snapshots on purpose.
 */
@RunWith(Parameterized.class)
public class GoldenPayloadTest {

    private static final Path GOLDEN_DIR = Paths.get("src", "test", "resources", "golden");
    private static final Path LEGACY_66_DIR = Paths.get("src", "test", "resources", "golden-6.6");
    private static final boolean UPDATE = Boolean.getBoolean("golden.update");

    private static TimeZone originalTimeZone;

    private final String name;
    private final Object payload;

    public GoldenPayloadTest(String name, Object payload) {
        this.name = name;
        this.payload = payload;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> samples() {
        Collection<Object[]> samples = new ArrayList<Object[]>();
        for (Map.Entry<String, Object> sample : GoldenOrders.all().entrySet()) {
            samples.add(new Object[]{sample.getKey(), sample.getValue()});
        }
        return samples;
    }

    // DateTimeSerializer formats with the JVM default time zone.
    @BeforeClass
    public static void pinTimeZone() {
        originalTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @AfterClass
    public static void restoreTimeZone() {
        TimeZone.setDefault(originalTimeZone);
    }

    @Test
    public void matchesSnapshot() throws IOException {
        JsonElement actual = JsonParser.parseString(JSONFormater.toJson(payload));
        Path snapshot = GOLDEN_DIR.resolve(name + ".json");

        if (UPDATE) {
            Files.createDirectories(GOLDEN_DIR);
            Files.write(snapshot, (pretty(actual) + "\n").getBytes(StandardCharsets.UTF_8));
            return;
        }

        assertTrue("Missing snapshot " + snapshot + ". Run with -Dgolden.update=true to create it.",
                Files.exists(snapshot));
        JsonElement expected = JsonParser.parseString(new String(Files.readAllBytes(snapshot), StandardCharsets.UTF_8));
        if (!expected.equals(actual)) {
            // Compare pretty-printed text so the failure shows a readable diff.
            assertEquals("Payload for '" + name + "' changed. If intended, run with -Dgolden.update=true.",
                    pretty(expected), pretty(actual));
        }
    }

    /**
     * The legacy wire format (RiskifiedClientBuilder.setLegacyWireFormat) must keep sending exactly what 6.6.x sent.
     * Snapshots in golden-6.6/ are frozen and never regenerated. A sample without one is unchanged since 6.6.x, so
     * its current snapshot applies; when a 6.7.0 change alters a current snapshot, copy the old one to golden-6.6/
     * first, or this test fails.
     */
    @Test
    public void legacyFormatMatches66Snapshot() throws IOException {
        if (UPDATE) {
            return;
        }
        JsonElement actual = JsonParser.parseString(JSONFormaterTestAccess.toJson(payload, true));
        Path frozen = LEGACY_66_DIR.resolve(name + ".json");
        Path snapshot = Files.exists(frozen) ? frozen : GOLDEN_DIR.resolve(name + ".json");
        assertTrue("Missing snapshot " + snapshot, Files.exists(snapshot));

        JsonElement expected = JsonParser.parseString(new String(Files.readAllBytes(snapshot), StandardCharsets.UTF_8));
        if (!expected.equals(actual)) {
            assertEquals("Legacy (6.6.x) payload for '" + name + "' doesn't match " + snapshot + ". If the documented "
                            + "format changed on purpose, copy the previous snapshot to " + LEGACY_66_DIR + " first.",
                    pretty(expected), pretty(actual));
        }
    }

    private static String pretty(JsonElement json) {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(json);
    }
}
