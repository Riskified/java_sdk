package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;
import java.util.*;

public class Verification implements IValidated {
    private String email;
    private Date verifiedAt;
    private VerificationStatus status;
    private String eventId;
    private VerificationSessionDetails verificationSessionDetails;
    private String vendorName;

    public Verification(Date verifiedAt, VerificationStatus status, String eventId) {
        this.verifiedAt = verifiedAt;
        this.status = status;
        this.eventId = eventId;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.verifiedAt, "verified_at");
            Validate.notNull(this, this.status, "status");
            Validate.notNull(this, this.eventId, "event_id");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public VerificationSessionDetails getVerificationSessionDetails() { return verificationSessionDetails; }

    public void setVerificationSessionDetails(VerificationSessionDetails verificationSessionDetails) { this.verificationSessionDetails = verificationSessionDetails; }

    public String getVendorName() { return vendorName; }

    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
}
