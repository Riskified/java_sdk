package com.riskified.models;

import com.riskified.validations.*;

import java.util.Date;

public class Login implements IValidated {
    private String customerId;
    private String email;
    private Date customerCreatedAt;
    private Boolean loginAtCheckout;
    private SocialType socialLoginType;
    private LoginStatus loginStatus;
    private ClientDetails clientDetails;
    private SessionDetails sessionDetails;

    public Login(String customerId, String email, LoginStatus loginStatus, ClientDetails clientDetails, SessionDetails sessionDetails) {
        this.customerId = customerId;
        this.email = email;
        this.loginStatus = loginStatus;
        this.clientDetails = clientDetails;
        this.sessionDetails = sessionDetails;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        Validate.notNullOrEmpty(this, this.customerId, "Customer ID");
        Validate.notNullOrEmpty(this, this.email, "Email");
        Validate.notNull(this, this.loginStatus, "Login Status");
        Validate.notNull(this, this.clientDetails, "Client Details");

        loginStatus.validate(validationType);
        sessionDetails.validate(validationType);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getCustomerCreatedAt() { return customerCreatedAt; }

    public void setCustomerCreatedAt(Date customerCreatedAt) {this.customerCreatedAt = customerCreatedAt; }

    public Boolean getLoginAtCheckout() { return loginAtCheckout; }

    public void setLoginAtCheckout(Boolean loginAtCheckout) {
        this.loginAtCheckout = loginAtCheckout;
    }

    public SocialType getSocialLoginType() {
        return socialLoginType;
    }

    public void setSocialLoginType(SocialType socialLoginType) {
        this.socialLoginType = socialLoginType;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
