package com.riskified.validations;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateTest {

    @Test
    public void testNumberIsPositive() {
        String fieldName = "testField";
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
}