package com.riskified.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import com.riskified.validations.*;

public class WalletPaymentDetails implements IPaymentDetails {

    private static final List<PaymentType> WALLET_PAYMENT_TYPES = Arrays.asList(
            PaymentType.APPLE_PAY,
            PaymentType.GOOGLE_PAY,
            PaymentType.SAMSUNG_PAY,
            PaymentType.WECHAT_PAY,
            PaymentType.AMAZON_PAY,
            PaymentType.ALIPAY);

    private static final List<String> VALID_ACQUIRER_REGIONS = Arrays.asList("EU", "NONEU");

    // A rule = a check plus the message to raise if it fails.
    // Add new rules here instead of adding branches in validate().
    private static final List<Rule> RULES = Arrays.asList(
            new Rule(p -> p.paymentType != null, "Payment Type can't be null."),
            new Rule(p -> p.paymentType == null || WALLET_PAYMENT_TYPES.contains(p.paymentType),
                    "Payment Type must be one of: " + supportedPaymentTypes()),
            new Rule(p -> p.authorizationId != null && !p.authorizationId.isEmpty(),
                    "Authorization Id can't be null or empty."),
            new Rule(p -> p.avsResultCode != null && !p.avsResultCode.isEmpty(),
                    "AVS Result Code can't be null or empty."),
            new Rule(p -> p.creditCardCountry == null || isValidCountryCode(p.creditCardCountry),
                    "Credit Card Country is not a valid ISO country code."),
            new Rule(p -> p.acquirerRegion == null || VALID_ACQUIRER_REGIONS.contains(p.acquirerRegion),
                    "Acquirer Region must be one of: " + VALID_ACQUIRER_REGIONS),
            new Rule(p -> p.expiryMonth == null || (p.expiryMonth >= 1 && p.expiryMonth <= 12),
                    "Expiry Month must be between 01 and 12"),
            new Rule(p -> p.expiryYear == null || (p.expiryYear >= 1900 && p.expiryYear <= 9999),
                    "Expiry Year must be a 4-digit integer formatted as YYYY"));

    private PaymentType paymentType;
    private String avsResultCode;
    private String creditCardCompany;
    private String creditCardCountry;
    private String creditCardToken;
    private String cardholderName;
    private String authorizationId;
    private String mid;
    private String id;
    private Date storedPaymentCreatedAt;
    private Date storedPaymentUpdatedAt;
    private Integer installments;
    private String acquirerBin;
    private String acquirerRegion;
    private AuthorizationType authorizationType;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Double initialPaymentAmount;
    private Integer paymentFrequency;
    private String billingAddressId;
    private AuthenticationResult authenticationResult;

    public WalletPaymentDetails(PaymentType paymentType, String authorizationId, String avsResultCode) {
        this.paymentType = paymentType;
        this.authorizationId = authorizationId;
        this.avsResultCode = avsResultCode;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType != Validation.ALL) {
            return;
        }
        for (Rule rule : RULES) {
            if (!rule.passes(this)) {
                throw new FieldBadFormatException(this, rule.message());
            }
        }
    }

    private static String supportedPaymentTypes() {
        List<String> names = new ArrayList<String>(WALLET_PAYMENT_TYPES.size());
        for (PaymentType type : WALLET_PAYMENT_TYPES) {
            names.add(type.name().toLowerCase());
        }
        return names.toString();
    }

    private static boolean isValidCountryCode(String countryCode) {
        return Arrays.asList(Locale.getISOCountries()).contains(countryCode);
    }

    private static final class Rule {
        private final Predicate<WalletPaymentDetails> check;
        private final String message;

        Rule(Predicate<WalletPaymentDetails> check, String message) {
            this.check = check;
            this.message = message;
        }

        boolean passes(WalletPaymentDetails details) {
            return check.test(details);
        }

        String message() {
            return message;
        }
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getAvsResultCode() {
        return avsResultCode;
    }

    public void setAvsResultCode(String avsResultCode) {
        this.avsResultCode = avsResultCode;
    }

    public String getCreditCardCompany() {
        return creditCardCompany;
    }

    public void setCreditCardCompany(String creditCardCompany) {
        this.creditCardCompany = creditCardCompany;
    }

    public String getCreditCardCountry() {
        return creditCardCountry;
    }

    public void setCreditCardCountry(String creditCardCountry) {
        this.creditCardCountry = creditCardCountry;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStoredPaymentCreatedAt() {
        return storedPaymentCreatedAt;
    }

    public void setStoredPaymentCreatedAt(Date storedPaymentCreatedAt) {
        this.storedPaymentCreatedAt = storedPaymentCreatedAt;
    }

    public Date getStoredPaymentUpdatedAt() {
        return storedPaymentUpdatedAt;
    }

    public void setStoredPaymentUpdatedAt(Date storedPaymentUpdatedAt) {
        this.storedPaymentUpdatedAt = storedPaymentUpdatedAt;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public String getAcquirerBin() {
        return acquirerBin;
    }

    public void setAcquirerBin(String acquirerBin) {
        this.acquirerBin = acquirerBin;
    }

    public String getAcquirerRegion() {
        return acquirerRegion;
    }

    public void setAcquirerRegion(String acquirerRegion) {
        this.acquirerRegion = acquirerRegion;
    }

    public AuthorizationType getAuthorizationType() {
        return authorizationType;
    }

    public void setAuthorizationType(AuthorizationType authorizationType) {
        this.authorizationType = authorizationType;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public Double getInitialPaymentAmount() {
        return initialPaymentAmount;
    }

    public void setInitialPaymentAmount(Double initialPaymentAmount) {
        this.initialPaymentAmount = initialPaymentAmount;
    }

    public Integer getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(Integer paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public String getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public AuthenticationResult getAuthenticationResult() {
        return authenticationResult;
    }

    public void setAuthenticationResult(AuthenticationResult authenticationResult) {
        this.authenticationResult = authenticationResult;
    }

}
