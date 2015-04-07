package com.riskified.validations;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateTest {
    private String fieldName = "testField";

    @Test
    public void testNumberIsPositive() {

        boolean caughtException = false;
        try {
            Validate.isNumberNegativeOrZero(this, -7, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception", caughtException);
        caughtException = false;

        try {
            Validate.isNumberNegativeOrZero(this, -11L, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception", caughtException);
        caughtException = false;

        try {
            Validate.isNumberNegativeOrZero(this, -1.9f, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception", caughtException);
        caughtException = false;

        try {
            Validate.isNumberNegativeOrZero(this, -1.7d, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception", caughtException);
        caughtException = false;

        try {
            Validate.isNumberNegativeOrZero(this, 0, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception as number is zero", caughtException);

        try {
            Validate.isNumberNegativeOrZero(this, 000000000, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception as number is zero", caughtException);

        caughtException = false;
        try {
            Validate.isNumberNegativeOrZero(this, 000000000017, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertFalse("Should not have caught an exception as number is not negative", caughtException);

        caughtException = false;
        try {
            Validate.isNumberNegativeOrZero(this, 7, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertFalse("Should not have caught an exception as number is not negative", caughtException);

        try {
            Validate.isNumberNegativeOrZero(this, 17L, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertFalse("Should not have caught an exception as number is not negative", caughtException);

        try {
            Validate.isNumberNegativeOrZero(this, 13.1f, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertFalse("Should not have caught an exception as number is not negative", caughtException);

        try {
            Validate.isNumberNegativeOrZero(this, 17.7D, fieldName);
        } catch (FieldBadFormatException ignore) {
            caughtException = true;
        }
        assertFalse("Should not have caught an exception as number is not negative", caughtException);
    }

    @Test
    public void testCountryCode() {
        boolean caughtException = false;
        try {
            Validate.countryCode(this, null, fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertTrue("Should have caught exception as we are passing null as country code.", caughtException);

        caughtException = false;
        try {
            Validate.countryCode(this, "", fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertTrue("Should have caught exception as we are passing empty string as country code.", caughtException);

        caughtException = false;
        try {
            Validate.countryCode(this, "USA", fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertTrue("Should have caught exception as 'CAW' is not a valid country code.", caughtException);

        caughtException = false;
        try {
            Validate.countryCode(this, "us", fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertTrue("Should have caught exception as we are passing string with lower case letters as country code.", caughtException);

        caughtException = false;
        try {
            Validate.countryCode(this, "US", fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertFalse("Should not have caught any exception as 'US' is a valid code.", caughtException);
    }

    @Test
    public void testProvinceCode() {
        boolean caughtException = false;
        try {
            Validate.provinceCode(this, null, fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception as we are sending null for province code", caughtException);

        caughtException = false;
        try {
            Validate.provinceCode(this, "", fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception as we are sending empty string for province code", caughtException);

        caughtException = false;
        try {
            Validate.provinceCode(this, "ca", fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception as we are sending lower case for province code", caughtException);

        caughtException = false;
        try {
            Validate.provinceCode(this, "CAW", fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertTrue("Should have caught an exception as we are sending invalid province code", caughtException);

        caughtException = false;
        try {
            Validate.provinceCode(this, "CA", fieldName);
        } catch (FieldBadFormatException e) {
            caughtException = true;
        }
        assertFalse("Should not have caught an exception as 'CA' is a valid province code.", caughtException);
    }
}