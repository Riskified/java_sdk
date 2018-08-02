package com.riskified.models;

import java.util.List;

public class Response {
    private ResOrder order;
    private String decision;
    private Integer received;
    private List<String> warnings;
    private Error error;

    public Response() {

    }

    public Response(CheckoutResponse checkoutResponse) {
        this.order = checkoutResponse.getCheckout();
        this.received = checkoutResponse.getReceived();
        this.warnings = checkoutResponse.getWarnings();
        this.error = checkoutResponse.getError();
    }

    public ResOrder getOrder() {
        return order;
    }

    public void setOrder(ResOrder order) {
        this.order = order;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
