package com.riskified.models;

public class Recommendation {
    private String type;
    private String recommendation;
    private Boolean recommended;


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getRecommendation() {
        return recommendation;
    }
    public void setRecommendation(String recommended) {
        this.recommendation = recommendation;
    }

    public Boolean getRecommended() {
        return recommended;
    }
    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }
}
