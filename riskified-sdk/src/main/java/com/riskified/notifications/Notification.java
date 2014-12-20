package com.riskified.notifications;

public class Notification {

    private NotificationOrder order;

    public static class NotificationOrder {
        private String id;
        private String status;
        private String oldStatus;
        private String description;

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

        public String getOldStatus() {
            return oldStatus;
        }

        public void setOldStatus(String oldStatus) {
            this.oldStatus = oldStatus;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public NotificationOrder getOrder() {
        return order;
    }

    public void setOrder(NotificationOrder order) {
        this.order = order;
    }
}
