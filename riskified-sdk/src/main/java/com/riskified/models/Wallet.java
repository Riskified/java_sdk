package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validation;

public class Wallet implements IValidated {
    String id;


    public Wallet() {
    }

    public void validate(Validation validationType)
            throws FieldBadFormatException {
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
