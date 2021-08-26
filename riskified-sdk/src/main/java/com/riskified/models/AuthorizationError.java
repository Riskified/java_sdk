package com.riskified.models;

import java.util.Date;

import com.riskified.validations.*;

public class AuthorizationError implements IValidated {
    private String message;
    private String errorCode;
    private Date createdAt;
    private Boolean dropOff;

    public AuthorizationError(String errorCode, Date createdAt) {
        this.errorCode = errorCode;
        this.createdAt = createdAt;
    }
    
    public Boolean getDropOff() {
        return dropOff;
    }

    public void setDropOff(Boolean dropOff) {
        this.dropOff = dropOff;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
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
