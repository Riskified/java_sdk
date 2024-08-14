package com.riskified.models;

public class OtpInitiate {
    private String id;
    private String challengeAccessToken;
    private String localizationLanguage;
    private String contactDetails;
    private ChannelMethod channelMethod;


    public OtpInitiate(String id, String challengeAccessToken, String localizationLanguage, String contactDetails, ChannelMethod channelMethod) {
        this.id = id;
        this.challengeAccessToken = challengeAccessToken;
        this.localizationLanguage = localizationLanguage;
        this.contactDetails = contactDetails;
        this.channelMethod = channelMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChallengeAccessToken() {
        return challengeAccessToken;
    }

    public void setChallengeAccessToken(String challengeAccessToken) {
        this.challengeAccessToken = challengeAccessToken;
    }

    public String getLocalizationLanguage() {
        return localizationLanguage;
    }

    public void setLocalizationLanguage(String localizationLanguage) {
        this.localizationLanguage = localizationLanguage;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public ChannelMethod getChannelMethod() {
        return channelMethod;
    }

    public void setChannelMethod(ChannelMethod channelMethod) {
        this.channelMethod = channelMethod;
    }


}
