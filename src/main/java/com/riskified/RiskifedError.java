package com.riskified;

public class RiskifedError extends Exception {

    public RiskifedError(Exception ex) {
        super("Error: contact your admin with this error", ex);
    }

    public RiskifedError(String string) {
        super("Error: contact your admin with this error: " + string);
    }
}
