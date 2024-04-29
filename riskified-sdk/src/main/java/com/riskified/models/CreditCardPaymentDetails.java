package com.riskified.models;

import java.util.Date;

import com.riskified.validations.*;

public class CreditCardPaymentDetails implements IPaymentDetails {
    private String creditCardBin;
    private String avsResultCode;
    private String cvvResultCode;
    private String creditCardNumber;
    private String creditCardCompany;
    private String authorizationId;
    private Date storedPaymentCreatedAt;
    private Date storedPaymentUpdatedAt;
    private AuthorizationError authorizationError;
    private AuthenticationResult authenticationResult;
    private String cardholderName;
    private String id;
    private String gateway;
    private String acquirerBin;
    private String mid;
    private String paymentPlan;
    private int installmentMonths;
    private int installments;
    private AuthorizationType authorizationType;
    private String creditCardCountry;
    private String acquirerRegion;
    

    public CreditCardPaymentDetails(){};

    public CreditCardPaymentDetails(
            String creditCardBin,
    		String avsResultCode, 
    		String cvvResultCode, 
    		String creditCardNumber, 
    		String creditCardCompany) {
        this.creditCardBin = creditCardBin;
        this.avsResultCode = avsResultCode;
        this.cvvResultCode = cvvResultCode;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCompany = creditCardCompany;
        
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.creditCardBin, "Credit Card Bin");
            Validate.notNullOrEmpty(this, this.creditCardNumber, "Credit Card Number");
            if (this.creditCardCountry != null) {
                Validate.countryCode(this, this.creditCardCountry, "Credit Card Country");
            }
            if (this.acquirerRegion != null && (this.acquirerRegion != "EU" && this.acquirerRegion != "NONEU")) {
                throw new FieldBadFormatException("Acquirer Region must be 'EU' or 'NONEU'");
            }

       //   Validate.notNullOrEmpty(this, this.acquirerBin, "acquirer Bin");
       //   Validate.notNullOrEmpty(this, this.gateway, "gateway");
          
            
        }
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }
    
    public int getInstallmentMonths() {
        return installmentMonths;
    }

    public void setInstallmentMonths(int installmentMonths) {
        this.installmentMonths = installmentMonths;
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

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public Date getStoredPaymentCreatedAt() { return storedPaymentCreatedAt; }

    public void setStoredPaymentCreatedAt(Date storedPaymentCreatedAt) { this.storedPaymentCreatedAt = storedPaymentCreatedAt; }

    public Date getStoredPaymentUpdatedAt() { return storedPaymentUpdatedAt; }

    public void setStoredPaymentUpdatedAt(Date storedPaymentUpdatedAt) { this.storedPaymentUpdatedAt = storedPaymentUpdatedAt; }
    
    public AuthorizationError getAuthorizationError() {
        return authorizationError;
    }

    public void setAuthorizationError(AuthorizationError authorizationError) {
        this.authorizationError = authorizationError;
    }
    
	public AuthenticationResult getAuthenticationResults() {
		return authenticationResult;
	}

	public void setAuthenticationResult(AuthenticationResult authenticationResults) {
		this.authenticationResult = authenticationResults;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}


	public String getId() {
		return id; 
	}

	public void setId(String id) { 
		this.id = id;
	}

	public String getGateway() { 
		return gateway; 
	}

	public void setGateway(String gateway) { 
		this.gateway = gateway; 
	}

	public String getAcquirerBin() { 
		return acquirerBin; 
	}

	public void setAcquirerBin(String acquirerBin) { 
		this.acquirerBin = acquirerBin; 
	}

	public String getMid() { 
		return mid;
	}

	public void setMid(String mid) { 
		this.mid = mid; 
	}

    public int getInstallments() { return installments; }

    public void setInstallments(int installments) { this.installments = installments; }

    public AuthorizationType getAuthorizationType() { return authorizationType; }

    public void setAuthorizationType(AuthorizationType authorizationType) { this.authorizationType = authorizationType; }

    public String getCreditCardCountry() { return creditCardCountry; }

    public void setCreditCardCountry(String creditCardCountry) { this.creditCardCountry = creditCardCountry; }

    public String getAcquirerRegion() { return acquirerRegion; }

    public void setAcquirerRegion(String acquirerRegion) { this.acquirerRegion = acquirerRegion; }

}