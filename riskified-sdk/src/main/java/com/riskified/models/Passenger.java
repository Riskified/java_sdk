package com.riskified.models;

import java.util.Date;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class Passenger implements IValidated {

	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String nationalityCode;
	private String insuranceType;
	private float insurancePrice;
	private String documentNumber;
	private String documentType;
	private Date documentIssueDate;
	private Date documentExpirationDate;
	private String passengerType;
	
	public Passenger(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	

	public void validate(Validation validationType) throws FieldBadFormatException {
		
		if (validationType == Validation.ALL) {
            Validate.notNull(this, this.firstName, "First Name");
            Validate.notNull(this, this.lastName, "Last Name");
            Validate.notNull(this, this.dateOfBirth, "Date Of Birth");
            Validate.notNull(this, this.nationalityCode, "Nationality Code");
            Validate.notNull(this, this.documentNumber, "Document Number");
            Validate.notNull(this, this.documentType, "Document Type");
        }
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getNationalityCode() {
		return nationalityCode;
	}


	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}


	public String getInsuranceType() {
		return insuranceType;
	}


	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}


	public float getInsurancePrice() {
		return insurancePrice;
	}


	public void setInsurancePrice(float insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public Date getDocumentIssueDate() {
		return documentIssueDate;
	}


	public void setDocumentIssueDate(Date documentIssueDate) {
		this.documentIssueDate = documentIssueDate;
	}


	public Date getDocumentExpirationDate() {
		return documentExpirationDate;
	}


	public void setDocumentExpirationDate(Date documentExpirationDate) {
		this.documentExpirationDate = documentExpirationDate;
	}


	public String getPassengerType() {
		return passengerType;
	}


	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}


	public String getDocumentNumber() {
		return documentNumber;
	}


	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}


	public String getDocumentType() {
		return documentType;
	}


	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

}
