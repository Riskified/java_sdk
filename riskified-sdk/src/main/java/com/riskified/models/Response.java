package com.riskified.models;

public class Response {
    private ResOrder order;
    private int received;


    public ResOrder getOrder() {
        return order;
    }

    public void setOrder(ResOrder order) {
        this.order = order;
    }

	public int getReceived() {
		return received;
	}

	public void setReceived(int received) {
		this.received = received;
	}
}
