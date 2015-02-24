package com.riskified.validations;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

public class Validate {

	
	static public void notNull(Object source, Object obj, String fieldName) throws FieldBadFormatException {
		if(obj == null) {
			throw new FieldBadFormatException(source, fieldName + " can't be null.");
		}
	}
	
	static public void stringNotNullOrEmpty(Object source, String str, String fieldName) throws FieldBadFormatException {
		
		if(str == null || str.isEmpty()) {
			throw new FieldBadFormatException(source, fieldName + " can't be null or empty.");
		}
	}
	static public void emailAddressWellFormed(Object source, String email, String fieldName) throws FieldBadFormatException {
		
		if(!EmailValidator.getInstance().isValid(email)) {
			throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + email + " is not a valid email.");
		}
		
	}
	
	static public void ipAddressWellFormed(Object source, String ip, String fieldName) throws FieldBadFormatException {
		if(!InetAddressValidator.getInstance().isValidInet4Address(ip)) {
			throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + ip + " is not a valid IP address.");
		}
	}
	
	static public void currencyCodeWellFormed(Object source, String currency, String fieldName) throws FieldBadFormatException { 
		if(currency.length() != 3 || !currency.matches("[A-Z]+")) {
			throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + currency + " is not a valid currency code (should be 3 capital letters).");
		}
	}
	static public void countryCodeWellFormed(Object source, String currency, String fieldName) throws FieldBadFormatException { 
		if(currency.length() != 2 || !currency.matches("[A-Z]+")) {
			throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + currency + " is not a valid currency code (should be 2 capital letters).");
		}
	}
	static public void provinceCodeWellFormed(Object source, String currency, String fieldName) throws FieldBadFormatException { 
		if(currency.length() != 2 || !currency.matches("[A-Z]+")) {
			throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + currency + " is not a valid province code (should be 2 capital letters).");
		}
	}
	
	
	static public void mustBePositive(Object source, double number, String fieldName) throws FieldBadFormatException {
		if(number <= 0) {
			throw new FieldBadFormatException(source, fieldName + " must be positive.");
		}
	}
	static public void mustBePositive(Object source, int number, String fieldName) throws FieldBadFormatException {
		if(number <= 0) {
			throw new FieldBadFormatException(source, fieldName + " must be positive.");
		}
	}
	static public void mustBeZeroOrPositive(Object source, double number, String fieldName) throws FieldBadFormatException {
		if(number < 0) {
			throw new FieldBadFormatException(source, fieldName + " can't be negative.");
		}
	}
	static public void mustBeZeroPositive(Object source, int number, String fieldName) throws FieldBadFormatException {
		if(number < 0) {
			throw new FieldBadFormatException(source, fieldName + " cen't be negative.");
		}
	}
	

}	
