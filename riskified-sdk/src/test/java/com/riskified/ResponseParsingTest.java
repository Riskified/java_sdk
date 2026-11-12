package com.riskified;

import com.riskified.models.CheckoutResponse;
import com.riskified.models.ResOrder;
import com.riskified.models.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Checkout responses ({@code checkout_create}, {@code advise}, {@code screen}, {@code checkout_denied}) and
 * order responses must bind multi-word keys whether the server sends them in snake_case or camelCase.
 */
public class ResponseParsingTest {

    // Every multi-word field reachable from Response, in snake_case. "%s" is the order wrapper key.
    private static final String SNAKE_CASE_BODY = "{"
            + "\"%s\":{"
            + "\"id\":\"order-1\",\"status\":\"approved\",\"description\":\"Reviewed\","
            + "\"old_status\":\"captured\",\"decision_code\":\"D-1\",\"risk_score\":12.5,"
            + "\"advice\":{\"in_regulatory_scope\":true,\"safe_order\":true,"
            + "\"recommendations\":[{\"type\":\"psd2\",\"recommendation\":\"tra\"}]},"
            + "\"recovery_eligibility\":{\"id\":\"rec-1\",\"status\":\"sent\",\"verified_at\":\"2024-01-15\","
            + "\"verification_method\":{\"type\":\"email\",\"value\":\"jane@example.com\"},\"challenge_type\":\"otp\","
            + "\"verification_session_details\":{\"cart_token\":\"cart-1\",\"browser_ip\":\"203.0.113.10\"}},"
            + "\"policy_protect\":{\"use_cases\":[{\"use_case\":\"returns\",\"decision\":\"approve\"}],"
            + "\"business_decision\":\"approve\"}"
            + "},"
            + "\"login_id\":\"login-1\",\"widget_token\":\"widget-1\","
            + "\"verification_data\":{\"email\":\"jane@example.com\"}"
            + "}";

    private RiskifiedClient client;

    @Before
    public void setUp() throws Exception {
        client = new RiskifiedClient("shop.example.com", "auth-key", Environment.SANDBOX);
    }

    @Test
    public void checkoutResponseBindsSnakeCaseKeys() throws Exception {
        CheckoutResponse response = client.getCheckoutResponseObject(String.format(SNAKE_CASE_BODY, "checkout"));
        assertAllFieldsBound(response);
    }

    @Test
    public void checkoutResponseStillBindsCamelCaseKeys() throws Exception {
        CheckoutResponse response = client.getCheckoutResponseObject(camelCase(String.format(SNAKE_CASE_BODY, "checkout")));
        assertAllFieldsBound(response);
    }

    @Test
    public void orderResponseBindsSnakeCaseKeys() throws Exception {
        Response response = client.getResponseObject(String.format(SNAKE_CASE_BODY, "order"));
        assertAllFieldsBound(response);
    }

    @Test
    public void orderResponseAlsoBindsCamelCaseKeys() throws Exception {
        Response response = client.getResponseObject(camelCase(String.format(SNAKE_CASE_BODY, "order")));
        assertAllFieldsBound(response);
    }

    // The documented /checkout_denied response.
    @Test
    public void checkoutDeniedResponseFromDocs() throws Exception {
        CheckoutResponse response = client.getCheckoutResponseObject("{\"checkout\":{\"id\":\"53953819\",\"status\":\"denied\"}}");
        assertEquals("53953819", response.getOrder().getId());
        assertEquals("denied", response.getOrder().getStatus());
    }

    private static void assertAllFieldsBound(Response response) {
        assertEquals("login-1", response.getLoginId());
        assertEquals("widget-1", response.getOtpwidgetToken());
        assertEquals("jane@example.com", response.getVerificationData().getEmail());

        ResOrder order = response.getOrder();
        assertEquals("order-1", order.getId());
        assertEquals("captured", order.getOldStatus());
        assertEquals("D-1", order.getDecisionCode());
        assertEquals(Double.valueOf(12.5), order.getRiskScore());
        assertTrue(order.getAdvice().getRegulatoryScope());
        assertTrue(order.getAdvice().getSafeOrder());
        assertEquals("tra", order.getAdvice().getRecommendation().get(0).getRecommendation());
        assertEquals("2024-01-15", order.getRecoveryEligibility().getVerifiedAt());
        assertEquals("jane@example.com", order.getRecoveryEligibility().getVerificationMethod().getValue());
        assertEquals("otp", order.getRecoveryEligibility().getChallengeType());
        assertEquals("cart-1", order.getRecoveryEligibility().getVerificationSessionDetails().getCartToken());
        assertEquals("203.0.113.10", order.getRecoveryEligibility().getVerificationSessionDetails().getBrowserIp());
        assertEquals("returns", order.getPolicyProtect().getUseCases().get(0).getUseCase());
        assertEquals("approve", order.getPolicyProtect().getBusiness_decision());
    }

    // The spellings plain Gson bound before: the Java field names.
    private static String camelCase(String json) {
        String[][] keys = {
                {"old_status", "oldStatus"}, {"decision_code", "decisionCode"}, {"risk_score", "riskScore"},
                {"in_regulatory_scope", "inRegulatoryScope"}, {"safe_order", "safeOrder"},
                {"recovery_eligibility", "recoveryEligibility"}, {"verified_at", "verifiedAt"},
                {"verification_method", "verificationMethod"}, {"challenge_type", "challengeType"},
                {"verification_session_details", "verificationSessionDetails"}, {"cart_token", "cartToken"},
                {"browser_ip", "browserIp"}, {"policy_protect", "policyProtect"}, {"use_cases", "useCases"},
                {"use_case", "useCase"}, {"login_id", "loginId"}, {"widget_token", "WidgetToken"},
                {"verification_data", "verificationData"},
        };
        for (String[] key : keys) {
            json = json.replace("\"" + key[0] + "\"", "\"" + key[1] + "\"");
        }
        return json;
    }
}
