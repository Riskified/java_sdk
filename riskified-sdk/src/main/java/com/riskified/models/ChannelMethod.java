package com.riskified.models;
import com.google.gson.annotations.SerializedName;

public class ChannelMethod {
    private ChannelType channelType;
    private String senderName;

    public ChannelMethod(ChannelType channelType, String senderName) {
        this.channelType = channelType;
        this.senderName = senderName;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}





