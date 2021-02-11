package com.riskified.models;

public class Policy {
	private boolean evaluate ;
	
	public Policy( boolean evaluate) {
		this.evaluate = evaluate;
	}

    public boolean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(boolean evaluate) {
        this.evaluate = evaluate;
    }
    
}
