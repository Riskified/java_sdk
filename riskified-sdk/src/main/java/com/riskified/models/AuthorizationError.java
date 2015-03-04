package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

import java.util.Date;

public class AuthorizationError implements IValidated {
    private String message;
    private AuthorizationErrorType errorCode;
    private Date createdAt;

    public AuthorizationError(AuthorizationErrorType errorCode, Date createdAt) {
        this.errorCode = errorCode;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthorizationErrorType getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(AuthorizationErrorType errorCode) {
        this.errorCode = errorCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNull(this, errorCode, "Error Code");
            Validate.notNull(this, createdAt, "Created At");
        }

    }
}
