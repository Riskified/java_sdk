package com.riskified.models;

/**
 * @author omer
 *
 */
public class Order extends BaseOrder {
	
	public String checkoutId;
	
	public Order() {

    }

    public String getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(String checkoutId) {
		this.checkoutId = checkoutId;
	}

}
