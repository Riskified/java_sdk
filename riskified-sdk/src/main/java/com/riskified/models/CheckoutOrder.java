package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class CheckoutOrder extends BaseOrder {

	public CheckoutOrder() {
		
	}

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		
		Validate.stringNotNullOrEmpty(this.getId(), "Id");
		
		// In Checkout order all fields except Id are optional.
		
		if(validationType == Validation.all && this.lineItems != null) {
			for(LineItem lineItem : this.lineItems) {
				lineItem.validate(validationType);
			}
		}
		
		if(validationType == Validation.all && this.discountCodes != null) {
			for(DiscountCode discountCode : this.discountCodes) {
				discountCode.validate(validationType);
			}
		}
		
		if(validationType == Validation.all && this.shippingLines != null) {
			for(ShippingLine shippingLine : this.shippingLines) {
				shippingLine.validate(validationType);
			}
		}
		
		if(validationType == Validation.all && this.paymentDetails != null) {
			this.paymentDetails.validate(validationType);
		}
		
		if(validationType == Validation.all && this.customer != null) {
			this.customer.validate(validationType);
		}
		
		if(validationType == Validation.all && this.billingAddress != null) {
			this.billingAddress.validate(validationType);
		}
		
		if(validationType == Validation.all && this.shippingAddress != null) {
			this.shippingAddress.validate(validationType);
		}
		
	}

}
