package com.riskified;

import com.google.gson.Gson;
import com.riskified.models.CheckoutResponse;
import com.riskified.models.Response;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Error-mapping regression tests.
 *
 * <p>
 * Two defects are covered. The client used to rewrite <b>every</b> unmatched status to
 * {@code 500 "Contact Riskified support"} and throw the body away, so a 503, a 502 and a genuine 500
 * were indistinguishable to a caller deciding whether to retry. And the checkout path read
 * {@code responseObject.getError().getMessage()} before establishing that the body had parsed as
 * that shape at all, so any of the other six documented error shapes
 * ({@code docs/flows/00-shared-contract.md} section 6) produced a {@link NullPointerException} in
 * place of the HTTP error.
 */
public class RiskifiedClientErrorTest {

    /** Parses an error body the way the checkout path does: best effort, never throwing. */
    private static Response parseCheckoutBody(String body) {
        try {
            CheckoutResponse parsed = new Gson().fromJson(body, CheckoutResponse.class);
            if (parsed == null) {
                return null;
            }
            parsed.setOrder(parsed.getCheckout());
            return parsed;
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Test
    public void testUnmatchedStatusKeepsItsOwnStatusCode() {
        RiskifiedHttpException actual503 =
                RiskifiedClient.buildHttpException(503, "Service Unavailable", "upstream down", null);
        RiskifiedHttpException actual502 =
                RiskifiedClient.buildHttpException(502, "Bad Gateway", "bad gateway", null);
        RiskifiedHttpException actual500 =
                RiskifiedClient.buildHttpException(500, "Internal Server Error", "boom", null);

        assertEquals(503, actual503.getStatusCode());
        assertEquals(502, actual502.getStatusCode());
        assertEquals(500, actual500.getStatusCode());
        assertEquals("Service Unavailable", actual503.getStatusText());
    }

    @Test
    public void testUnmatchedStatusKeepsTheResponseBody() {
        String inputBody = "<html><body>502 Bad Gateway</body></html>";

        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(502, "Bad Gateway", inputBody, null);

        assertEquals(inputBody, actualException.getResponseBody());
        assertNull("a non-JSON body parses to no error object, and that is not a failure",
                actualException.getError());
    }

    @Test
    public void testUnmatchedStatusIsNoLongerRewrittenTo500() {
        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(429, "Too Many Requests", "slow down", null);

        assertTrue(actualException instanceof HttpResponseException);
        assertEquals(429, actualException.getStatusCode());
        assertNotEquals("Contact Riskified support", actualException.getReasonPhrase());
    }

    /** Shape A — the one shape the SDK parses. The message comes from the parsed error. */
    @Test
    public void testShapeAErrorBodyYieldsTheParsedMessage() {
        String inputBody = "{\"error\":{\"message\":\"order id is missing\",\"code\":\"invalid\"}}";

        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(400, "Bad Request", inputBody, parseCheckoutBody(inputBody));

        assertEquals(400, actualException.getStatusCode());
        assertNotNull(actualException.getError());
        assertEquals("order id is missing", actualException.getError().getMessage());
        assertEquals("order id is missing", actualException.getReasonPhrase());
        assertEquals(inputBody, actualException.getResponseBody());
    }

    /**
     * Shape B — a flat {@code {"message": ...}} body, used by the Policy specs. Parses to no
     * {@code error} object; previously this was the {@link NullPointerException}.
     */
    @Test
    public void testFlatMessageErrorBodyDoesNotThrow() {
        String inputBody = "{\"message\":\"JSON malformed - missing 'claim_reason' field\"}";

        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(400, "Bad Request", inputBody, parseCheckoutBody(inputBody));

        assertEquals(400, actualException.getStatusCode());
        assertNull(actualException.getError());
        assertEquals(inputBody, actualException.getResponseBody());
        assertEquals(inputBody, actualException.getReasonPhrase());
    }

    /** Shape G — a bare JSON string rather than an object, which the OTP spec returns on 400/403/500. */
    @Test
    public void testBareStringErrorBodyDoesNotThrow() {
        String inputBody = "\"invalid phone number\"";

        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(400, "Bad Request", inputBody, parseCheckoutBody(inputBody));

        assertEquals(400, actualException.getStatusCode());
        assertNull(actualException.getError());
        assertEquals(inputBody, actualException.getResponseBody());
    }

    /** Shape C/E — {@code statusCode} arrives as a JSON number under a schema that declares a string. */
    @Test
    public void testErrorWithCodeBodyDoesNotThrow() {
        String inputBody = "{\"statusCode\":429,\"message\":\"Too many requests\"}";

        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(429, "Too Many Requests", inputBody, parseCheckoutBody(inputBody));

        assertEquals(429, actualException.getStatusCode());
        assertNull(actualException.getError());
        assertEquals(inputBody, actualException.getResponseBody());
    }

    /** Shape D — {@code {"messages": [...]}}, returns spec only. */
    @Test
    public void testMessagesArrayErrorBodyDoesNotThrow() {
        String inputBody = "{\"messages\":[\"a\",\"b\"]}";

        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(400, "Bad Request", inputBody, parseCheckoutBody(inputBody));

        assertEquals(400, actualException.getStatusCode());
        assertNull(actualException.getError());
    }

    /** An empty body, and a malformed one, are both survivable. */
    @Test
    public void testEmptyAndMalformedBodiesDoNotThrow() {
        RiskifiedHttpException actualEmpty =
                RiskifiedClient.buildHttpException(500, "Internal Server Error", "", parseCheckoutBody(""));
        RiskifiedHttpException actualMalformed =
                RiskifiedClient.buildHttpException(500, "Internal Server Error", "{not json",
                        parseCheckoutBody("{not json"));

        assertEquals(500, actualEmpty.getStatusCode());
        assertEquals("Internal Server Error", actualEmpty.getReasonPhrase());
        assertEquals(500, actualMalformed.getStatusCode());
        assertEquals("{not json", actualMalformed.getResponseBody());
    }

    /**
     * A non-ASCII error body read without an explicit charset.
     *
     * <p>
     * The fallback in {@code EntityUtils.toString(entity)} is subtler than "always ISO-8859-1": for
     * {@code application/json} with no charset parameter, httpclient 4.5.13 resolves UTF-8 from
     * {@code ContentType.APPLICATION_JSON}'s registered default, so the happy path was never broken.
     * It falls back to ISO-8859-1 when the mime type's registered default is ISO-8859-1
     * ({@code text/html}, {@code text/plain}) or when there is <b>no</b> {@code Content-Type} header
     * at all — which is precisely the 502/503-from-a-proxy and bare-string-OTP-error case that the
     * unmatched-status work is about. Passing {@code "UTF-8"} explicitly makes the read independent
     * of what the server chose to label the body.
     */
    @Test
    public void testNonAsciiErrorBodyIsReadAsUtf8RegardlessOfContentType() throws Exception {
        String inputMessage = "Requête invalide — coût 12€";
        byte[] inputBytes = inputMessage.getBytes(StandardCharsets.UTF_8);
        // A proxy error page, and a body with no Content-Type at all.
        ByteArrayEntity inputHtmlEntity = new ByteArrayEntity(inputBytes, ContentType.create("text/html"));
        ByteArrayEntity inputUntypedEntity = new ByteArrayEntity(inputBytes);

        assertEquals(inputMessage, EntityUtils.toString(inputHtmlEntity, "UTF-8"));
        assertEquals(inputMessage, EntityUtils.toString(inputUntypedEntity, "UTF-8"));
        // Guards the test: without the explicit charset these two are mojibake, which is the bug.
        assertNotEquals(inputMessage, EntityUtils.toString(new ByteArrayEntity(inputBytes,
                ContentType.create("text/html"))));
        assertNotEquals(inputMessage, EntityUtils.toString(new ByteArrayEntity(inputBytes)));
    }

    /** The non-ASCII body survives onto the exception verbatim, in both the body and the message. */
    @Test
    public void testNonAsciiErrorBodyIsPreservedOnTheException() {
        String inputBody = "{\"error\":{\"message\":\"coût invalide 12€\"}}";

        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(400, "Bad Request", inputBody, parseCheckoutBody(inputBody));

        assertEquals(inputBody, actualException.getResponseBody());
        assertNotNull(actualException.getError());
        assertEquals("coût invalide 12€", actualException.getError().getMessage());
    }

    /** 504 is documented with no content schema, so its message stays the fixed retry hint. */
    @Test
    public void test504KeepsItsDocumentedRetryMessage() {
        RiskifiedHttpException actualException =
                RiskifiedClient.buildHttpException(504, "Gateway Timeout", "", null);

        assertEquals(504, actualException.getStatusCode());
        assertEquals("Temporary error, please retry", actualException.getReasonPhrase());
    }
}
