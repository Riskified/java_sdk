package com.riskified.models;

import java.util.List;

public class Advice {
    private boolean in_regulatory_scope;
    private boolean safe_order;
    private List<Recommendation> recommendations;


    public boolean getRegulatoryScope() {
        return in_regulatory_scope;
    }

    public void setRegulatoryScope(boolean in_regulatory_scope) {
        this.in_regulatory_scope = in_regulatory_scope;
    }

    public List<Recommendation> getRecommendation() {
        return recommendations;
    }
    public void setRecommendation(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public boolean getSafeOrder() {
        return safe_order;
    }
    public void setSafeOrder(boolean safe_order) {
        this.safe_order = safe_order;
    }
}

