package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class DiscountCode implements IValidated {

    private String code;
    private Double amount;

    public DiscountCode(double amount, String code) {
        this.code = code;
        this.amount = amount;
    }
    
	public void validate(Validation validationType)
			throws FieldBadFormatException {
		
		if(validationType == Validation.all)
		Validate.notNull(this.code, "Code");
		Validate.notNull(this.amount, "Amount");
		
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
