package com.riskified.models;

import java.util.*;
import com.riskified.validations.*;

public class VerificationSessionDetails implements IValidated {

    private String cartToken;
    private String browserIp;

    public VerificationSessionDetails() {}

    public void validate(Validation validationType)
            throws FieldBadFormatException {
    }


    public String getCartToken() {
        return cartToken;
    }

    public void setCartToken(String cartToken) {
        this.cartToken = cartToken;
    }

    public String getBrowserIp() {
        return browserIp;
    }

    public void setBrowserIp(String browserIp) {
        this.browserIp = browserIp;
    }

}
