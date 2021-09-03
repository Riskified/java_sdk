package com.riskified.models;

import java.util.ArrayList;
import java.util.List;

import com.riskified.validations.*;

public class RefundOrder extends BaseOrder implements IValidated {

    private List<RefundDetails> refunds;

    public RefundOrder() {
        refunds = new ArrayList<RefundDetails>();
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {

        Validate.notNullOrEmpty(this, this.id, "Id");

        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.refunds, "Refunds");
        }

        if (this.refunds != null) {
            for (RefundDetails refundDetails : this.refunds) {
                refundDetails.validate(validationType);
            }
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RefundDetails> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundDetails> refunds) {
        this.refunds = refunds;
    }

}
