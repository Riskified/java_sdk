package com.riskified.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaymentDetailsTest {

    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Test
    public void testCreditCardDefaultPaymentType() {
        CreditCardPaymentDetails cc = new CreditCardPaymentDetails("411111", "Y", "M", "XXXX-1234", "Visa");
        assertEquals(PaymentType.CARD, cc.getPaymentType());
    }

    @Test
    public void testCreditCardPaymentTypeSerializesToCard() {
        CreditCardPaymentDetails cc = new CreditCardPaymentDetails("411111", "Y", "M", "XXXX-1234", "Visa");
        String json = gson.toJson(cc);
        assertTrue(json.contains("\"payment_type\":\"card\""));
    }

    @Test
    public void testPaypalDefaultPaymentType() {
        PaypalPaymentDetails paypal = new PaypalPaymentDetails("buyer@example.com", "verified", "confirmed", "eligible");
        assertEquals(PaymentType.PAYPAL, paypal.getPaymentType());
    }

    @Test
    public void testPaypalPaymentTypeSerializesToPaypal() {
        PaypalPaymentDetails paypal = new PaypalPaymentDetails("buyer@example.com", "verified", "confirmed", "eligible");
        String json = gson.toJson(paypal);
        assertTrue(json.contains("\"payment_type\":\"paypal\""));
    }

    @Test
    public void testBankWireDefaultPaymentType() {
        BankWirePaymentDetails bankWire = new BankWirePaymentDetails("123456789", "021000021");
        assertEquals(PaymentType.BANK_TRANSFER, bankWire.getPaymentType());
    }

    @Test
    public void testBankWirePaymentTypeSerializesToBankTransfer() {
        BankWirePaymentDetails bankWire = new BankWirePaymentDetails("123456789", "021000021");
        String json = gson.toJson(bankWire);
        assertTrue(json.contains("\"payment_type\":\"bank_transfer\""));
    }
}
