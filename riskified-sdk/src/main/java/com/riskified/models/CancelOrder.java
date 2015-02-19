package com.riskified.models;

import java.util.Date;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

/**
 * Cancel Order details
 */
public class CancelOrder implements IValidated {
    private String id;
    private String cancelReason;
    private Date cancelledAt;

    public void validate(Validation validationType)
			throws FieldBadFormatException {
		Validate.stringNotNullOrEmpty(this.id, "Id");
		Validate.stringNotNullOrEmpty(this.cancelReason, "Cancel Reason");
		Validate.notNull(this.cancelledAt, "Cancelled At");
		
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
