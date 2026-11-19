package com.riskified.golden;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.riskified.JSONFormater;
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

    private static String pretty(JsonElement json) {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(json);
    }
}
