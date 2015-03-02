package com.riskified.models;

import java.util.Date;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class DecisionOrder implements IValidated {

	
	private String id;
	private DecisionDetails decision;
	public DecisionOrder(String id, DecisionDetails decision) {
		this.id = id;
		this.decision = decision;
	}
	
	public void validate(Validation validationType)
						throws FieldBadFormatException {
		if(validationType == Validation.all) {
		Validate.stringNotNullOrEmpty(this, this.id, "Id");
		Validate.notNull(this, this.decision, "Decision");
		}
		if(this.decision != null) {
		this.decision.validate(validationType);
		}
	}
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public DecisionDetails getDecision() {
		return decision;
	}
	
	
	public void setDecision(DecisionDetails decision) {
		this.decision = decision;
	}
	
	


}
