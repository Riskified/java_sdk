package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

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

        if (validationType == Validation.all) {
            Validate.stringNotNullOrEmpty(this, this.network, "Network");
            Validate.stringNotNullOrEmpty(this, this.publicUsername, "Public Username");
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


}
