package com.riskified.models;

public class ChargeFreePaymentDetails {

	private String gateway;
	private Double amount;
	
	public ChargeFreePaymentDetails(String gateway, Double amount) {
		this.gateway = gateway;
		this.amount = amount;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	

}
