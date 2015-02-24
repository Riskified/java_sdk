package com.riskified.models;

public class CheckoutResponse extends Response {

	private ResOrder checkout;
	public CheckoutResponse() {
		
	}
	public ResOrder getCheckout() {
		return checkout;
	}
	
	public void setCheckout(ResOrder checkout) {
		this.checkout = checkout;
		this.order = checkout;
	}

}
