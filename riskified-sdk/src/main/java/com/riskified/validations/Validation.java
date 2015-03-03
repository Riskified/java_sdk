package com.riskified.validations;

/*
 * According to the validation type object that will be posted to the server will be validated.
 * None = No validation at all
 * Weak = Only shallow validation
 * All = All object (including inner objects) will be validated
 */
public enum Validation {
    none,
    ignoreMissing,
    all
}
