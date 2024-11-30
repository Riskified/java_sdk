package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class VenmoPaymentDetails implements IPaymentDetails {

    private String venmoEmail;
    private String venmoUsername;
    private String venmoAccountId;
    private String authorizationId;
    private AuthorizationError authorizationError;
    private AuthenticationResult authenticationResult;
    private String id;
    private String gateway;
    private String acquirerBin;
    private String mid;
    private AuthorizationType authorizationType;


    public VenmoPaymentDetails(String venmoEmail) {
        this.venmoEmail = venmoEmail;
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {
        if (validationType == validationType.ALL) {
            Validate.emailAddress(this, this.venmoEmail, "Venmo Email");
            Validate.notNullOrEmpty(this, this.venmoUsername, "Venmo Username");
        }

    }

    public String getVenmoEmail() {
        return venmoEmail;
    }

    public void setVenmoEmail(String venmoEmail) {
        this.venmoEmail = venmoEmail;
    }

    public String getVenmoUsername() {
        return venmoUsername;
    }

    public void setVenmoAccountId(String venmoAccountId) {
        this.venmoAccountId = venmoAccountId;
    }

    public String getVenmoAccountId() {
        return venmoAccountId;
    }

    public void setVenmoUsername(String venmoUsername) {
        this.venmoUsername = venmoUsername;
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