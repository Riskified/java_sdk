package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;


public class CheckoutDeniedOrder implements IValidated {

	private String id;
	private AuthorizationError authorizationError;

	public CheckoutDeniedOrder(String id, AuthorizationError authorizationError) {
		this.id = id;
		this.authorizationError = authorizationError;
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public AuthorizationError getAuthorizationError() {
        return authorizationError;
    }

    public void setAuthorizationError(AuthorizationError authorizationError) {
        this.authorizationError = authorizationError;
    }

	public void validate(Validation validationType) throws FieldBadFormatException {
		
		Validate.stringNotNullOrEmpty(this.id, "Id");
		Validate.notNull(authorizationError, "Authorization Error");
		if(validationType == Validation.all) {
			authorizationError.validate(validationType);
		}
		
		
		
		
	}
	
}
