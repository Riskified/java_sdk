package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

import java.util.Date;

public class RefundDetails implements IValidated {
    private String refundId;
    private Date refundedAt;
    private Double amount;
    private String currency;
    private String reason;


    public void validate(Validation validationType)
    throws FieldBadFormatException {
        if (validationType == Validation.all) {
            Validate.stringNotNullOrEmpty(this, this.refundId, "Refund Id");
            Validate.notNull(this, this.refundedAt, "Refunded At");
            Validate.notNull(this, this.amount, "Amount");
            Validate.notNull(this, this.currency, "Currency");
            Validate.notNull(this, this.reason, "Reason");
        }

        if (currency != null) {
            Validate.currencyCodeWellFormed(this, currency, "Currency");
        }


    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public Date getRefundedAt() {
        return refundedAt;
    }

    public void setRefundedAt(Date refundedAt) {
        this.refundedAt = refundedAt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
