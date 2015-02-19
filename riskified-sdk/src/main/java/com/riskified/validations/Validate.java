package com.riskified.validations;

import org.apache.commons.validator.routines.EmailValidator;

public class Validate {

	
	static public void notNull(Object obj, String fieldName) throws FieldBadFormatException {
		if(obj == null) {
			throw new FieldBadFormatException(fieldName + " can't be null.");
		}
	}
	
	static public void stringNotNullOrEmpty(String str, String fieldName) throws FieldBadFormatException {
		
		if(str == null || str.isEmpty()) {
			throw new FieldBadFormatException(fieldName + " can't be null or empty.");
		}
	}
	static public void emailAddressWellFormed(String email, String fieldName) throws FieldBadFormatException {
		Validate.notNull(email, fieldName);
		if(!EmailValidator.getInstance().isValid(email)) {
			throw new FieldBadFormatException("in " + fieldName + " field, value of " + email + " is not a valid email.");
		}
		
	}
	
	static public void mustBePositive(double number, String fieldName) throws FieldBadFormatException {
		if(number <= 0) {
			throw new FieldBadFormatException(fieldName + " must be positive.");
		}
	}
	static public void mustBePositive(int number, String fieldName) throws FieldBadFormatException {
		if(number <= 0) {
			throw new FieldBadFormatException(fieldName + " must be positive.");
		}
	}
	static public void mustBeZeroOrPositive(double number, String fieldName) throws FieldBadFormatException {
		if(number < 0) {
			throw new FieldBadFormatException(fieldName + " can't be negative.");
		}
	}
	static public void mustBeZeroPositive(int number, String fieldName) throws FieldBadFormatException {
		if(number < 0) {
			throw new FieldBadFormatException(fieldName + " cen't be negative.");
		}
	}
	

}	
