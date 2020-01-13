package com.riskified.models;

public class AuthenticationType {

	private String auth_type;
    private String exemption_method;

    public AuthenticationType(String authType) {
        this.auth_type = authType;
    }

    public String getAuthType() { return auth_type;}

    public void setAuthType(String authType) { this.auth_type = authType;}

    public String getExemptionMethod() { return exemption_method; }

    public void setExemptionMethod(String exemptionMethod) { this.exemption_method = exemptionMethod; }
}