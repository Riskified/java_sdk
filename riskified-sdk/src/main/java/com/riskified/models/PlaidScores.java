package com.riskified.models;

public class PlaidScores {
	private InitiatedReturnRisk customerInitiatedReturnRisk;
    private InitiatedReturnRisk bankInitiatedReturnRisk;
	
    public InitiatedReturnRisk getCustomerInitiatedReturnRisk() {
        return customerInitiatedReturnRisk;
    }

    public void setCustomerInitiatedReturnRisk(InitiatedReturnRisk customerInitiatedReturnRisk) {
        this.customerInitiatedReturnRisk = customerInitiatedReturnRisk;
    }
    
    public InitiatedReturnRisk getBankInitiatedReturnRisk() {
        return bankInitiatedReturnRisk;
    }

    public void setBankInitiatedReturnRisk(InitiatedReturnRisk bankInitiatedReturnRisk) {
        this.bankInitiatedReturnRisk = bankInitiatedReturnRisk;
    }
}