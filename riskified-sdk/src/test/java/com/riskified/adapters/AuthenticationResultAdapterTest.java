package com.riskified.adapters;

import com.google.gson.*;
import com.riskified.TransStatus;
import com.riskified.TransStatusReason;
import com.riskified.models.AuthenticationResult;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.PaypalPaymentDetails;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for AuthenticationResultAdapterFactory to verify backward compatibility handling.
 * Tests the factory-created adapter through payment details deserialization.
 */
public class AuthenticationResultAdapterTest {

    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .create();
    }

    @Test
    public void testLegacyFormatAllOldFields() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{\"eci\":\"05\"," +
                "\"cavv\":\"AAABCZIhcQAAAABZlyFxAAAAAAA=\",\"tranStatus\":\"Y\"," +
                "\"tranStatusReason\":\"01\",\"liabilityShift\":true," +
                "\"three_d_challenge\":false,\"tra_exemption\":true}}";

        CreditCardPaymentDetails details = gson.fromJson(json, CreditCardPaymentDetails.class);

        assertNotNull("Payment details should not be null", details);
        assertNotNull("Authentication result should not be null", details.getAuthenticationResults());

        AuthenticationResult result = details.getAuthenticationResults();
        assertEquals("ECI should match", "05", result.getEci());
        assertEquals("CAVV should match", "AAABCZIhcQAAAABZlyFxAAAAAAA=", result.getCavv());
        assertEquals("TransStatus should be Y", TransStatus.Y, result.getTransStatus());
        assertEquals("TransStatusReason should be Zero_One", TransStatusReason.Zero_One, result.getTransStatusReason());
        assertTrue("Liability shift should be true", result.getLiabilityShift());
        assertFalse("3D challenge should be false", result.get3DChallenge());
        assertTrue("TRA exemption should be true", result.getTRAExemption());
    }

    @Test
    public void testCurrentFormatAllNewFields() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{\"eci\":\"05\"," +
                "\"cavv\":\"AAABCZIhcQAAAABZlyFxAAAAAAA=\",\"transStatus\":\"Y\"," +
                "\"transStatusReason\":\"01\",\"liabilityShift\":true," +
                "\"threeDChallenge\":false,\"TRA_exemption\":true}}";

        CreditCardPaymentDetails details = gson.fromJson(json, CreditCardPaymentDetails.class);

        assertNotNull("Payment details should not be null", details);
        assertNotNull("Authentication result should not be null", details.getAuthenticationResults());

        AuthenticationResult result = details.getAuthenticationResults();
        assertEquals("ECI should match", "05", result.getEci());
        assertEquals("CAVV should match", "AAABCZIhcQAAAABZlyFxAAAAAAA=", result.getCavv());
        assertEquals("TransStatus should be Y", TransStatus.Y, result.getTransStatus());
        assertEquals("TransStatusReason should be Zero_One", TransStatusReason.Zero_One, result.getTransStatusReason());
        assertTrue("Liability shift should be true", result.getLiabilityShift());
        assertFalse("3D challenge should be false", result.get3DChallenge());
        assertTrue("TRA exemption should be true", result.getTRAExemption());
    }

    @Test
    public void testMixedFormat() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{\"eci\":\"05\"," +
                "\"tranStatus\":\"N\",\"transStatusReason\":\"02\"," +
                "\"liabilityShift\":false,\"three_d_challenge\":true,\"tra_exemption\":false}}";

        CreditCardPaymentDetails details = gson.fromJson(json, CreditCardPaymentDetails.class);

        assertNotNull("Payment details should not be null", details);
        assertNotNull("Authentication result should not be null", details.getAuthenticationResults());

        AuthenticationResult result = details.getAuthenticationResults();
        assertEquals("ECI should match", "05", result.getEci());
        assertEquals("TransStatus should be N", TransStatus.N, result.getTransStatus());
        assertEquals("TransStatusReason should be Zero_TWO", TransStatusReason.Zero_TWO, result.getTransStatusReason());
        assertFalse("Liability shift should be false", result.getLiabilityShift());
        assertTrue("3D challenge should be true", result.get3DChallenge());
        assertFalse("TRA exemption should be false", result.getTRAExemption());
    }

    @Test
    public void testNullAuthenticationResult() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":null}";

        CreditCardPaymentDetails details = gson.fromJson(json, CreditCardPaymentDetails.class);

        assertNotNull("Payment details should not be null", details);
        assertNull("Authentication result should be null", details.getAuthenticationResults());
    }

    @Test
    public void testMissingAuthenticationResult() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\"}";

        CreditCardPaymentDetails details = gson.fromJson(json, CreditCardPaymentDetails.class);

        assertNotNull("Payment details should not be null", details);
        assertNull("Authentication result should be null when field is missing", details.getAuthenticationResults());
    }

    @Test
    public void testEnumValues() {
        String json1 = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{\"tranStatus\":\"A\"}}";
        CreditCardPaymentDetails details1 = gson.fromJson(json1, CreditCardPaymentDetails.class);
        assertEquals("TransStatus should be A", TransStatus.A, details1.getAuthenticationResults().getTransStatus());

        String json2 = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{\"transStatus\":\"U\"}}";
        CreditCardPaymentDetails details2 = gson.fromJson(json2, CreditCardPaymentDetails.class);
        assertEquals("TransStatus should be U", TransStatus.U, details2.getAuthenticationResults().getTransStatus());

        String json3 = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{\"tranStatusReason\":\"03\"}}";
        CreditCardPaymentDetails details3 = gson.fromJson(json3, CreditCardPaymentDetails.class);
        assertEquals("TransStatusReason should be Zero_Three", TransStatusReason.Zero_Three,
                details3.getAuthenticationResults().getTransStatusReason());
    }

    @Test(expected = JsonParseException.class)
    public void testInvalidTypeString() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":\"invalid\"}";

        gson.fromJson(json, CreditCardPaymentDetails.class);
    }

    @Test(expected = JsonParseException.class)
    public void testInvalidTypeArray() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":[{\"eci\":\"05\"}]}";

        gson.fromJson(json, CreditCardPaymentDetails.class);
    }

    @Test(expected = JsonParseException.class)
    public void testInvalidTypeNumber() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":12345}";

        gson.fromJson(json, CreditCardPaymentDetails.class);
    }

    @Test
    public void testEmptyObject() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{}}";

        CreditCardPaymentDetails details = gson.fromJson(json, CreditCardPaymentDetails.class);

        assertNotNull("Payment details should not be null", details);
        assertNotNull("Authentication result should not be null", details.getAuthenticationResults());
        assertNull("ECI should be null in empty object", details.getAuthenticationResults().getEci());
    }

    @Test
    public void testAllFieldsPresent() {
        String json = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{\"eci\":\"05\"," +
                "\"cavv\":\"AAABCZIhcQAAAABZlyFxAAAAAAA=\",\"tranStatus\":\"Y\"," +
                "\"tranStatusReason\":\"01\",\"liabilityShift\":true," +
                "\"three_d_challenge\":false,\"tra_exemption\":true}}";

        CreditCardPaymentDetails details = gson.fromJson(json, CreditCardPaymentDetails.class);

        assertNotNull("Payment details should not be null", details);
        AuthenticationResult result = details.getAuthenticationResults();
        assertNotNull("Authentication result should not be null", result);

        assertEquals("ECI should match", "05", result.getEci());
        assertEquals("CAVV should match", "AAABCZIhcQAAAABZlyFxAAAAAAA=", result.getCavv());
        assertEquals("TransStatus should match", TransStatus.Y, result.getTransStatus());
        assertEquals("TransStatusReason should match", TransStatusReason.Zero_One, result.getTransStatusReason());
        assertTrue("Liability shift should be true", result.getLiabilityShift());
        assertFalse("3D challenge should be false", result.get3DChallenge());
        assertTrue("TRA exemption should be true", result.getTRAExemption());
    }

    @Test
    public void testBooleanFieldTypes() {
        // Test with true values
        String json1 = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{" +
                "\"liabilityShift\":true,\"three_d_challenge\":true,\"tra_exemption\":true}}";

        CreditCardPaymentDetails details1 = gson.fromJson(json1, CreditCardPaymentDetails.class);
        AuthenticationResult result1 = details1.getAuthenticationResults();
        assertTrue("Liability shift should be true", result1.getLiabilityShift());
        assertTrue("3D challenge should be true", result1.get3DChallenge());
        assertTrue("TRA exemption should be true", result1.getTRAExemption());

        // Test with false values
        String json2 = "{\"credit_card_bin\":\"123456\",\"credit_card_number\":\"****1234\"," +
                "\"credit_card_company\":\"Visa\",\"authenticationResult\":{" +
                "\"liabilityShift\":false,\"three_d_challenge\":false,\"tra_exemption\":false}}";

        CreditCardPaymentDetails details2 = gson.fromJson(json2, CreditCardPaymentDetails.class);
        AuthenticationResult result2 = details2.getAuthenticationResults();
        assertFalse("Liability shift should be false", result2.getLiabilityShift());
        assertFalse("3D challenge should be false", result2.get3DChallenge());
        assertFalse("TRA exemption should be false", result2.getTRAExemption());
    }

    @Test
    public void testPaypalPaymentDetails() {
        String json = "{\"payer_email\":\"test@example.com\",\"payer_status\":\"verified\"," +
                "\"payer_address_status\":\"confirmed\",\"protection_eligibility\":\"eligible\"," +
                "\"authenticationResult\":{\"eci\":\"07\",\"tranStatus\":\"Y\"," +
                "\"tranStatusReason\":\"01\",\"liabilityShift\":true," +
                "\"three_d_challenge\":false,\"tra_exemption\":true}}";

        PaypalPaymentDetails details = gson.fromJson(json, PaypalPaymentDetails.class);

        assertNotNull("Payment details should not be null", details);
        assertNotNull("Authentication result should not be null", details.getAuthenticationResult());

        AuthenticationResult result = details.getAuthenticationResult();
        assertEquals("ECI should match", "07", result.getEci());
        assertEquals("TransStatus should be Y", TransStatus.Y, result.getTransStatus());
        assertEquals("TransStatusReason should be Zero_One", TransStatusReason.Zero_One, result.getTransStatusReason());
        assertTrue("Liability shift should be true", result.getLiabilityShift());
        assertFalse("3D challenge should be false", result.get3DChallenge());
        assertTrue("TRA exemption should be true", result.getTRAExemption());
    }
}
