package com.riskified.models;

import com.riskified.validations.*;

import java.util.Date;

public class Recipient implements IValidated {

	String email;
	String phone;
	SocialDetails social;
	private String accountNumber;
	private String routingNumber;
	private Date createdAt;
	private Date updatedAt;
	private boolean selfRecipient;
	
	public Recipient() {
	}

	public void validate(Validation validationType)
			throws FieldBadFormatException {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public SocialDetails getSocial() {
		return social;
	}

	public void setSocial(SocialDetails social) {
		this.social = social;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getRoutingNumber() {
		return routingNumber;
	}
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt(){
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt){
		this.updatedAt = updatedAt;
	}

	public boolean isSelfRecipient() {
		return selfRecipient;
	}

	public void setSelfRecipient(boolean selfRecipient) {
		this.selfRecipient = selfRecipient;
	}
}
