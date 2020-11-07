package com.riskified.models;

public class Advice {

    private Boolean inRegulatoryScope;
    private String recommendation;

    public Advice(Boolean inRegulatoryScope) {
        this.inRegulatoryScope = inRegulatoryScope;
    }

    public Boolean getInRegulatoryScope() { return inRegulatoryScope;}

    public void setInRegulatoryScope(Boolean inRegulatoryScope) { this.inRegulatoryScope = inRegulatoryScope;}

    public String getRecommendation() { return recommendation; }

    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

}
