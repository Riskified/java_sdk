package com.riskified.models;

public class NoChargeAmount {

	String refundId;
	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	Double amount;
	String currency;
	String reason;
	
	public NoChargeAmount(String refundId, Double amount, String currency, String reason) {
		this.refundId = refundId;
		this.amount = amount;
		this.currency = currency;
		this.reason = reason;
	}
	
	public NoChargeAmount() {
		
	}

}
