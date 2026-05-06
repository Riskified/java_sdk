package com.riskified.notifications;

public class AuthError extends Exception {

    public AuthError() {
        super("Request HMAC signature was either missing or incorrect");
    }
}
