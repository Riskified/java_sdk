package com.riskified.models;

import com.riskified.validations.*;


public class CheckoutDeniedOrder extends BaseOrder {

    private AuthorizationError authorizationError;

    public CheckoutDeniedOrder(String id) {
    	this.id = id;
    }
    
    public CheckoutDeniedOrder(String id, AuthorizationError authorizationError) {
    	this.id = id;
        this.authorizationError = authorizationError;
    }

    public AuthorizationError getAuthorizationError() {
        return authorizationError;
    }

    public void setAuthorizationError(AuthorizationError authorizationError) {
        this.authorizationError = authorizationError;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.id, "Id");
        }
        if (this.authorizationError != null) {
            authorizationError.validate(validationType);
        }


    }

}
