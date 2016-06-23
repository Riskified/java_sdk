package com.riskified;

public class RiskifiedError extends Exception {

    public RiskifiedError(Exception ex) {
        super("Error: contact your admin with this error", ex);
    }

    public RiskifiedError(String string) {
        super("Error: contact your admin with this error: " + string);
    }
}
