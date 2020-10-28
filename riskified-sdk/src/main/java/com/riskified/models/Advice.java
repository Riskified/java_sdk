package com.riskified.models;

public class Advice {

    private Boolean inRegulatoryScope;
    private String recommendation;
    private int score;

    public Advice(Boolean inRegulatoryScope) {
        this.inRegulatoryScope = inRegulatoryScope;
    }

    public Boolean getInRegulatoryScope() { return inRegulatoryScope;}

    public void setInRegulatoryScope(Boolean inRegulatoryScope) { this.inRegulatoryScope = inRegulatoryScope;}

    public String getRecommendation() { return recommendation; }

    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

}
