package com.riskified.models;

import java.util.List;
import com.riskified.validations.*;

public class CustomerCreate implements IValidated {
    private String customerId;
    private Boolean phoneMandatory;
    private String referrerCustomerId;
    private SocialType socialSignupType;
    private ClientDetails clientDetails;
    private SessionDetails sessionDetails;
    private Customer customer;
    private List<IPaymentDetails> paymentDetails;
    private List<Address> billingAddress;
    private List<Address> shippingAddress;
    private String vendorName;

    public CustomerCreate(String customerId, ClientDetails clientDetails, SessionDetails sessionDetails, Customer customer) {
        this.customerId = customerId;
        this.clientDetails = clientDetails;
        this.sessionDetails = sessionDetails;
        this.customer = customer;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        Validate.notNullOrEmpty(this, this.customerId, "Customer ID");
        Validate.notNull(this, this.clientDetails, "Client Details");
        Validate.notNull(this, this.sessionDetails, "Session Details");
        Validate.notNull(this, this.customer, "Customer");

        sessionDetails.validate(validationType);
        customer.validate(validationType);

        if (this.paymentDetails != null) {
            for (IPaymentDetails paymentDetail : this.paymentDetails) {
                paymentDetail.validate(validationType);
            }
        }

        if (this.billingAddress != null) {
            for (Address bAddress: this.billingAddress) {
                bAddress.validate(validationType);
            }
        }

        if (this.shippingAddress != null) {
            for (Address sAddress: this.shippingAddress) {
                sAddress.validate(validationType);
            }
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Boolean getPhoneMandatory() {
        return phoneMandatory;
    }

    public void setPhoneMandatory(Boolean phoneMandatory) {
        this.phoneMandatory = phoneMandatory;
    }

    public String getReferrerCustomerId() {
        return referrerCustomerId;
    }

    public void setReferrerCustomerId(String referrerCustomerId) {
        this.referrerCustomerId = referrerCustomerId;
    }

    public SocialType getSocialSignupType() {
        return socialSignupType;
    }

    public void setSocialSignupType(SocialType socialSignupType) {
        this.socialSignupType = socialSignupType;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<IPaymentDetails> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<IPaymentDetails> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public List<Address> getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(List<Address> billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<Address> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(List<Address> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getVendorName() { return vendorName; }

    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
}
