package com.riskified.models;

public class ClientDetails {
    private  String acceptLanguage;
    private String browserIp;
    private String userAgent;
    private String sessionHash;

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getBrowserIp() {
        return browserIp;
    }

    public void setBrowserIp(String browserIp) {
        this.browserIp = browserIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSessionHash() {
        return sessionHash;
    }

    public void setSessionHash(String sessionHash) {
        this.sessionHash = sessionHash;
    }
}
