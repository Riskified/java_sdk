package com.riskified.models;

import java.util.Date;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class DecisionDetails implements IValidated {

	private DecisionType externalStatus;
	private String reason;
	private Date decidedAt;
	private double amount;
	private String currency;
	private String notes;
	
	public DecisionDetails() {
		
	}

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		
		if(validationType == Validation.all) {
			
			Validate.notNull(this, this.externalStatus, "External Status");
			Validate.notNull(this, this.decidedAt, "Decided At");
		}
		
		if(currency != null) {
			Validate.currencyCodeWellFormed(this, this.currency, "Currency");
		}
		
	}
	
	

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getDecidedAt() {
		return decidedAt;
	}

	public void setDecidedAt(Date decidedAt) {
		this.decidedAt = decidedAt;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public DecisionType getExternalStatus() {
		return externalStatus;
	}

	public void setExternalStatus(DecisionType externalStatus) {
		this.externalStatus = externalStatus;
	}

	

}
