package com.riskified.models;

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