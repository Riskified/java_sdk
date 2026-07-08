package com.riskified.models;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.riskified.validations.*;

public class WalletPaymentDetails implements IPaymentDetails {

    private static final List<PaymentType> WALLET_PAYMENT_TYPES = Arrays.asList(
            PaymentType.APPLE_PAY,
            PaymentType.GOOGLE_PAY,
            PaymentType.SAMSUNG_PAY,
            PaymentType.WECHAT_PAY,
            PaymentType.AMAZON_PAY,
            PaymentType.ALIPAY);

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
        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.paymentType, "Payment Type");
            if (!WALLET_PAYMENT_TYPES.contains(this.paymentType)) {
                throw new FieldBadFormatException(this,
                        "Payment Type must be one of: apple_pay, google_pay, samsung_pay, wechat_pay, amazon_pay, alipay");
            }
            Validate.notNullOrEmpty(this, this.authorizationId, "Authorization Id");
            Validate.notNullOrEmpty(this, this.avsResultCode, "AVS Result Code");

            if (this.creditCardCountry != null) {
                Validate.countryCode(this, this.creditCardCountry, "Credit Card Country");
            }
            if (this.acquirerRegion != null
                    && !"EU".equals(this.acquirerRegion) && !"NONEU".equals(this.acquirerRegion)) {
                throw new FieldBadFormatException(this, "Acquirer Region must be 'EU' or 'NONEU'");
            }
            if (this.expiryMonth != null && (this.expiryMonth < 1 || this.expiryMonth > 12)) {
                throw new FieldBadFormatException(this, "Expiry Month must be between 01 and 12");
            }
            if (this.expiryYear != null && (this.expiryYear < 1900 || this.expiryYear > 9999)) {
                throw new FieldBadFormatException(this, "Expiry Year must be a 4-digit integer formatted as YYYY");
            }
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
