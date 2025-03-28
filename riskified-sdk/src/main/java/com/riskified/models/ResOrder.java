package com.riskified.models;

public class ResOrder {

    private String id;
    private String status;
    private String description;
    private String oldStatus;
    private String decisionCode;
    private String category;
	private int score;
	private AuthenticationType authentication_type;
	private String action;
	private Advice advice;
    private RecoveryEligibility recoveryEligibility;
    private Double riskScore;

	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getDecisionCode() {
        return decisionCode;
    }

    public void setDecisionCode(String decisionCode) {
        this.decisionCode = decisionCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public AuthenticationType getAuthenticationType() { 
    	return authentication_type; 
    }

    public void setAuthenticationType(AuthenticationType authenticationType) { 
    	this.authentication_type = authenticationType; 
    }   
    
    public Advice getAdvice() {
    	return advice;
    }
    
    public void setAdvice(Advice advice) {
    	this.advice = advice;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public RecoveryEligibility getRecoveryEligibility(){return recoveryEligibility;}

    public void setRecoveryEligibility(RecoveryEligibility recoveryEligibility){this.recoveryEligibility = recoveryEligibility;}

    public void setRiskScore(Double riskScore){this.riskScore = riskScore;}

    public Double getRiskScore(){return riskScore;}

}
