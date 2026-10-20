package com.riskified.notifications;

import com.riskified.SHA256Handler;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Regression tests for {@link NotificationHandler} HMAC verification.
 *
 * SECURITY-10974. The sibling PHP SDK disclosed the server-computed HMAC in its authorization
 * failure, which let an unauthenticated caller learn a valid signature for a payload of its
 * choosing. This SDK never had that defect, and these tests are what keeps it that way.
 */
public class NotificationHandlerTest {

    private static final String AUTH_KEY = "notification-test-secret";
    private static final String BODY =
            "{\"order\":{\"id\":\"ord-1\",\"status\":\"approved\",\"old_status\":\"pending\"}}";

    private String validHmacFor(String body) throws Exception {
        return new SHA256Handler(AUTH_KEY).createSHA256(body.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void correctlySignedNotificationIsParsed() throws Exception {
        NotificationHandler handler = new NotificationHandler(AUTH_KEY);

        Notification notification = handler.toObject(BODY, validHmacFor(BODY));

        assertEquals("ord-1", notification.getOrder().getId());
        assertEquals("approved", notification.getOrder().getStatus());
    }

    @Test(expected = AuthError.class)
    public void wrongHmacIsRejected() throws Exception {
        new NotificationHandler(AUTH_KEY).toObject(BODY, "not-the-right-hmac");
    }

    @Test(expected = AuthError.class)
    public void hmacForADifferentBodyIsRejected() throws Exception {
        String otherBody = "{\"order\":{\"id\":\"ord-2\",\"status\":\"declined\"}}";

        new NotificationHandler(AUTH_KEY).toObject(BODY, validHmacFor(otherBody));
    }

    @Test(expected = AuthError.class)
    public void hmacFromADifferentAuthKeyIsRejected() throws Exception {
        String foreignHmac = new SHA256Handler("someone-elses-secret")
                .createSHA256(BODY.getBytes(StandardCharsets.UTF_8));

        new NotificationHandler(AUTH_KEY).toObject(BODY, foreignHmac);
    }

    /**
     * A request that carries no X-Riskified-Hmac-Sha256 header arrives here as a null hash.
     * It must fail as AuthError - the documented exception - and not as NullPointerException,
     * which would escape every caller that catches only AuthError.
     */
    @Test
    public void missingHmacThrowsAuthErrorNotNullPointerException() throws Exception {
        try {
            new NotificationHandler(AUTH_KEY).toObject(BODY, null);
            fail("Expected AuthError for a notification with no HMAC header");
        } catch (AuthError expected) {
            assertEquals("Request HMAC signature was either missing or incorrect", expected.getMessage());
        } catch (NullPointerException e) {
            fail("Missing HMAC header must raise AuthError, not NullPointerException");
        }
    }

    @Test(expected = AuthError.class)
    public void emptyHmacIsRejected() throws Exception {
        new NotificationHandler(AUTH_KEY).toObject(BODY, "");
    }

    /**
     * The failure must not tell the caller what the correct signature was, nor echo the body
     * back at it. That disclosure is the whole of SECURITY-10974.
     */
    @Test
    public void authErrorDisclosesNeitherHmacNorBody() throws Exception {
        String computed = validHmacFor(BODY);

        try {
            new NotificationHandler(AUTH_KEY).toObject(BODY, "not-the-right-hmac");
            fail("Expected AuthError");
        } catch (AuthError e) {
            String message = String.valueOf(e.getMessage());
            assertFalse("computed HMAC must not appear in the error", message.contains(computed));
            assertFalse("received HMAC must not appear in the error", message.contains("not-the-right-hmac"));
            assertFalse("request body must not appear in the error", message.contains(BODY));
            assertFalse("order id must not appear in the error", message.contains("ord-1"));
        }
    }
}
