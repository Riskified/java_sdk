package com.riskified.models;


public class OrderWrapper<T> {

    private T order;

    public OrderWrapper(T data) {
        order = data;
    }

    public T getOrder() {
        return order;
    }

    public void setOrder(T order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderWrapper{" +
                "order=" + order +
                '}';
    }
}
