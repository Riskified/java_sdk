package com.riskified.models;

public class DigitalLineItem extends LineItem {
	
	// Giftcard industry fields
	String senderName;
    String displayName;
    Boolean photoUploaded;
    String photoUrl;
    String greetingPhotoUrl;
    String message;
    String greetingMessage;
    String cardType;
    String cardSubType;
    String senderEmail;
    Recipient recipient;
	
	public DigitalLineItem(double price, int quantity, String title, Recipient recipient) {
		super(price, quantity, title);
		this.recipient = recipient;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getPhotoUploaded() {
		return photoUploaded;
	}

	public void setPhotoUploaded(Boolean photoUploaded) {
		this.photoUploaded = photoUploaded;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getGreetingPhotoUrl() {
		return greetingPhotoUrl;
	}

	public void setGreetingPhotoUrl(String greetingPhotoUrl) {
		this.greetingPhotoUrl = greetingPhotoUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGreetingMessage() {
		return greetingMessage;
	}

	public void setGreetingMessage(String greetingMessage) {
		this.greetingMessage = greetingMessage;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardSubType() {
		return cardSubType;
	}

	public void setCardSubType(String cardSubType) {
		this.cardSubType = cardSubType;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}
}
