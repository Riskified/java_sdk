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
    
    public class Advice { 
    	
    	private boolean in_regulatory_scope;
    	private boolean safe_order;
    	private String recommendation;
    	
    	
    	public boolean getRegulatoryScope() {
    		return in_regulatory_scope;
    	}
    	public String getRecommendation() {
    		return recommendation;
    	}
    	
    	public boolean getSafeOrder() {
    		return safe_order;
    	}
    
    }
    
}
