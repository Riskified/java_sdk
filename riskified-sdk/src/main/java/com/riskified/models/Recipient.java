package com.riskified.models;

import com.riskified.validations.*;

public class Recipient implements IValidated {

	String email;
	String phone;
	SocialDetails social;
	
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

}
