package com.riskified.models;

public class ContactDetails {

    private ContactMethodType contactType;
    private String email;
    private String phone;
    private String facebookAccountUrl;
    private String agentName;
    private String agentId;
    private Integer conversationDuration;
    private Integer waitingDuration;
    private String conversationLanguage;
    private AgentImpression agentImpression;
    private InboundOutbound inboundOutbound;
    private String customerVerifiedBy;

    public ContactDetails(ContactMethodType contactType) {this.contactType = contactType; }

    public ContactMethodType getContactType() { return contactType; }

    public void setContactType(ContactMethodType contactType) { this.contactType = contactType; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getFacebookAccountUrl() { return facebookAccountUrl; }

    public void setFacebookAccountUrl(String facebookAccountUrl) { this.facebookAccountUrl = facebookAccountUrl; }

    public String getAgentName() { return agentName; }

    public void setAgentName(String agentName) { this.agentName = agentName; }

    public String getAgentId() { return agentId; }

    public void setAgentId(String agentId) { this.agentId = agentId; }

    public Integer getConversationDuration() { return conversationDuration; }

    public void setConversationDuration(Integer conversationDuration) { this.conversationDuration = conversationDuration; }

    public Integer getWaitingDuration() { return waitingDuration; }

    public void setWaitingDuration(Integer waitingDuration) { this.waitingDuration = waitingDuration; }

    public String getConversationLanguage() { return conversationLanguage; }

    public void setConversationLanguage(String conversationLanguage) { this.conversationLanguage = conversationLanguage; }

    public AgentImpression getAgentImpression() { return agentImpression; }

    public void setAgentImpression(AgentImpression agentImpression) { this.agentImpression = agentImpression; }

    public InboundOutbound getInboundOutbound() { return inboundOutbound; }

    public void setInboundOutbound(InboundOutbound inboundOutbound) { this.inboundOutbound = inboundOutbound; }

    public String getCustomerVerifiedBy() { return customerVerifiedBy; }

    public void setCustomerVerifiedBy(String customerVerifiedBy) { this.customerVerifiedBy = customerVerifiedBy; }

}
