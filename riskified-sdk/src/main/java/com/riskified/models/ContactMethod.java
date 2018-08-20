package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class ContactMethod {
    private ContactMethodType contactMethodType;
    private String email;
    private String phone;
    private String facebookAccountUrl;
    private String numberOfMessages;
    private String chatSubject;

    public ContactMethod(ContactMethodType contactMethodType) {
        this.contactMethodType = contactMethodType;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (this.email != null) {
            Validate.emailAddress(this, this.email, "Email");
        }
    }

    public ContactMethodType getContactMethodType() {
        return contactMethodType;
    }

    public void setContactMethodType(ContactMethodType contactMethodType) {
        this.contactMethodType = contactMethodType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebookAccountUrl() {
        return facebookAccountUrl;
    }

    public void setFacebookAccountUrl(String facebookAccountUrl) {
        this.facebookAccountUrl = facebookAccountUrl;
    }

    public String getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(String numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public String getChatSubject() {
        return chatSubject;
    }

    public void setChatSubject(String chatSubject) {
        this.chatSubject = chatSubject;
    }
}
