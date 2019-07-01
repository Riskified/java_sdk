package com.riskified.models;

import java.util.Date;

import com.riskified.ErrorCode;
import com.riskified.validations.*;

public class AuthorizationError implements IValidated {
    private String message;
    private ErrorCode errorCode;
    private Date createdAt;

    public AuthorizationError(ErrorCode errorCode, Date createdAt) {
        this.errorCode = errorCode;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
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
