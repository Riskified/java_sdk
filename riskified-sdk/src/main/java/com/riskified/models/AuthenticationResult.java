package com.riskified.models;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.riskified.TransStatusReason;
import com.riskified.TransStatus;
import com.riskified.validations.*;


public class AuthenticationResult implements IValidated {
	private String eci;
	private String cavv;
	private Date createdAt;
	private TransStatus transStatus;
	private TransStatusReason transStatusReason;
	private Boolean liabilityShift;
	private Boolean three_d_challenge;

    @SerializedName("TRA_exemption")
	private Boolean tra_exemption;
	
	

	public AuthenticationResult(String eci) {
	        this.eci = eci;
	        }

    public TransStatusReason getTranStatusReason() {
        return transStatusReason;
    }

    public void setTranStatusReason(TransStatusReason tranStatusReason) {
        this.transStatusReason = tranStatusReason;
    }
    

    public TransStatus getTranStatus() {
        return transStatus;
    }

    public void setTranStatus(TransStatus transStatus) {
        this.transStatus = transStatus;
    }
    
	
    public boolean getLiabilityShift() {
        return liabilityShift;
    }

    public void setLiabilityShift(boolean liabilityShift) {
        this.liabilityShift = liabilityShift;
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

	public boolean get3DChallenge() {
		return three_d_challenge;
	}

	public void set3DChallenge(boolean ThreeDChallenge) {
		this.three_d_challenge = ThreeDChallenge;
	}

	public boolean getTRAExemption() {
		return tra_exemption;
	}

	public void setTRAExemption(boolean TRAExemption) {
		tra_exemption = TRAExemption;
	}
    
}

