package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class CreditCardPaymentDetails implements IPaymentDetails {
    private String creditCardBin;
    private String avsResultCode;
    private String cvvResultCode;
    private String creditCardNumber;
    private String creditCardCompany;
    private String authorization_id;

    public CreditCardPaymentDetails(String creditCardBin, String avsResultCode, String cvvResultCode, String creditCardNumber, String creditCardCompany) {
        this.creditCardBin = creditCardBin;
        this.avsResultCode = avsResultCode;
        this.cvvResultCode = cvvResultCode;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCompany = creditCardCompany;
    }
    
    public void validate(Validation validationType)
			throws FieldBadFormatException {
		if(validationType == Validation.all) {
	    	Validate.stringNotNullOrEmpty(this, this.creditCardBin, "Credit Card Bin");
	    	Validate.stringNotNullOrEmpty(this, this.avsResultCode, "AVS Result Code");
	    	Validate.stringNotNullOrEmpty(this, this.cvvResultCode, "CVV Result Code");
	    	Validate.stringNotNullOrEmpty(this, this.creditCardNumber, "Credit Card Number");
		}
		
	}

    public String getCreditCardBin() {
        return creditCardBin;
    }

    public void setCreditCardBin(String creditCardBin) {
        this.creditCardBin = creditCardBin;
    }

    public String getAvsResultCode() {
        return avsResultCode;
    }

    public void setAvsResultCode(String avsResultCode) {
        this.avsResultCode = avsResultCode;
    }

    public String getCvvResultCode() {
        return cvvResultCode;
    }

    public void setCvvResultCode(String cvvResultCode) {
        this.cvvResultCode = cvvResultCode;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardCompany() {
        return creditCardCompany;
    }

    public void setCreditCardCompany(String creditCardCompany) {
        this.creditCardCompany = creditCardCompany;
    }

    public String getAuthorization_id() {
        return authorization_id;
    }

    public void setAuthorization_id(String authorization_id) {
        this.authorization_id = authorization_id;
    }

	
}