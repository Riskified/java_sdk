package com.riskified.models;

import com.riskified.validations.*;

/**
 * Buy now, pay later payment details. Sent with {@code payment_type: "bnpl"}.
 */
public class BuyNowPayLaterPaymentDetails implements IPaymentDetails {

    private PaymentType paymentType = PaymentType.BNPL;
    private Double initialPaymentAmount;
    private Integer installments;
    private Integer paymentFrequency;

    public BuyNowPayLaterPaymentDetails() {
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

    /** Amount of the first installment. */
    public Double getInitialPaymentAmount() {
        return initialPaymentAmount;
    }

    public void setInitialPaymentAmount(Double initialPaymentAmount) {
        this.initialPaymentAmount = initialPaymentAmount;
    }

    /** Number of installments. */
    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    /** Number of days between installments. */
    public Integer getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(Integer paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }
}
