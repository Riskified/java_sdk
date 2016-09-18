package com.riskified.models;

import java.util.Date;

public class DisputeDetails {

	private String caseID;
	private String status;
	private Date disputeAt;
	private Date expectedResolutionDate;
	private String disputeType;
	private String issuerPocPhoneNumber;
	
	public DisputeDetails() {
	}

	public String getCaseID() {
		return caseID;
	}

	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDisputeAt() {
		return disputeAt;
	}

	public void setDisputeAt(Date disputeAt) {
		this.disputeAt = disputeAt;
	}

	public Date getExpectedResolutionDate() {
		return expectedResolutionDate;
	}

	public void setExpectedResolutionDate(Date expectedResolutionDate) {
		this.expectedResolutionDate = expectedResolutionDate;
	}

	public String getDisputeType() {
		return disputeType;
	}

	public void setDisputeType(String disputeType) {
		this.disputeType = disputeType;
	}

	public String getIssuerPocPhoneNumber() {
		return issuerPocPhoneNumber;
	}

	public void setIssuerPocPhoneNumber(String issuerPocPhoneNumber) {
		this.issuerPocPhoneNumber = issuerPocPhoneNumber;
	}

}
