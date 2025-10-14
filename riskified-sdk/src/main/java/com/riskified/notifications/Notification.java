package com.riskified.notifications;

import com.google.gson.annotations.SerializedName;
import com.riskified.models.*;

import java.util.Map;

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
        private RecoveryEligibility recoveryEligibility;
        private Context context;
        private String[] decision_reasons;
        private PolicyProtect policyProtect;
        @SerializedName("risk_indicators")
        private Map<String, Object> riskIndicatorsMap;

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

        public RecoveryEligibility getRecoveryEligibility(){ return recoveryEligibility; }

        public void setRecoveryEligibility(RecoveryEligibility recoveryEligibility){this.recoveryEligibility = recoveryEligibility;}

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public String[] getDecisionReasons() {
            return decision_reasons;
        }

        public void setDecisionReasons(String[] decisionReasons) {
            this.decision_reasons = decisionReasons;
        }

        public PolicyProtect getPolicyProtect() {
            return policyProtect;
        }
        public void setPolicyProtect(PolicyProtect policyProtect) {}

        public Map<String, Object> getRiskIndicatorsMap() {
            return riskIndicatorsMap;
        }

        public void setRiskIndicatorsMap(Map<String, Object> riskIndicatorsMap) {
            this.riskIndicatorsMap = riskIndicatorsMap;
        }

        // Convenience method to get as RiskIndicators object
        public RiskIndicators getRiskIndicators() {
            if (riskIndicatorsMap == null || riskIndicatorsMap.isEmpty()) {
                return null;
            }

            RiskIndicators ri = new RiskIndicators();
            riskIndicatorsMap.forEach((key, value) -> ri.set(key, value));
            return ri;
        }

        // Convenience method to set from RiskIndicators object
        public void setRiskIndicators(RiskIndicators riskIndicators) {
            if (riskIndicators == null) {
                this.riskIndicatorsMap = null;
            } else {
                this.riskIndicatorsMap = riskIndicators.getAllProperties();
            }
        }
    }
}
