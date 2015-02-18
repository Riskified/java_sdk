package com.riskified.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FulfillmentDetails {

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
