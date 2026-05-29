package com.riskified.models;

import com.riskified.validations.*;

import java.util.Date;

/**
 * Represents account balance information from a financial data provider.
 * All fields are required.
 */
public class AccountBalance implements IValidated {

    private double availableBalance;
    private AccountBalanceServiceName serviceName;
    private Date updatedAt;
    private String currencyCode;

    public AccountBalance(double availableBalance, AccountBalanceServiceName serviceName, Date updatedAt, String currencyCode) {
        this.availableBalance = availableBalance;
        this.serviceName = serviceName;
        this.updatedAt = updatedAt;
        this.currencyCode = currencyCode;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.serviceName, "Service Name");
            Validate.notNull(this, this.updatedAt, "Updated At");
            Validate.currencyCode(this, this.currencyCode, "Currency Code");
        }
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public AccountBalanceServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(AccountBalanceServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
