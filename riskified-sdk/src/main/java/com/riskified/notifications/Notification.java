package com.riskified.notifications;

import com.riskified.models.Custom;

// Shop URL may also be added to the API notifications from Riskified depending on your
// account configuration (contact your Account Manager or Integration Engineer for details).
// The Shop URL will reflect the URL of the Riskified account processing the order in case
// your technical team would like to use the parameter for routing in your back-end systems.

public class Notification {

    private NotificationOrder order;

    public NotificationOrder getOrder() {
        return order;
    }

    public void setOrder(NotificationOrder order) {
        this.order = order;
    }

    public static class NotificationOrder {
        private String id;
        private String status;
        private String oldStatus;
        private String description;
        private String decisionCode;
        private String category;
        private Custom custom;

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

		public String getDecisionCode() {
			return decisionCode;
		}

		public void setDecisionCode(String decisionCode) {
			this.decisionCode = decisionCode;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

        public Custom getCustom() { return custom; }

        public void setCustom(Custom custom) {
            this.custom = custom;
        }
    }
}
