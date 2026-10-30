package com.riskified.models;

import com.riskified.validations.*;

import java.util.Map;

public class SocialDetails implements IValidated {

    private String network;
    private String publicUsername;
    private int communityScore;
    private String profilePicture;
    private String email;
    private String bio;
    private String accountUrl;
    private int following;
    private int followed;
    private int posts;
    private String id;
    private String authToken;
    private Map<String, Object> socialData;

    public SocialDetails(String network, String publicUserName) {
        this.network = network;
        this.publicUsername = publicUserName;
    }

    public SocialDetails(String network, String publicUserName, String accountUrl) {
        this.network = network;
        this.publicUsername = publicUserName;
        this.accountUrl = accountUrl;
    }

    public void validate(Validation validationType)
    throws FieldBadFormatException {

        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.network, "Network");
            Validate.notNullOrEmpty(this, this.publicUsername, "Public Username");
        }

    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getPublicUsername() {
        return publicUsername;
    }

    public void setPublicUsername(String publicUsername) {
        this.publicUsername = publicUsername;
    }

    public int getCommunityScore() {
        return communityScore;
    }

    public void setCommunityScore(int communityScore) {
        this.communityScore = communityScore;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    /** The customer's ID on the social network. */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /** Any additional data from the social network, sent as a JSON object. */
    public Map<String, Object> getSocialData() {
        return socialData;
    }

    public void setSocialData(Map<String, Object> socialData) {
        this.socialData = socialData;
    }


}
