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

    @Test
    public void testBankWirePlaidScalarFieldsSerializeWithCorrectKeys() {
        BankWirePaymentDetails bankWire = new BankWirePaymentDetails("123456789", "021000021");
        bankWire.setDaysSinceAccountOpening(90);
        bankWire.setDaysWithNegativeBalanceCount(4);
        bankWire.setIsSavingsOrMoneyMarketAccount(true);
        bankWire.setNsfOverdraftTransactionsCount(31);
        bankWire.setUnauthorizedTransactionsCount(2);

        String json = gson.toJson(bankWire);

        assertTrue(json.contains("\"days_since_account_opening\":90"));
        assertTrue(json.contains("\"days_with_negative_balance_count\":4"));
        assertTrue(json.contains("\"is_savings_or_money_market_account\":true"));
        assertTrue(json.contains("\"nsf_overdraft_transactions_count\":31"));
        assertTrue(json.contains("\"unauthorized_transactions_count\":2"));
    }

    @Test
    public void testBankWirePlaidScoresGetterAndSetter() {
        BankWirePaymentDetails bankWire = new BankWirePaymentDetails("123456789", "021000021");

        InitiatedReturnRisk customerRisk = new InitiatedReturnRisk();
        customerRisk.setScore(9);
        customerRisk.setRiskTier(1);

        InitiatedReturnRisk bankRisk = new InitiatedReturnRisk();
        bankRisk.setScore(82);
        bankRisk.setRiskTier(7);

        PlaidScores plaidScores = new PlaidScores();
        plaidScores.setCustomerInitiatedReturnRisk(customerRisk);
        plaidScores.setBankInitiatedReturnRisk(bankRisk);

        bankWire.setPlaidScores(plaidScores);

        assertEquals(9, bankWire.getPlaidScores().getCustomerInitiatedReturnRisk().getScore());
        assertEquals(1, bankWire.getPlaidScores().getCustomerInitiatedReturnRisk().getRiskTier());
        assertEquals(82, bankWire.getPlaidScores().getBankInitiatedReturnRisk().getScore());
        assertEquals(7, bankWire.getPlaidScores().getBankInitiatedReturnRisk().getRiskTier());
    }

    @Test
    public void testBankWirePlaidScoresSerializeWithCorrectStructure() {
        BankWirePaymentDetails bankWire = new BankWirePaymentDetails("123456789", "021000021");

        InitiatedReturnRisk customerRisk = new InitiatedReturnRisk();
        customerRisk.setScore(9);
        customerRisk.setRiskTier(1);

        InitiatedReturnRisk bankRisk = new InitiatedReturnRisk();
        bankRisk.setScore(82);
        bankRisk.setRiskTier(7);

        PlaidScores plaidScores = new PlaidScores();
        plaidScores.setCustomerInitiatedReturnRisk(customerRisk);
        plaidScores.setBankInitiatedReturnRisk(bankRisk);

        bankWire.setPlaidScores(plaidScores);

        String json = gson.toJson(bankWire);

        assertTrue(json.contains("\"plaid_scores\""));
        assertTrue(json.contains("\"customer_initiated_return_risk\""));
        assertTrue(json.contains("\"bank_initiated_return_risk\""));
        assertTrue(json.contains("\"score\":9"));
        assertTrue(json.contains("\"risk_tier\":1"));
        assertTrue(json.contains("\"score\":82"));
        assertTrue(json.contains("\"risk_tier\":7"));
    }

    @Test
    public void testBankWirePlaidScoresNullByDefault() {
        BankWirePaymentDetails bankWire = new BankWirePaymentDetails("123456789", "021000021");
        assertNull(bankWire.getPlaidScores());
    }
}
