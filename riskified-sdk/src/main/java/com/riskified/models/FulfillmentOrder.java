package com.riskified.models;

import java.util.List;

import com.riskified.validations.*;

public class FulfillmentOrder implements IValidated {

    private String id;
    private List<FulfillmentDetails> fulfillments;

    public FulfillmentOrder() {
    }

    public FulfillmentOrder(String id, List<FulfillmentDetails> fulfillments) {
        this.id = id;
        this.fulfillments = fulfillments;
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.id, "Id");
            Validate.notNull(this, fulfillments, "Fulfillments");
        }

        if (this.fulfillments != null) {
            for (FulfillmentDetails fulfillmentDetails : this.fulfillments) {
                fulfillmentDetails.validate(validationType);
            }
        }
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
