package com.riskified.models;

import java.util.List;

public class FulfillmentOrder {

	private String id;
	private List<FulfillmentDetails> fulfillments;
	

	public FulfillmentOrder(String id, List<FulfillmentDetails> fulfillments)
	{
		this.id = id;
		this.fulfillments = fulfillments;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<FulfillmentDetails> getFulfillments() {
		return fulfillments;
	}
	public void setFulfillments(List<FulfillmentDetails> fulfillments) {
		this.fulfillments = fulfillments;
	}
	
	
}
