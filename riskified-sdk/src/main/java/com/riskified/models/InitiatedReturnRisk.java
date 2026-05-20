package com.riskified.models;

public class InitiatedReturnRisk {
	private int score; 
    private int riskTier; 
	
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getRiskTier() {
        return riskTier;
    }

    public void setRiskTier(int riskTier) {
        this.riskTier = riskTier;
    }
}