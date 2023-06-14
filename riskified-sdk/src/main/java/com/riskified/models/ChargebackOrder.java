package com.riskified.models;
import java.util.List;
import com.riskified.validations.*;

public class ChargebackOrder implements IValidated {
	private String id;
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

	public List<FulfillmentDetails> getFulfillment() {
		return fulfillment;
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
