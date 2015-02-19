package com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class FulfillmentDetails implements IValidated {

	private String fulfillmentId;
	private Date createdAt;
	private String status;
    private List<LineItem> lineItems;
    private String trackingCompany;
    private String trackingNumbers;
    private String message;
    private String receipt;
    
    public FulfillmentDetails(String fulfillmentId, Date createdAt, String status)
    {
    	this.fulfillmentId = fulfillmentId;
    	this.createdAt = createdAt;
    	this.status = status;
    	lineItems = new ArrayList<LineItem>();
    }

    public void validate(Validation validationType)
			throws FieldBadFormatException {
		
    	Validate.stringNotNullOrEmpty(fulfillmentId, "Fulfillment Id");
    	Validate.notNull(this.createdAt, "Created At");
    	Validate.stringNotNullOrEmpty(this.status, "Status");
    	
    	if(validationType == Validation.all && this.lineItems != null) {
			for(LineItem lineItem : this.lineItems) {
				lineItem.validate(validationType);
			}
		}
	}
    
	public String getFulfillmentId() {
		return fulfillmentId;
	}
	public void setFulfillmentId(String fulfillmentId) {
		this.fulfillmentId = fulfillmentId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<LineItem> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
	public String getTrackingCompany() {
		return trackingCompany;
	}
	public void setTrackingCompany(String trackingCompany) {
		this.trackingCompany = trackingCompany;
	}
	public String getTrackingNumbers() {
		return trackingNumbers;
	}
	public void setTrackingNumbers(String trackingNumbers) {
		this.trackingNumbers = trackingNumbers;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	
	
}
