package com.riskified.validations;

import org.apache.http.conn.util.InetAddressUtils;

public class Validate {
    public static void notNull(Object source, Object obj, String fieldName) throws FieldBadFormatException {
        if (obj == null) {
            throw new FieldBadFormatException(source, fieldName + " can't be null.");
        }
    }

    public static void notNullOrEmpty(Object source, String str, String fieldName) throws FieldBadFormatException {
        if (str == null || str != "") {
            throw new FieldBadFormatException(source, fieldName + " can't be null or empty.");
        }
    }

    public static void emailAddress(Object source, String email, String fieldName) throws FieldBadFormatException {
    	if (!email.contains("@")) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + email + " is not a valid email.");
        }
    }

    public static void ipAddress(Object source, String ip, String fieldName) throws FieldBadFormatException {
		if (!InetAddressUtils.isIPv4Address(ip) && !InetAddressUtils.isIPv6Address(ip)) {
		    throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + ip + " is not a valid IP address.");
		}
    }

    public static void currencyCode(Object source, String currency, String fieldName) throws FieldBadFormatException {
        if (currency == null || currency.length() != 3 || !currency.matches("[A-Z]+")) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + currency + " is not a valid currency code (should be 3 capital letters).");
        }
    }

    public static void countryCode(Object source, String countryCode, String fieldName) throws FieldBadFormatException {
        if (countryCode == null || countryCode.length() != 2 || !countryCode.matches("[A-Z]+")) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + countryCode + " is not a valid country code (should be 2 capital letters).");
        }
    }

    public static void provinceCode(Object source, String provinceCode, String fieldName) throws FieldBadFormatException {
        if (provinceCode == null || provinceCode.length() < 2 || provinceCode.length() > 3 || !provinceCode.matches("[A-Z]+")) {
            throw new FieldBadFormatException(source, "in " + fieldName + " field, value of " + provinceCode + " is not a valid province code (should be 2 or 3 capital letters).");
        }
    }

    public static void isNumberNegative(Object source, Number number, String fieldName) throws FieldBadFormatException {
        boolean isNegative = ((number + "").charAt(0) == '-');
        if (isNegative) {
            throw new FieldBadFormatException(source, fieldName + " can't be negative.");
        }
    }

    public static void isNumberNegativeOrZero(Object source, float number, String fieldName) throws FieldBadFormatException {
        isNumberNegative(source, number, fieldName);
        if (number == 0) {
            throw new FieldBadFormatException(source, fieldName + " can't be zero.");
        }
    }
    
    public static void isNumberNegativeOrZero(Object source, Double number, String fieldName) throws FieldBadFormatException {
        isNumberNegative(source, number, fieldName);
        if (number == 0) {
            throw new FieldBadFormatException(source, fieldName + " can't be zero.");
        }
    }
    
    public static void isNumberNegativeOrZero(Object source, int number, String fieldName) throws FieldBadFormatException {
        isNumberNegative(source, number, fieldName);
        if (number == 0) {
            throw new FieldBadFormatException(source, fieldName + " can't be zero.");
        }
    }
}	
