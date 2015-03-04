package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

import java.util.Date;

/**
 * Cancel Order details
 */
public class CancelOrder implements IValidated {
    private String id;
    private String cancelReason;
    private Date cancelledAt;

    public void validate(Validation validationType)
    throws FieldBadFormatException {

        if (validationType == Validation.all) {
            Validate.stringNotNullOrEmpty(this, this.id, "Id");
            Validate.stringNotNullOrEmpty(this, this.cancelReason, "Cancel Reason");
            Validate.notNull(this, this.cancelledAt, "Cancelled At");
        }

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
