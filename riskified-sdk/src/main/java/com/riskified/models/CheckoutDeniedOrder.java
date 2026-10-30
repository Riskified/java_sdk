package com.riskified.models;

import com.riskified.validations.*;


public class CheckoutDeniedOrder extends BaseOrder {

    private AuthorizationError authorizationError;

    public CheckoutDeniedOrder(String id) {
    	this.id = id;
    }
    
    /**
     * @deprecated The docs put authorization_error inside the card, wallet and PayPal payment details. Use
     * {@link #CheckoutDeniedOrder(String)} and set the error on each payment detail, e.g.
     * {@link CreditCardPaymentDetails#setAuthorizationError(AuthorizationError)}. Until 7.0.0 a root-level error is
     * still sent, and also copied into each of those payment details that has none of its own.
     */
    @Deprecated
    public CheckoutDeniedOrder(String id, AuthorizationError authorizationError) {
    	this.id = id;
        this.authorizationError = authorizationError;
    }

    /**
     * @deprecated The docs put authorization_error inside the card, wallet and PayPal payment details; use
     * {@link CreditCardPaymentDetails#getAuthorizationError()}, {@link WalletPaymentDetails#getAuthorizationError()} or
     * {@link PaypalPaymentDetails#getAuthorizationError()}.
     */
    @Deprecated
    public AuthorizationError getAuthorizationError() {
        return authorizationError;
    }

    /**
     * @deprecated The docs put authorization_error inside the card, wallet and PayPal payment details; set it there,
     * e.g. {@link CreditCardPaymentDetails#setAuthorizationError(AuthorizationError)}. Until 7.0.0 a root-level error
     * is still sent, and also copied into each of those payment details that has none of its own.
     */
    @Deprecated
    public void setAuthorizationError(AuthorizationError authorizationError) {
        this.authorizationError = authorizationError;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.id, "Id");
        }
        if (this.authorizationError != null) {
            authorizationError.validate(validationType);
        }


    }

}
