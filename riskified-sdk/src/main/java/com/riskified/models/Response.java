package com.riskified.models;

import java.util.List;

public class Response {
    private ResOrder order;
    private int received;
    private List<String> warnings;
    private Error error;

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

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
