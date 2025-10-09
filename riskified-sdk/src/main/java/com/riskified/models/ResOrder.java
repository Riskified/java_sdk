package com.riskified.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

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
    private PolicyProtect policyProtect;

    @SerializedName("risk_indicators")
    private Map<String, Object> riskIndicatorsMap;

	
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

    public PolicyProtect getPolicyProtect() {
        return policyProtect;
    }
    public void setPolicyProtect(PolicyProtect policyProtect) {
        this.policyProtect = policyProtect;
    }


    public Map<String, Object> getRiskIndicatorsMap() {
        return riskIndicatorsMap;
    }

    public void setRiskIndicatorsMap(Map<String, Object> riskIndicatorsMap) {
        this.riskIndicatorsMap = riskIndicatorsMap;
    }

    // Convenience method to get as RiskIndicators object
    public RiskIndicators getRiskIndicators() {
        if (riskIndicatorsMap == null || riskIndicatorsMap.isEmpty()) {
            return null;
        }

        RiskIndicators ri = new RiskIndicators();
        riskIndicatorsMap.forEach(ri::set);
        return ri;
    }

    // Convenience method to set from RiskIndicators object
    public void setRiskIndicators(RiskIndicators riskIndicators) {
        if (riskIndicators == null) {
            this.riskIndicatorsMap = null;
        } else {
            this.riskIndicatorsMap = riskIndicators.getAllProperties();
        }
    }

}
