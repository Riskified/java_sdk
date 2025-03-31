package com.riskified.models;

import java.util.List;

public class Advice {
    private boolean inRegulatoryScope;
    private boolean safeOrder;
    private List<Recommendation> recommendations;


    public boolean getRegulatoryScope() {
        return inRegulatoryScope;
    }

    public void setRegulatoryScope(boolean inRegulatoryScope) {
        this.inRegulatoryScope = inRegulatoryScope;
    }

    public List<Recommendation> getRecommendation() {
        return recommendations;
    }

    public void setRecommendation(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public boolean getSafeOrder() {
        return safeOrder;
    }

    public void setSafeOrder(boolean safeOrder) {
        this.safeOrder = safeOrder;
    }
}

