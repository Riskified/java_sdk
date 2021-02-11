package com.riskified.models;

public class Policy {
	private boolean evaluate ;
	
	public Policy( boolean evaluate) {
		this.evaluate = evaluate;
	}

    public boolean getPolicy() {
        return evaluate;
    }

    public void setPolicy(boolean evaluate) {
        this.evaluate = evaluate;
    }
    
}
