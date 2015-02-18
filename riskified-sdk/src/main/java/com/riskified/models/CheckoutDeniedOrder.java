package com.riskified.models;


public class CheckoutDeniedOrder {

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
	
}
