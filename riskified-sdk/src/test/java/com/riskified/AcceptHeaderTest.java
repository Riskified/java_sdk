package com.riskified;

import com.riskified.models.CheckoutDeniedOrder;
import com.riskified.models.Order;
import com.riskified.models.OtpInitiate;
import com.riskified.validations.Validation;
import com.sun.net.httpserver.HttpServer;
import org.apache.http.client.methods.HttpPost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;

/**
 * The documented Accept value goes to every endpoint except OTP, which keeps application/json. The legacy wire format
 * sends application/json everywhere, as 6.6.x did.
 */
public class AcceptHeaderTest {

    private static final String DOCUMENTED = "application/vnd.riskified.com; version=2";
    private static final String JSON = "application/json";

    private final List<String> accepts = new CopyOnWriteArrayList<String>();
    private HttpServer server;
    private String baseUrl;

    // A local server that records each request's Accept header and answers like the API.
    @Before
    public void startServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            accepts.add(exchange.getRequestHeaders().getFirst("Accept"));
            byte[] body = "{\"order\":{\"id\":\"1\"},\"checkout\":{\"id\":\"1\"}}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        server.start();
        baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
    }

    @After
    public void stopServer() {
        server.stop(0);
    }

    private RiskifiedClient client() throws RiskifiedError {
        return new RiskifiedClient("shop.example.com", "auth-key", Validation.NONE,
                baseUrl, baseUrl, baseUrl, baseUrl, baseUrl, baseUrl);
    }

    @Test
    public void orderEndpointsSendTheDocumentedValue() throws Exception {
        Order order = new Order();
        order.setId("1");

        client().submitOrder(order);
        client().checkoutDeniedOrder(new CheckoutDeniedOrder("1"));

        assertEquals(2, accepts.size());
        assertEquals(DOCUMENTED, accepts.get(0));
        assertEquals(DOCUMENTED, accepts.get(1));
    }

    @Test
    public void otpSendsJson() throws Exception {
        client().InitiateOtp(new OtpInitiate("1", "token", "en", "jane@example.com", null));

        assertEquals(1, accepts.size());
        assertEquals(JSON, accepts.get(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void legacyWireFormatSendsJsonEverywhere() throws Exception {
        RiskifiedClient client = new RiskifiedClient.RiskifiedClientBuilder("shop.example.com", "auth-key", Environment.SANDBOX)
                .setLegacyWireFormat(true)
                .build();

        HttpPost order = client.createPostRequest("https://sandbox.riskified.com/api/submit");
        HttpPost otp = client.createPostRequest("https://otp-sandbox.self-veri.com/recover/v1/otp/initiate", JSON);

        assertEquals(JSON, order.getFirstHeader("Accept").getValue());
        assertEquals(JSON, otp.getFirstHeader("Accept").getValue());
    }

    @Test
    public void sendsOneAcceptHeader() throws Exception {
        HttpPost request = client().createPostRequest(baseUrl + "/api/submit");

        assertEquals(1, request.getHeaders("Accept").length);
    }
}
