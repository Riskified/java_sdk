package com.riskified.models;

import java.util.Date;

import com.riskified.validations.*;

/**
 * Cancel Order details
 */
public class CancelOrder implements IValidated {
    private String id;
    private String cancelReason;
    private Date cancelledAt;

    public void validate(Validation validationType)
    throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.id, "Id");
            Validate.notNullOrEmpty(this, this.cancelReason, "Cancel Reason");
            Validate.notNull(this, this.cancelledAt, "Cancelled At");
        }
    }

    public CancelOrder() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }
}
