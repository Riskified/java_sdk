package com.riskified.models;

import com.riskified.validations.*;

import java.util.List;

public class ChargebackOrder extends BaseOrder implements IValidated {
	private ChargebackDetails chargebackDetails;
	private List<FulfillmentDetails> fulfillment;
	private DisputeDetails disputeDetails;

	public ChargebackOrder() {

	}
	
    public void validate(Validation validationType)
    throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.id, "Id");
        }
    }


	public ChargebackDetails getChargebackDetails() {
		return chargebackDetails;
	}

	public void setChargebackDetails(ChargebackDetails chargebackDetails) {
		this.chargebackDetails = chargebackDetails;
	}


	public void setFulfillment(List<FulfillmentDetails> fulfillment) {
		this.fulfillment = fulfillment;
	}

	public DisputeDetails getDisputeDetails() {
		return disputeDetails;
	}

	public void setDisputeDetails(DisputeDetails disputeDetails) {
		this.disputeDetails = disputeDetails;
	}


}
