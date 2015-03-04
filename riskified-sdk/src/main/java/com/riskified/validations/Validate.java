package com.riskified.validations;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

public class Validate {
    public static void notNull(Object source, Object obj, String fieldName) throws FieldBadFormatException {
        if (obj == null) {
            throw new FieldBadFormatException(source, fieldName + " can't be null.");
        }
    }

    public static void notNullOrEmpty(Object source, String str, String fieldName) throws FieldBadFormatException {
        if (str.isEmpty()) {
            throw new FieldBadFormatException(source, fieldName + " can't be null or empty.");
        }
    }

    public static void emailAddress(Object source, String email, String fieldName) throws FieldBadFormatException {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + email + " is not a valid email.");
        }
    }

    public static void ipAddress(Object source, String ip, String fieldName) throws FieldBadFormatException {
        if (!InetAddressValidator.getInstance().isValidInet4Address(ip)) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + ip + " is not a valid IP address.");
        }
    }

    public static void currencyCode(Object source, String currency, String fieldName) throws FieldBadFormatException {
        if (currency.length() != 3 || !currency.matches("[A-Z]+")) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + currency + " is not a valid currency code (should be 3 capital letters).");
        }
    }

    public static void countryCode(Object source, String currency, String fieldName) throws FieldBadFormatException {
        if (currency.length() != 2 || !currency.matches("[A-Z]+")) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + currency + " is not a valid currency code (should be 2 capital letters).");
        }
    }

    public static void provinceCode(Object source, String currency, String fieldName) throws FieldBadFormatException {
        if (currency.length() != 2 || !currency.matches("[A-Z]+")) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + currency + " is not a valid province code (should be 2 capital letters).");
        }
    }

    public static void isNumberNegative(Object source, Number number, String fieldName) throws FieldBadFormatException {
        boolean isNegative = ((number + "").charAt(0) == '-');
        if (isNegative) {
            throw new FieldBadFormatException(source, fieldName + " can't be negative.");
        }
    }

    public static void isNumberNegativeOrZero(Object source, Number number, String fieldName) throws FieldBadFormatException {
        isNumberNegative(source, number, fieldName);
        boolean isZero = (number + "").charAt(0) == '0';
        if (isZero) {
            throw new FieldBadFormatException(source, fieldName + " can't be zero.");
        }
    }
}	
