package com.riskified.models;

import com.riskified.validations.*;

import java.util.List;

/**
 * Represents identity information associated with a payment account.
 * All fields are optional.
 */
public class AccountIdentity implements IValidated {

    private List<String> names;
    private List<Address> addresses;
    private List<String> phoneNumbers;
    private List<String> emails;

    public AccountIdentity() {
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (this.addresses != null) {
            for (Address address : this.addresses) {
                address.validate(validationType);
            }
        }
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
