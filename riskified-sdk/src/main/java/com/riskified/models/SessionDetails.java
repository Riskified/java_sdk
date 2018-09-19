package com.riskified.models;

import java.util.*;
import com.riskified.validations.*;

public class SessionDetails implements IValidated {
    private Date createdAt;
    private String cartToken;
    private String browserIp;
    private SessionSource source;
    private String referringSite;

    public SessionDetails(Date createdAt, String cartToken, String browserIp, SessionSource source) {
        this.createdAt = createdAt;
        this.cartToken = cartToken;
        this.browserIp = browserIp;
        this.source = source;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.createdAt, "Created At");
            Validate.notNullOrEmpty(this, this.cartToken, "Cart Token");
            Validate.notNullOrEmpty(this, this.browserIp, "Browser IP");
            Validate.notNull(this, this.source, "SessionSource");
        }
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    private void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCartToken() {
        return cartToken;
    }

    private void setCartToken(String cartToken) {
        this.cartToken = cartToken;
    }

    public String getBrowserIp() {
        return browserIp;
    }

    public void setBrowserIp(String browserIp) {
        this.browserIp = browserIp;
    }

    public SessionSource getSource() {
        return source;
    }

    public void setSource(SessionSource source) {
        this.source = source;
    }

    public String getReferringSite() {
        return referringSite;
    }

    public void setReferringSite(String referringSite) {
        this.referringSite = referringSite;
    }
}
