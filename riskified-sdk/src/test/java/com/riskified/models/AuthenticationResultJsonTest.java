package com.riskified.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.riskified.TransStatus;
import com.riskified.TransStatusReason;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Checks that AuthenticationResult reads every key spelling merchants have used, and writes the documented one, both
 * with a plain Gson and with the snake_case Gson the SDK itself uses.
 */
@RunWith(Parameterized.class)
public class AuthenticationResultJsonTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> gsons() {
        return Arrays.asList(new Object[][] {
                {"plain", new Gson()},
                {"snake_case", new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()},
        });
    }

    private final Gson gson;
    private final boolean snakeCase;

    public AuthenticationResultJsonTest(String name, Gson gson) {
        this.gson = gson;
        this.snakeCase = name.equals("snake_case");
    }

    // Keys without @SerializedName follow the Gson instance's naming policy.
    private String liabilityShiftKey() {
        return snakeCase ? "liability_shift" : "liabilityShift";
    }

    private String authenticationResultKey() {
        return snakeCase ? "authentication_result" : "authenticationResult";
    }

    private AuthenticationResult read(String transStatus, String transStatusReason, String threeDChallenge, String traExemption) {
        String json = "{\"eci\":\"05\",\"cavv\":\"AAABCZIhcQAAAABZlyFxAAAAAAA=\",\"" + liabilityShiftKey() + "\":true,"
                + "\"" + transStatus + "\":\"Y\",\"" + transStatusReason + "\":\"01\","
                + "\"" + threeDChallenge + "\":false,\"" + traExemption + "\":true}";
        return gson.fromJson(json, AuthenticationResult.class);
    }

    private static void assertAllFields(AuthenticationResult result) {
        assertNotNull(result);
        assertEquals("05", result.getEci());
        assertEquals("AAABCZIhcQAAAABZlyFxAAAAAAA=", result.getCavv());
        assertTrue(result.getLiabilityShift());
        assertEquals(TransStatus.Y, result.getTransStatus());
        assertEquals(TransStatusReason.Zero_One, result.getTransStatusReason());
        assertFalse(result.get3DChallenge());
        assertTrue(result.getTRAExemption());
    }

    @Test
    public void readsDocumentedKeys() {
        assertAllFields(read("trans_status", "trans_status_reason", "three_d_challenge", "TRA_exemption"));
    }

    @Test
    public void readsCamelCaseKeys() {
        assertAllFields(read("transStatus", "transStatusReason", "threeDChallenge", "traExemption"));
    }

    @Test
    public void readsLegacyCamelCaseKeys() {
        assertAllFields(read("tranStatus", "tranStatusReason", "three_d_challenge", "tra_exemption"));
    }

    @Test
    public void readsLegacySnakeCaseKeys() {
        assertAllFields(read("tran_status", "tran_status_reason", "three_d_challenge", "tra_exemption"));
    }

    @Test
    public void readsMixedKeys() {
        assertAllFields(read("tranStatus", "trans_status_reason", "threeDChallenge", "tra_exemption"));
    }

    @Test
    public void writesDocumentedKeys() {
        AuthenticationResult result = read("tranStatus", "tranStatusReason", "three_d_challenge", "tra_exemption");
        JsonObject written = JsonParser.parseString(gson.toJson(result)).getAsJsonObject();

        assertEquals("Y", written.get("trans_status").getAsString());
        assertEquals("01", written.get("trans_status_reason").getAsString());
        assertFalse(written.get("three_d_challenge").getAsBoolean());
        assertTrue(written.get("TRA_exemption").getAsBoolean());
        assertTrue(written.get(liabilityShiftKey()).getAsBoolean());
        for (String legacy : new String[] {"tranStatus", "tran_status", "transStatus", "threeDChallenge", "tra_exemption", "traExemption"}) {
            assertFalse("unexpected key " + legacy, written.has(legacy));
        }
    }

    @Test
    public void readsEnumValues() {
        assertEquals(TransStatus.A, gson.fromJson("{\"tranStatus\":\"A\"}", AuthenticationResult.class).getTransStatus());
        assertEquals(TransStatus.U, gson.fromJson("{\"trans_status\":\"U\"}", AuthenticationResult.class).getTransStatus());
        assertEquals(TransStatusReason.Zero_Three,
                gson.fromJson("{\"tran_status_reason\":\"03\"}", AuthenticationResult.class).getTransStatusReason());
    }

    @Test
    public void readsEmptyObject() {
        AuthenticationResult result = gson.fromJson("{}", AuthenticationResult.class);
        assertNotNull(result);
        assertNull(result.getEci());
        assertNull(result.getTransStatus());
    }

    @Test
    public void readsInsideCreditCardPaymentDetails() {
        String json = "{\"" + authenticationResultKey() + "\":{\"eci\":\"05\",\"tran_status\":\"N\",\"three_d_challenge\":true}}";
        AuthenticationResult result = gson.fromJson(json, CreditCardPaymentDetails.class).getAuthenticationResults();
        assertEquals("05", result.getEci());
        assertEquals(TransStatus.N, result.getTransStatus());
        assertTrue(result.get3DChallenge());
    }

    @Test
    public void readsInsidePaypalPaymentDetails() {
        String json = "{\"" + authenticationResultKey() + "\":{\"eci\":\"07\",\"tranStatus\":\"Y\",\"tra_exemption\":true}}";
        AuthenticationResult result = gson.fromJson(json, PaypalPaymentDetails.class).getAuthenticationResult();
        assertEquals("07", result.getEci());
        assertEquals(TransStatus.Y, result.getTransStatus());
        assertTrue(result.getTRAExemption());
    }

    @Test
    public void readsNullAndMissingAuthenticationResult() {
        assertNull(gson.fromJson("{\"" + authenticationResultKey() + "\":null}", CreditCardPaymentDetails.class)
                .getAuthenticationResults());
        assertNull(gson.fromJson("{}", CreditCardPaymentDetails.class).getAuthenticationResults());
    }

    @Test(expected = JsonParseException.class)
    public void rejectsString() {
        gson.fromJson("{\"" + authenticationResultKey() + "\":\"invalid\"}", CreditCardPaymentDetails.class);
    }

    @Test(expected = JsonParseException.class)
    public void rejectsArray() {
        gson.fromJson("{\"" + authenticationResultKey() + "\":[{\"eci\":\"05\"}]}", CreditCardPaymentDetails.class);
    }

    @Test(expected = JsonParseException.class)
    public void rejectsNumber() {
        gson.fromJson("{\"" + authenticationResultKey() + "\":12345}", CreditCardPaymentDetails.class);
    }
}
