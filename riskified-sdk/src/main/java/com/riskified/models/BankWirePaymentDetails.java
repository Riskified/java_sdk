package com.riskified.models;

import com.riskified.validations.*;

public class BankWirePaymentDetails implements IPaymentDetails {
    private String accountNumber;
    private String routingNumber;
    private String token;

    public BankWirePaymentDetails(String accountNumber, String routingNumber) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }

    public BankWirePaymentDetails(String accountNumber, String routingNumber, String token) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.token = token;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.accountNumber, "Bank Account Number");
            Validate.notNullOrEmpty(this, this.routingNumber, "Bank Routing Number");
            Validate.notNullOrEmpty(this, this.token, "Token");
        }
    }
}
