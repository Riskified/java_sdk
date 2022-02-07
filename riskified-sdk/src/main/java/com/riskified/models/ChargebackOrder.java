package com.riskified.models;

import com.riskified.validations.*;

public class ChargebackOrder extends BaseOrder implements IValidated {
	private String id;
	private ChargebackDetails chargebackDetails;
	private FulfillmentDetails fulfillment;
	private DisputeDetails disputeDetails;

	public ChargebackOrder() {

	}
	
    public void validate(Validation validationType)
    throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.id, "Id");
        }
    }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ChargebackDetails getChargebackDetails() {
		return chargebackDetails;
	}

	public void setChargebackDetails(ChargebackDetails chargebackDetails) {
		this.chargebackDetails = chargebackDetails;
	}

	public FulfillmentDetails getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(FulfillmentDetails fulfillment) {
		this.fulfillment = fulfillment;
	}

	public DisputeDetails getDisputeDetails() {
		return disputeDetails;
	}

	public void setDisputeDetails(DisputeDetails disputeDetails) {
		this.disputeDetails = disputeDetails;
	}


}
