package com.riskified.models;

import java.util.Date;

import com.riskified.validations.*;

public class AccommodationLineItem extends LineItem {

	// Hotel industry fields
	private String roomType;
	private String city;
	private String countryCode;
	private Date checkInDate;
	private Date checkOutDate;
	private Float rating;
	private int numberOfGuests;
	private String cancellationPolicy;
	private String accommodationType;

	public AccommodationLineItem(double price, String title, String productId,
			String city, String countryCode, Date checkInDate, Date checkOutDate) {
		super(price, 1, title, productId);
		this.city = city;
		this.countryCode = countryCode;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		super.validate(validationType);

		if (validationType == Validation.ALL) {
			Validate.notNull(this, this.roomType, "Room Type");
			Validate.notNull(this, this.city, "Accommodation City");
			Validate.notNull(this, this.countryCode, "Accommodation Country Code");
			Validate.notNull(this, this.checkInDate, "Check In Date");
			Validate.notNull(this, this.checkOutDate, "Check Out Date");
			Validate.notNull(this, this.numberOfGuests, "Number Of Guests");
			Validate.notNull(this, this.cancellationPolicy,
				"Accommodation Cancellation Policy");
			Validate.notNull(this, this.accommodationType, "Accommodation Type");
		}

		if (this.countryCode != null) {
			Validate.countryCode(this, this.countryCode,
			"Accommodation Country Code");
		}
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public String getCancellationPolicy() {
		return cancellationPolicy;
	}

	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}

	public String getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(String accommodationType) {
		this.accommodationType = accommodationType;
	}
}
