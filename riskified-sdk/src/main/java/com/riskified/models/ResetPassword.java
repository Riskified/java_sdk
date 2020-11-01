package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class ResetPassword implements IValidated {
    private String customerId;
    private ResetPasswordStatusType status;
    private ReasonType reason;
    private String email;
    private ClientDetails clientDetails;
    private SessionDetails sessionDetails;
    private String vendorName;

    public ResetPassword(String customerId, ResetPasswordStatusType status, ReasonType reason, String email, ClientDetails clientDetails, SessionDetails sessionDetails) {
        this.customerId = customerId;
        this.status = status;
        this.reason = reason;
        this.email = email;
        this.clientDetails = clientDetails;
        this.sessionDetails = sessionDetails;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.customerId, "Customer ID");
            Validate.notNull(this,this.status,"Status");
            Validate.notNull(this, this.reason, "Reason");
            Validate.notNull(this, this.email, "Email");
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

    public ResetPasswordStatusType getStatus() { return status; }

    public void setStatus(ResetPasswordStatusType status) { this.status = status; }

    public ReasonType getReason() { return reason; }

    public void setReason(ReasonType reason) {this.reason = reason; }

    public String getEmail() {return email; }

    public void setEmail(String email) { this.email = email; }

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

    public String getVendorName() { return vendorName; }

    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
}
