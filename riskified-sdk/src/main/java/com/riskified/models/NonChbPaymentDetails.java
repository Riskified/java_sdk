package com.riskified.models;

import com.riskified.validations.*;

/**
 * Payment details for payments that can't be charged back: store credit and reward points. Only the payment type is
 * sent, e.g. {@code payment_type: "store_credit"}.
 */
public class NonChbPaymentDetails implements IPaymentDetails {

    private PaymentType paymentType;

    /**
     * @param paymentType {@link PaymentType#STORE_CREDIT} or {@link PaymentType#REWARD_POINTS}
     */
    public NonChbPaymentDetails(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    // No client-side checks for this type.
    public void validate(Validation validationType) throws FieldBadFormatException {
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
