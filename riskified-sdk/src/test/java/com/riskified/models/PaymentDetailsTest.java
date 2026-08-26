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

    /**
     * {@code payment_details} carries <b>no</b> type discriminator on the wire.
     *
     * <p>
     * The variant is expressed by which keys are present, with the constant {@code payment_type}
     * acting as the de-facto discriminator — see {@code docs/flows/01-model-catalog.md} section 6.
     * This SDK used to register a {@code RuntimeTypeAdapterFactory} that injected a {@code "method"}
     * key whose values ({@code credit_card}, {@code bank_wire}, {@code digital_wallet}) disagreed
     * with the {@code payment_type} emitted right next to it. No other SDK in the fleet sent it.
     * Unrecognised keys are dropped rather than rejected, which is why it went unnoticed.
     */
    @Test
    public void testPaymentDetailsCarryNoMethodDiscriminator() {
        List<IPaymentDetails> inputPaymentDetails = new ArrayList<IPaymentDetails>();
        inputPaymentDetails.add(new WalletPaymentDetails(PaymentType.WECHAT_PAY, "12345", "X"));
        inputPaymentDetails.add(new CreditCardPaymentDetails("411111", "Y", "M", "XXXX-1234", "Visa"));
        inputPaymentDetails.add(new BankWirePaymentDetails("123456789", "021000021"));
        inputPaymentDetails.add(new PaypalPaymentDetails("buyer@example.com", "verified", "confirmed", "eligible"));
        // Java-only variant, present in no other SDK and in no spec. Kept in place deliberately; it
        // must simply stop emitting "method" like every other variant.
        inputPaymentDetails.add(new StripePaymentDetails("auth-1"));

        String actualJson = JSONFormater.toJson(inputPaymentDetails);

        assertFalse("payment_details must carry no type discriminator: " + actualJson,
                actualJson.contains("\"method\""));
        assertFalse(actualJson, actualJson.contains("digital_wallet"));
        assertFalse(actualJson, actualJson.contains("bank_wire"));
    }

    /**
     * Dropping the discriminator must not cost the concrete fields. Gson dispatches on each
     * element's runtime type inside a {@code List<IPaymentDetails>}, so the variant-specific keys —
     * and the {@code payment_type} that actually identifies the variant — still ship.
     */
    @Test
    public void testEachVariantStillSerializesItsOwnPaymentType() {
        List<IPaymentDetails> inputPaymentDetails = new ArrayList<IPaymentDetails>();
        inputPaymentDetails.add(new WalletPaymentDetails(PaymentType.WECHAT_PAY, "12345", "X"));
        inputPaymentDetails.add(new CreditCardPaymentDetails("411111", "Y", "M", "XXXX-1234", "Visa"));
        inputPaymentDetails.add(new BankWirePaymentDetails("123456789", "021000021"));
        inputPaymentDetails.add(new PaypalPaymentDetails("buyer@example.com", "verified", "confirmed", "eligible"));

        String actualJson = JSONFormater.toJson(inputPaymentDetails);

        assertTrue(actualJson, actualJson.contains("\"payment_type\":\"wechat_pay\""));
        assertTrue(actualJson, actualJson.contains("\"payment_type\":\"card\""));
        assertTrue(actualJson, actualJson.contains("\"payment_type\":\"bank_transfer\""));
        assertTrue(actualJson, actualJson.contains("\"payment_type\":\"paypal\""));
        assertTrue(actualJson, actualJson.contains("\"credit_card_bin\":\"411111\""));
        assertTrue(actualJson, actualJson.contains("\"routing_number\":\"021000021\""));
        assertTrue(actualJson, actualJson.contains("\"authorization_id\":\"12345\""));
    }

    /** The order payload itself must not carry the injected key either. */
    @Test
    public void testOrderPayloadCarriesNoMethodDiscriminator() {
        List<IPaymentDetails> inputPaymentDetails = new ArrayList<IPaymentDetails>();
        inputPaymentDetails.add(new CreditCardPaymentDetails("411111", "Y", "M", "XXXX-1234", "Visa"));
        Order inputOrder = new Order();
        inputOrder.setPaymentDetails(inputPaymentDetails);

        String actualJson = JSONFormater.toJson(inputOrder);

        assertTrue(actualJson, actualJson.contains("\"payment_details\""));
        assertFalse(actualJson, actualJson.contains("\"method\""));
        assertTrue(actualJson, actualJson.contains("\"payment_type\":\"card\""));
    }
}
