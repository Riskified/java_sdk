package com.riskified.notifications;

public class AuthError extends Exception {

    public AuthError(String AuthError, String calcAuth) {
        super(String.format("Auth Error, got hash: %s , expected: %s", AuthError, calcAuth));
    }
}
