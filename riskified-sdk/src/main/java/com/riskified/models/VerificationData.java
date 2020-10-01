package com.riskified.models;

import java.util.Date;

public class VerificationData {

    private String email;
    private String device;
    private String location;
    private Date date;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDevice() { return device; }

    public void setDevice(String device) { this.device = device; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
