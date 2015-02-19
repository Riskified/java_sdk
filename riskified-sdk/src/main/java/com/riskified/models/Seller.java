package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class Seller implements IValidated {

	private Customer customer;
	private int correspondence;
	private boolean priceNegotiated;
	private float startingPrice;
	
	public Seller(Customer customer)
	{
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getCorrespondence() {
		return correspondence;
	}
	public void setCorrespondence(int correspondence) {
		this.correspondence = correspondence;
	}
	public boolean isPriceNegotiated() {
		return priceNegotiated;
	}
	public void setPriceNegotiated(boolean priceNegotiated) {
		this.priceNegotiated = priceNegotiated;
	}
	public float getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(float startingPrice) {
		this.startingPrice = startingPrice;
	}

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		
		if(validationType == Validation.all) {
			Validate.notNull(this.customer, "Customer");
		}
		if(customer != null) {
			customer.validate(validationType);
		}
		
	}
}
