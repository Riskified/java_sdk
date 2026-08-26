package com.riskified.models;

import java.util.List;

public class Response {
    private ResOrder order;
    private String decision;
    private Integer received;
    private List<String> warnings;
    private Error error;
    private String loginId;
    private String message;
    private VerificationData verificationData;
    private String WidgetToken;


    public Response() {

    }

    public Response(CheckoutResponse checkoutResponse) {
        this.order = checkoutResponse.getCheckout();
        this.received = checkoutResponse.getReceivedOrNull();
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

    public String getLoginId() { return loginId; }

    public void setLoginId(String loginId) { this.loginId = loginId; }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    /**
     * @return the {@code received} count, or {@code 0} when the response carried no such field.
     *         Unboxing a null {@link Integer} here used to raise a {@link NullPointerException} on
     *         every response that omits it — which is every error response and several success
     *         ones. Use {@link #getReceivedOrNull()} when the difference between absent and zero
     *         matters; the primitive return type is kept so existing callers still compile.
     */
    public int getReceived() {
        return received == null ? 0 : received;
    }

    /**
     * @return the {@code received} count, or {@code null} when the response carried no such field.
     */
    public Integer getReceivedOrNull() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage() {
        this.message = message;
    }

    public VerificationData getVerificationData() {
        return verificationData;
    }

    public void setVerificationData(VerificationData verificationData) {
        this.verificationData = verificationData;
    }

    public String getOtpwidgetToken() {
        return WidgetToken;
    }

    public void setOtpwidgetToken(String widgetToken) {
        this.WidgetToken = widgetToken;
    }
}
