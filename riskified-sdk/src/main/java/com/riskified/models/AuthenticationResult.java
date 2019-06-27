package com.riskified.models;

import java.util.Date;

import com.riskified.validations.*;


public class AuthenticationResult implements IValidated {
	private String eci;
	private String cavv;
	private Date createdAt;
	private String tranStatus;
	private String tranStatusReason;
	

	public AuthenticationResult(String eci) {
	        this.eci = eci;
	        }

    public String getTranStatusReason() {
        return tranStatusReason;
    }

    public void setTranStatusReason(String tranStatusReason) {
        this.tranStatusReason = tranStatusReason;
    }
    

    public String getTranStatus() {
        return tranStatus;
    }

    public void setTranStatusv(String tranStatus) {
        this.tranStatus = tranStatus;
    }
    
	
    public String getCavv() {
        return cavv;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }
    
    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    
    public void validate(Validation validationType)
    throws FieldBadFormatException {

        if (validationType == Validation.ALL) {

        	// The SDK is not enforcing any fields for Advise API
        }

    }
    
}

