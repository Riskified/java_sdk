package com.riskified.notifications;

import com.riskified.SHA256Handler;
import org.junit.Test;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class NotificationHandlerTest {

    private static final String AUTH_KEY = "auth-key";

    @Test
    public void acceptsSingleLineBody() throws Exception {
        String body = "{\"order\":{\"id\":\"1\",\"status\":\"approved\",\"description\":\"Reviewed and approved\"}}";

        Notification notification = parse(body, sign(body));

        assertEquals("1", notification.getOrder().getId());
        assertEquals("approved", notification.getOrder().getStatus());
    }

    @Test
    public void acceptsPrettyPrintedBody() throws Exception {
        String body = "{\n"
                + "  \"order\": {\n"
                + "    \"id\": \"1\",\n"
                + "    \"status\": \"declined\"\n"
                + "  }\n"
                + "}\n";

        Notification notification = parse(body, sign(body));

        assertEquals("declined", notification.getOrder().getStatus());
    }

    @Test
    public void acceptsNonAsciiBodyWithoutDeclaredCharset() throws Exception {
        String body = "{\"order\":{\"id\":\"1\",\"status\":\"approved\",\"description\":\"Zürich café – approved\"}}";

        Notification notification = parse(body, sign(body));

        assertEquals("Zürich café – approved", notification.getOrder().getDescription());
    }

    @Test(expected = AuthError.class)
    public void rejectsWrongSignature() throws Exception {
        String body = "{\"order\":{\"id\":\"1\",\"status\":\"approved\"}}";

        parse(body, sign("{\"order\":{\"id\":\"2\",\"status\":\"approved\"}}"));
    }

    private static Notification parse(String body, String signature) throws Exception {
        return new NotificationHandler(AUTH_KEY).parseServletPostRequest(request(body.getBytes(StandardCharsets.UTF_8), signature));
    }

    private static String sign(String body) throws Exception {
        return new SHA256Handler(AUTH_KEY).createSHA256(body.getBytes(StandardCharsets.UTF_8));
    }

    // A request with no declared charset. Like a servlet container, getReader() then decodes as ISO-8859-1.
    private static HttpServletRequest request(final byte[] body, final String signature) {
        return (HttpServletRequest) Proxy.newProxyInstance(
                NotificationHandlerTest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "getHeader":
                            return "X-Riskified-Hmac-Sha256".equalsIgnoreCase((String) args[0]) ? signature : null;
                        case "getInputStream":
                            return inputStream(body);
                        case "getReader":
                            return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(body), StandardCharsets.ISO_8859_1));
                        case "getCharacterEncoding":
                            return null;
                        default:
                            throw new UnsupportedOperationException(method.getName());
                    }
                });
    }

    private static ServletInputStream inputStream(byte[] body) {
        final ByteArrayInputStream in = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return in.read();
            }

            @Override
            public boolean isFinished() {
                return in.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
