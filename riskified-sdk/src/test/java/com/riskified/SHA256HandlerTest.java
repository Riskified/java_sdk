package com.riskified;

import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * HMAC regression tests.
 *
 * <p>
 * The signature is a function of the request body and the auth token alone — no timestamp, no
 * nonce, no method, no path, no headers — and it is lowercase hex. See
 * {@code docs/flows/00-shared-contract.md} section 1.
 */
public class SHA256HandlerTest {

    /** {@code docs/flows/00-shared-contract.md} section 1, "Reference test vector". */
    private static final String REFERENCE_TOKEN = "test_token";
    private static final String REFERENCE_BODY = "{\"order\":{\"id\":\"TEST-1\"}}";
    private static final String REFERENCE_HMAC =
            "b07f97d1466dfe33f74c2c006e16cc3d17d97c7f337a96eaaaff35ce20a83131";

    /**
     * The corpus reference vector. A match proves the key encoding, the message encoding, the hex
     * casing and the body-only rule all at once.
     */
    @Test
    public void testReferenceVectorReproduces() throws RiskifiedError {
        SHA256Handler inputHandler = new SHA256Handler(REFERENCE_TOKEN);

        String actualHmac = inputHandler.createSHA256(REFERENCE_BODY.getBytes(StandardCharsets.UTF_8));

        assertEquals(REFERENCE_HMAC, actualHmac);
    }

    /**
     * The key is encoded as UTF-8, not with the platform default charset.
     *
     * <p>
     * For the hex tokens Riskified issues this changes nothing — that is exactly why the old
     * {@code authKey.getBytes()} went unnoticed. The point of pinning it is that the signature must
     * not depend on a JVM locale setting, so this asserts the encoding directly for a token whose
     * bytes differ between charsets.
     */
    @Test
    public void testKeyIsEncodedAsUtf8NotThePlatformDefault() throws Exception {
        String inputNonAsciiToken = "t\u00f6k\u00e9n-\u00e9\u00e0";
        SHA256Handler inputHandler = new SHA256Handler(inputNonAsciiToken);

        String actualHmac = inputHandler.createSHA256(REFERENCE_BODY.getBytes(StandardCharsets.UTF_8));
        String expectedUtf8KeyHmac = hmacWithKeyBytes(inputNonAsciiToken.getBytes(StandardCharsets.UTF_8));
        String latin1KeyHmac = hmacWithKeyBytes(inputNonAsciiToken.getBytes(StandardCharsets.ISO_8859_1));

        assertEquals(expectedUtf8KeyHmac, actualHmac);
        // Guards the test itself: if these two agreed, the assertion above would prove nothing.
        assertNotEquals(expectedUtf8KeyHmac, latin1KeyHmac);
        assertEquals(64, actualHmac.length());
        assertEquals(actualHmac.toLowerCase(Locale.US), actualHmac);
    }

    /** An independent HMAC-SHA256 implementation, so the assertions above are not self-referential. */
    private static String hmacWithKeyBytes(byte[] keyBytes) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(keyBytes, "HmacSHA256"));
        byte[] digest = mac.doFinal(REFERENCE_BODY.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            hex.append(String.format(Locale.US, "%02x", b));
        }
        return hex.toString();
    }

    /** A trailing newline changes the digest — the byte-identity rule, demonstrated. */
    @Test
    public void testTrailingNewlineChangesTheDigest() throws RiskifiedError {
        SHA256Handler inputHandler = new SHA256Handler(REFERENCE_TOKEN);

        String actualHmac =
                inputHandler.createSHA256((REFERENCE_BODY + "\n").getBytes(StandardCharsets.UTF_8));

        assertEquals(64, actualHmac.length());
        assertNotEquals(REFERENCE_HMAC, actualHmac);
    }
}
