package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class Wishlist implements IValidated {
    private String customerId;
    private String wishlistAction;
    private ClientDetails clientDetails;
    private SessionDetails sessionDetails;
    private LineItem lineItem;

    public Wishlist(String customerId, String wishlistAction, ClientDetails clientDetails, SessionDetails sessionDetails, LineItem lineItem) {
        this.customerId = customerId;
        this.wishlistAction = wishlistAction;
        this.clientDetails = clientDetails;
        this.sessionDetails = sessionDetails;
        this.lineItem = lineItem;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.customerId, "Customer ID");
            Validate.notNull(this, this.clientDetails, "Client Details");
            Validate.notNull(this, this.sessionDetails, "Session Details");
            Validate.notNull(this, this.lineItem, "Line Item");
            Validate.notNullOrEmpty(this, this.lineItem.getCategory(), "Line Item Category");
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWishlistAction() {
        return wishlistAction;
    }

    public void setWishlistAction(String wishlistAction) {
        this.wishlistAction = wishlistAction;
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

    public LineItem getLineItem() {
        return lineItem;
    }

    public void setLineItem(LineItem lineItem) {
        this.lineItem = lineItem;
    }
}
