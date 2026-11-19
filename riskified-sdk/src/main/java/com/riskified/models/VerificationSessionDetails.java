package com.riskified.models;

import com.google.gson.annotations.SerializedName;

import java.util.*;
import com.riskified.validations.*;

public class VerificationSessionDetails implements IValidated {

    @SerializedName(value = "cart_token", alternate = "cartToken")
    private String cartToken;
    @SerializedName(value = "browser_ip", alternate = "browserIp")
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
