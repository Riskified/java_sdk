package com.riskified.models;

import java.util.Date;

import org.apache.commons.lang3.Validate;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validation;

public class PassengerDetails implements IValidated {

	private String firstName;
	private String lastName;
	private Date birthDate;
	private String passportNationality;
	private String passportIssuer;
	private Date passportIssueDate;
	private Date passportExpriationDate;
	private String visa;
	private double insurancePrice;
	
	public PassengerDetails() {
		
	}
	

	public void validate(Validation validationType) throws FieldBadFormatException {
		
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassportNationality() {
		return passportNationality;
	}

	public void setPassportNationality(String passportNationality) {
		this.passportNationality = passportNationality;
	}

	public String getPassportIssuer() {
		return passportIssuer;
	}

	public void setPassportIssuer(String passpordIssuer) {
		this.passportIssuer = passpordIssuer;
	}

	public Date getPassportIssueDate() {
		return passportIssueDate;
	}

	public void setPassportIssueDate(Date passportIssueDate) {
		this.passportIssueDate = passportIssueDate;
	}

	public Date getPassportExpriationDate() {
		return passportExpriationDate;
	}

	public void setPassportExpriationDate(Date passwortExpriationDate) {
		this.passportExpriationDate = passwortExpriationDate;
	}

	public String getVisa() {
		return visa;
	}

	public void setVisa(String visa) {
		this.visa = visa;
	}

	public double getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(double insurancePrice) {
		this.insurancePrice = insurancePrice;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
