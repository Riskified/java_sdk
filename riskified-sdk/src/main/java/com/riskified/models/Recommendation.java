package com.riskified.models;

public class Recommendation {
    private String type;
    private boolean recommended;


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean getIsRecommended() {
        return recommended;
    }
    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }
}
