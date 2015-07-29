package com.riskified.models;

import com.riskified.validations.*;

public class DiscountCode implements IValidated {

    private String code;
    private Double amount;

    public DiscountCode(double amount, String code) {
        this.code = code;
        this.amount = amount;
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {

        if (validationType == Validation.ALL)
            Validate.notNull(this, this.code, "Code");
        Validate.notNull(this, this.amount, "Amount");

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
