package com.riskified.validations;

public interface IValidated {

	void validate(Validation validationType) throws FieldBadFormatException;
	
}
