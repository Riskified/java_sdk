package com.riskified.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Advice {
    @SerializedName(value = "in_regulatory_scope", alternate = "inRegulatoryScope")
    private boolean inRegulatoryScope;
    @SerializedName(value = "safe_order", alternate = "safeOrder")
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

