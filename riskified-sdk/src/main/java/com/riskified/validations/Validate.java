package com.riskified.validations;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

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
		
		if(!EmailValidator.getInstance().isValid(email)) {
			throw new FieldBadFormatException("in " + fieldName + " field, value of " + email + " is not a valid email.");
		}
		
	}
	
	static public void ipAddressWellFormed(String ip, String fieldName) throws FieldBadFormatException {
		if(!InetAddressValidator.getInstance().isValidInet4Address(ip)) {
			throw new FieldBadFormatException("in " + fieldName + " field, value of " + ip + " is not a valid IP address.");
		}
	}
	
	static public void currencyCodeWellFormed(String currency, String fieldName) throws FieldBadFormatException { 
		if(currency.length() != 3 || !currency.matches("[A-Z]+")) {
			throw new FieldBadFormatException("in " + fieldName + " field, value of " + currency + " is not a valid currency code (should be 3 capital letters).");
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
