package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class StripePaymentDetails implements IPaymentDetails {

	private String authorizationId ;
	
	public StripePaymentDetails(String authorizationId) {
		this.authorizationId = authorizationId;
	}
	
	public void validate(Validation validationType) throws FieldBadFormatException {
		if(validationType == validationType.NONE) {
			Validate.notNullOrEmpty(this, this.authorizationId, "Authorization ID");
		
		}
		
	}

}