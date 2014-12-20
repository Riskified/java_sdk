package com.riskified.models;

import java.util.ArrayList;
import java.util.List;

public class ArrayOrders {
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
}
