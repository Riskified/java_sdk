package com.riskified.models;

import java.util.Date;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.riskified.TransStatusReason;
import com.riskified.TransStatus;
import com.riskified.adapters.AuthenticationResultAdapterFactory;
import com.riskified.validations.*;


@JsonAdapter(AuthenticationResultAdapterFactory.class)
public class AuthenticationResult implements IValidated {
	private String eci;
	private String cavv;
	private Date createdAt;
	private TransStatus transStatus;
	private TransStatusReason transStatusReason;
	private Boolean liabilityShift;
	private Boolean threeDChallenge;

    @SerializedName("TRA_exemption")
	private Boolean traExemption;
	
	

	public AuthenticationResult(String eci) {
	        this.eci = eci;
	        }

    public TransStatusReason getTransStatusReason() {
        return transStatusReason;
    }

    public void setTransStatusReason(TransStatusReason transStatusReason) {
        this.transStatusReason = transStatusReason;
    }
    

    public TransStatus getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(TransStatus transStatus) {
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
		return threeDChallenge;
	}

	public void set3DChallenge(boolean ThreeDChallenge) {
		this.threeDChallenge = ThreeDChallenge;
	}

	public boolean getTRAExemption() {
		return traExemption;
	}

	public void setTRAExemption(boolean TRAExemption) {
        traExemption = TRAExemption;
	}
    
}

