package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class Redeem implements IValidated {
    private String customerId;
    private String redeemType;
    private ClientDetails clientDetails;
    private SessionDetails sessionDetails;

    public Redeem(String customerId, String redeemType, ClientDetails clientDetails, SessionDetails sessionDetails) {
        this.customerId = customerId;
        this.redeemType = redeemType;
        this.clientDetails = clientDetails;
        this.sessionDetails = sessionDetails;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.customerId, "Customer ID");
            Validate.notNull(this, this.redeemType, "Redeem Type");
            Validate.notNull(this, this.clientDetails, "Client Details");
            Validate.notNull(this, this.sessionDetails, "Session Details");
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRedeemType() {
        return redeemType;
    }

    public void setRedeemType(String redeemType) {
        this.redeemType = redeemType;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public SessionDetails getSessionDetails() {
        return sessionDetails;
    }

    public void setSessionDetails(SessionDetails sessionDetails) {
        this.sessionDetails = sessionDetails;
    }
}
