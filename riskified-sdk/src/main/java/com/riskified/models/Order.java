package com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

/**
 * @author omer
 *
 */
public class Order extends BaseOrder {

	public Order() {
	}

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		
		Validate.stringNotNullOrEmpty(this.id, "Id");
		
		
		if(validationType == Validation.weak) { // Validation for updating, all fields except Id are optional
			
			if(this.totalPrice != null) {
				Validate.mustBePositive(this.totalPrice, "Total Price");
			}
			return;
		}
		
		Validate.stringNotNullOrEmpty(this.name, "Name");
		Validate.emailAddressWellFormed(this.email, "Email");
		Validate.notNull(this.createdAt, "Created At");
		Validate.notNull(this.closedAt, "Closed At");
		Validate.notNull(this.updatedAt, "Updated At");
		Validate.stringNotNullOrEmpty(this.gateway, "Gateway");
		Validate.stringNotNullOrEmpty(this.browserIp, "Browser IP");
		Validate.notNull(this.totalPrice, "Total Price");
		Validate.mustBePositive(this.totalPrice, "Total Price");
		Validate.notNull(this.totalDiscounts, "Total Discounts");
		
		Validate.notNull(this.lineItems, "Line Items");
		if(validationType == Validation.all) {
			for(LineItem lineItem : this.lineItems) {
				lineItem.validate(validationType);
			}
		}
		
		Validate.notNull(this.discountCodes, "Discount Codes");
		if(validationType == Validation.all) {
			for(DiscountCode discountCode : this.discountCodes) {
				discountCode.validate(validationType);
			}
		}
		
		Validate.notNull(this.shippingLines, "Shipping Lines");
		if(validationType == Validation.all) {
			for(ShippingLine shippingLine : this.shippingLines) {
				shippingLine.validate(validationType);
			}
		}
		
		Validate.notNull(this.paymentDetails, "Payment Details");
		if(validationType == Validation.all) {
			this.paymentDetails.validate(validationType);
		}
		
		Validate.notNull(this.customer, "Customer");
		if(validationType == Validation.all) {
			this.customer.validate(validationType);
		}
		
		Validate.notNull(this.billingAddress, "Billing Address");
		if(validationType == Validation.all) {
			this.billingAddress.validate(validationType);
		}
		
		Validate.notNull(this.shippingAddress, "Shipping Address");
		if(validationType == Validation.all) {
			this.shippingAddress.validate(validationType);
		}
	}
	
}
