package com.riskified.models;

import java.util.ArrayList;
import java.util.List;

public class RefundOrder {

    private String id;
    private List<RefundDetails> refunds;

    public RefundOrder() {
        refunds = new ArrayList<RefundDetails>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RefundDetails> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundDetails> refunds) {
        this.refunds = refunds;
    }

    @Override
    public String toString() {
        return "RefundOrder{" +
                "id='" + id + '\'' +
                ", refunds=" + refunds +
                '}';
    }
}
