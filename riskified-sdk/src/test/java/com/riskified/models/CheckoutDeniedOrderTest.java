package com.riskified.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.riskified.JSONFormater;
import com.riskified.JSONFormaterTestAccess;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("deprecation") // the root-level authorization error is what's being bridged
public class CheckoutDeniedOrderTest {

    private static final AuthorizationError ROOT_ERROR = new AuthorizationError("card_declined", new Date(0));

    private static CreditCardPaymentDetails card() {
        return new CreditCardPaymentDetails("411111", "Y", "M", "XXXX-1111", "Visa");
    }

    private static JsonArray paymentDetails(String json) {
        return JsonParser.parseString(json).getAsJsonObject().getAsJsonArray("payment_details");
    }

    private static String errorCode(JsonArray details, int index) {
        return details.get(index).getAsJsonObject().getAsJsonObject("authorization_error").get("error_code").getAsString();
    }

    @Test
    public void copiesRootErrorIntoCardWalletAndPaypal() {
        CheckoutDeniedOrder order = new CheckoutDeniedOrder("1", ROOT_ERROR);
        order.setPaymentDetails(Arrays.asList(card(),
                new WalletPaymentDetails(PaymentType.APPLE_PAY, "auth", "Y"),
                new PaypalPaymentDetails("payer@example.com", "verified", "confirmed", "Eligible")));

        JsonArray details = paymentDetails(JSONFormater.toJson(order));

        assertEquals("card_declined", errorCode(details, 0));
        assertEquals("card_declined", errorCode(details, 1));
        assertEquals("card_declined", errorCode(details, 2));
    }

    @Test
    public void keepsThePaymentDetailsOwnError() {
        CreditCardPaymentDetails card = card();
        card.setAuthorizationError(new AuthorizationError("insufficient_funds", new Date(0)));
        CheckoutDeniedOrder order = new CheckoutDeniedOrder("1", ROOT_ERROR);
        order.setPaymentDetails(Collections.singletonList(card));

        assertEquals("insufficient_funds", errorCode(paymentDetails(JSONFormater.toJson(order)), 0));
    }

    @Test
    public void doesNotCopyIntoTypesWithoutTheField() {
        CheckoutDeniedOrder order = new CheckoutDeniedOrder("1", ROOT_ERROR);
        order.setPaymentDetails(Arrays.asList(new BankWirePaymentDetails("000123", "110000000"),
                new BuyNowPayLaterPaymentDetails(), new NonChbPaymentDetails(PaymentType.GIFT_CARD)));

        JsonArray details = paymentDetails(JSONFormater.toJson(order));

        for (int i = 0; i < details.size(); i++) {
            assertFalse(details.get(i).getAsJsonObject().has("authorization_error"));
        }
    }

    @Test
    public void keepsTheRootKey() {
        CheckoutDeniedOrder order = new CheckoutDeniedOrder("1", ROOT_ERROR);
        order.setPaymentDetails(Collections.singletonList(card()));

        JsonObject json = JsonParser.parseString(JSONFormater.toJson(order)).getAsJsonObject();

        assertEquals("card_declined", json.getAsJsonObject("authorization_error").get("error_code").getAsString());
    }

    @Test
    public void legacyWireFormatSendsTheErrorAtTheRootOnly() {
        CheckoutDeniedOrder order = new CheckoutDeniedOrder("1", ROOT_ERROR);
        order.setPaymentDetails(Collections.singletonList(card()));

        String json = JSONFormaterTestAccess.toJson(order, true);

        assertTrue(JsonParser.parseString(json).getAsJsonObject().has("authorization_error"));
        assertFalse(paymentDetails(json).get(0).getAsJsonObject().has("authorization_error"));
    }

    @Test
    public void addsNothingWithoutARootError() {
        CheckoutDeniedOrder order = new CheckoutDeniedOrder("1");
        order.setPaymentDetails(Collections.singletonList(card()));

        String json = JSONFormater.toJson(order);

        assertFalse(JsonParser.parseString(json).getAsJsonObject().has("authorization_error"));
        assertFalse(paymentDetails(json).get(0).getAsJsonObject().has("authorization_error"));
    }

    @Test
    public void leavesTheMerchantsObjectsUnchanged() {
        CreditCardPaymentDetails card = card();
        CheckoutDeniedOrder order = new CheckoutDeniedOrder("1", ROOT_ERROR);
        order.setPaymentDetails(Collections.singletonList(card));

        JSONFormater.toJson(order);

        assertNull(card.getAuthorizationError());
    }
}
