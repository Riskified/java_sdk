package com.riskified.models;

public class CheckoutOrderWrapper<T> {

    private T checkout;

    public CheckoutOrderWrapper(T data) {
    	checkout = data;
    }

    public T getOrder() {
        return checkout;
    }

    public void setOrder(T checkout) {
        this.checkout = checkout;
    }
}
