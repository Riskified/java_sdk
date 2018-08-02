package com.riskified.models;

import com.riskified.validations.*;

public class LoginStatus implements IValidated {
    private LoginStatusType loginStatusType;
    private FailureReason failureReason;

    public LoginStatus(LoginStatusType loginStatusType) {
        this.loginStatusType = loginStatusType;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.loginStatusType, "Login Status Type");
        }

        if (this.failureReason != null && this.loginStatusType != LoginStatusType.failure) {
            throw new FieldBadFormatException("Reason for unsuccessful login should only be included on failed login.");
        }
    }

    public LoginStatusType getLoginStatusType() {
        return loginStatusType;
    }

    public void setLoginStatusType(LoginStatusType loginStatusType) {
        this.loginStatusType = loginStatusType;
    }

    public FailureReason getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(FailureReason failureReason) {
        this.failureReason = failureReason;
    }
}
