package com.riskified.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.riskified.JSONFormater;
import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validation;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

        assertTrue(json.contains("\"days_since_account_opening\":90"));
        assertTrue(json.contains("\"days_with_negative_balance_count\":4"));
        assertTrue(json.contains("\"is_savings_or_money_market_account\":true"));
        assertTrue(json.contains("\"nsf_overdraft_transactions_count\":31"));
        assertTrue(json.contains("\"unauthorized_transactions_count\":2"));
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

    @Test
    public void testWalletPaymentTypeSetFromConstructor() {
        WalletPaymentDetails wallet = new WalletPaymentDetails(PaymentType.APPLE_PAY, "12345", "X");
        assertEquals(PaymentType.APPLE_PAY, wallet.getPaymentType());
    }

    @Test
    public void testWalletPaymentTypeSerializesToWalletValue() {
        WalletPaymentDetails wallet = new WalletPaymentDetails(PaymentType.APPLE_PAY, "12345", "X");
        String json = gson.toJson(wallet);
        assertTrue(json.contains("\"payment_type\":\"apple_pay\""));
    }

    @Test
    public void testWalletNewFieldsSerializeWithCorrectKeys() {
        WalletPaymentDetails wallet = new WalletPaymentDetails(PaymentType.GOOGLE_PAY, "12345", "X");
        wallet.setCreditCardToken("tok_1A2b3C4d5E6f7G8h9I");
        wallet.setInitialPaymentAmount(400.0);
        wallet.setPaymentFrequency(1);
        wallet.setBillingAddressId("addr_bill_01");

        String json = gson.toJson(wallet);

        assertTrue(json.contains("\"credit_card_token\":\"tok_1A2b3C4d5E6f7G8h9I\""));
        assertTrue(json.contains("\"initial_payment_amount\":400"));
        assertTrue(json.contains("\"payment_frequency\":1"));
        assertTrue(json.contains("\"billing_address_id\":\"addr_bill_01\""));
    }

    @Test
    public void testWalletValidatePassesForValidWallet() throws FieldBadFormatException {
        WalletPaymentDetails wallet = new WalletPaymentDetails(PaymentType.SAMSUNG_PAY, "12345", "X");
        wallet.setCreditCardCountry("US");
        wallet.setAcquirerRegion("NONEU");
        wallet.setExpiryMonth(12);
        wallet.setExpiryYear(2028);
        wallet.validate(Validation.ALL);
    }

    @Test(expected = FieldBadFormatException.class)
    public void testWalletValidateRejectsNonWalletPaymentType() throws FieldBadFormatException {
        WalletPaymentDetails wallet = new WalletPaymentDetails(PaymentType.CARD, "12345", "X");
        wallet.validate(Validation.ALL);
    }

    @Test(expected = FieldBadFormatException.class)
    public void testWalletValidateRejectsBadAcquirerRegion() throws FieldBadFormatException {
        WalletPaymentDetails wallet = new WalletPaymentDetails(PaymentType.ALIPAY, "12345", "X");
        wallet.setAcquirerRegion("ASIA");
        wallet.validate(Validation.ALL);
    }

    @Test
    public void testWalletSerializesWithMethodDiscriminator() {
        Gson polymorphicGson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(JSONFormater.paymentDetailsSerializer())
                .create();

        List<IPaymentDetails> paymentDetails = new ArrayList<IPaymentDetails>();
        paymentDetails.add(new WalletPaymentDetails(PaymentType.WECHAT_PAY, "12345", "X"));

        String json = polymorphicGson.toJson(paymentDetails, new TypeToken<List<IPaymentDetails>>() {}.getType());

        assertTrue(json.contains("\"method\":\"digital_wallet\""));
        assertTrue(json.contains("\"payment_type\":\"wechat_pay\""));
    }
}
