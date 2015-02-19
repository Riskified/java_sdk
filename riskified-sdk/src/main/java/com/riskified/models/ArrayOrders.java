package com.riskified.models;

import java.util.ArrayList;
import java.util.List;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

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

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		
		Validate.notNull(this.orders, "Orders");
		for(Order order : this.orders) {
			order.validate(validationType);
		}
		
	}
}
