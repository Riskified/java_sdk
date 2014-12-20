package com.riskified.models;

public class PaypalPaymentDetails implements IPaymentDetails {

    private String payerEmail;
    private String payerStatus;
    private String payerAddressStatus;
    private String protectionEligibility;
    private String paymentStatus;
    private String pendingReason;
    private String authorizationId;

    public PaypalPaymentDetails(String payerEmail, String payerStatus, String payerAddressStatus, String protectionEligibility) {
        this.payerEmail = payerEmail;
        this.payerStatus = payerStatus;
        this.payerAddressStatus = payerAddressStatus;
        this.protectionEligibility = protectionEligibility;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getPayerStatus() {
        return payerStatus;
    }

    public void setPayerStatus(String payerStatus) {
        this.payerStatus = payerStatus;
    }

    public String getPayerAddressStatus() {
        return payerAddressStatus;
    }

    public void setPayerAddressStatus(String payerAddressStatus) {
        this.payerAddressStatus = payerAddressStatus;
    }

    public String getProtectionEligibility() {
        return protectionEligibility;
    }

    public void setProtectionEligibility(String protectionEligibility) {
        this.protectionEligibility = protectionEligibility;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPendingReason() {
        return pendingReason;
    }

    public void setPendingReason(String pendingReason) {
        this.pendingReason = pendingReason;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }
}