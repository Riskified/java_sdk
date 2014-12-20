package com.riskified.models;

import java.util.Date;

/**
 * Cancel Order details
 */
public class CancelOrder {
    private String id;
    private String cancelReason;
    private Date cancelledAt;

    @Override
    public String toString() {
        return "CancelOrder{" +
                "id='" + id + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                ", cancelledAt=" + cancelledAt +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }
}
