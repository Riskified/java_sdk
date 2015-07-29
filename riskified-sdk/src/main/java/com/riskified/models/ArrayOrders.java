package com.riskified.models;

import java.util.ArrayList;
import java.util.List;

import com.riskified.validations.*;

public class ArrayOrders implements IValidated {
    private List<Order> orders;

    public ArrayOrders() {
        orders = new ArrayList<Order>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void validate(Validation validationType) throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.orders, "Orders");
        }
        if (this.orders != null) {
            for (Order order : this.orders) {
                order.validate(validationType);
            }
        }
    }
}
