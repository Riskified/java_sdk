package com.riskified.models;

import java.util.Date;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class AuthorizationError implements IValidated {


	private String message;
	private String errorCode;
    private Date createdAt;

    public AuthorizationError(String errorCode, Date createdAt)
    {
    	this.errorCode = errorCode;
    	this.createdAt = createdAt;
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
		
		if(validationType == Validation.all) {
			Validate.stringNotNullOrEmpty(errorCode, "Error Code");
			Validate.notNull(createdAt, "Created At");
		}
		
	}
}
