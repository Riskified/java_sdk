package com.riskified.models;

import com.riskified.validations.*;

import java.util.List;

public class DecisionOrder implements IValidated {

    private String id;
    private String gateway;
    private String submissionReason;
    private DecisionDetails decision;
    private List<? extends IPaymentDetails> paymentDetails;

    public DecisionOrder(String id, DecisionDetails decision) {
        this.id = id;
        this.decision = decision;
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.id, "Id");
            Validate.notNull(this, this.decision, "Decision");
        }
        if (this.decision != null) {
            this.decision.validate(validationType);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
    
    public void setSubmissionReason(String submissionReason) {
        this.submissionReason = submissionReason;
    }
    
    public String getSubmissionReason() {
        return submissionReason;
    }

    public DecisionDetails getDecision() {
        return decision;
    }

    public void setDecision(DecisionDetails decision) {
        this.decision = decision;
    }

    public List<? extends IPaymentDetails> getPaymentDetails(){
        return paymentDetails;
    }

    public void setPaymentDetails(List<? extends IPaymentDetails> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

}
