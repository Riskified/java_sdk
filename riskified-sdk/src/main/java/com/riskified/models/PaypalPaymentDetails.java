package com.riskified.models;

import com.riskified.PaymentType;
import com.riskified.validations.*;

public class PaypalPaymentDetails implements IPaymentDetails {

    private String payerEmail;
    private String payerStatus;
    private String payerAddressStatus;
    private String protectionEligibility;
    private String paymentStatus;
    private String pendingReason;
    private String authorizationId;
    private AuthorizationError authorizationError;
    private AuthenticationResult authenticationResult;
    private PaymentType payment_type;
    private String id;
    private String gateway;
    private String acquirerBin;
    private String mid;
    private AuthorizationType authorizationType;

    

    public PaypalPaymentDetails(String payerEmail, String payerStatus, String payerAddressStatus, String protectionEligibility) {
        this.payerEmail = payerEmail;
        this.payerStatus = payerStatus;
        this.payerAddressStatus = payerAddressStatus;
        this.protectionEligibility = protectionEligibility;
        this.payment_type = PaymentType.paypal;
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {
        if (validationType == validationType.ALL) {
            Validate.emailAddress(this, this.payerEmail, "Payer Email");
            Validate.notNullOrEmpty(this, this.payerStatus, "Payer Status");
            Validate.notNullOrEmpty(this, this.payerAddressStatus, "Payer Address Status");
            Validate.notNullOrEmpty(this, this.protectionEligibility, "Protection Eligibility");
        }

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

	public AuthenticationResult getAuthenticationResult() {
		return authenticationResult;
	}

	public void setAuthenticationResult(AuthenticationResult authenticationResult) {
		this.authenticationResult = authenticationResult;
	}
	
	public AuthorizationError getAuthorizationError() {
		return authorizationError;
	}

	public void setAuthorizationError(AuthorizationError authorizationError) {
		this.authorizationError = authorizationError;
	}
	
	public PaymentType getType() {
		return payment_type;
	}

	public void setType(PaymentType type) {
		this.payment_type = type;
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

    public AuthorizationType getAuthorizationType() {return authorizationType;}

    public void setAuthorizationType(AuthorizationType authorizationType) { this.authorizationType = authorizationType; }
    
}