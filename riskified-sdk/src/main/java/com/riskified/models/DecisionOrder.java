package com.riskified.models;

import java.util.Date;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class DecisionOrder implements IValidated {


	private String id;
	private String decisionType;
	private String reason;
	private Date decidedAt;
	
	public DecisionOrder(String id, String decisionType, Date decidedAt, String reason) {
		this.id = id;
		this.decisionType = decisionType;
		this.decidedAt = decidedAt;
		this.reason = reason;
	}
	

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		
		if(validationType == Validation.all) {
			
			Validate.stringNotNullOrEmpty(this, this.id, "Id");
			Validate.stringNotNullOrEmpty(this, this.decisionType, "Decision Type");
			Validate.notNull(this, this.decidedAt, "Decided At");
		}
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDecisionType() {
		return decisionType;
	}

	public void setDecisionType(String decisionType) {
		this.decisionType = decisionType;
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


	



}
