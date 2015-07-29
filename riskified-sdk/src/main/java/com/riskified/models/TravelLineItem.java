package com.riskified.models;

import java.util.Date;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class TravelLineItem extends LineItem {

	private int routeIndex;
	private int legIndex;

	private String departurePortCode;
	private String arrivalPortCode;
	private Date departureDate;
	private Date arrivalDate;
	private String departureCountryCode;
	private String arrivalCountryCode;
	private String departureCity;
	private String arrivalCity;
	private String ticketClass;
	private String legId;
	private String carrierName;
	private String carrierCode;

	public TravelLineItem(double price, int quantity, String title,
			int productId, String legId, int legIndex, int routeIndex) {
		super(price, quantity, title, productId);
		this.legId = legId;
		this.legIndex = legIndex;
		this.routeIndex = routeIndex;
	}

	public TravelLineItem(double price, int quantity, String title,
			int productId) {
		super(price, quantity, title, productId);
	}

	public void validate(Validation validationType)
			throws FieldBadFormatException {
		super.validate(validationType);

		if (validationType == Validation.ALL) {
			Validate.notNull(this, this.routeIndex, "Route Index");
			Validate.notNull(this, this.legIndex, "Leg Index");
			Validate.notNull(this, this.legId, "Leg Id");
			Validate.notNull(this, this.departureCity, "Departure City");
			Validate.notNull(this, this.arrivalCity, "Arrival City");
			Validate.notNull(this, this.departureCountryCode,
					"Departure Country Code");
			Validate.notNull(this, this.arrivalCountryCode,
					"Arrival Country Code");
			Validate.notNull(this, this.departureDate, "Departure Date");
		}

		if (this.departureCountryCode != null) {
			Validate.countryCode(this, this.departureCountryCode,
					"Departure Country Code");
		}
		if (this.arrivalCountryCode != null) {
			Validate.countryCode(this, this.arrivalCountryCode,
					"Arrival Country Code");
		}

	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getDepartureCountryCode() {
		return departureCountryCode;
	}

	public void setDepartureCountryCode(String departureCountryCode) {
		this.departureCountryCode = departureCountryCode;
	}

	public String getArrivalCountryCode() {
		return arrivalCountryCode;
	}

	public void setArrivalCountryCode(String arrivalCountryCode) {
		this.arrivalCountryCode = arrivalCountryCode;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getTicketClass() {
		return ticketClass;
	}

	public void setTicketClass(String ticketClass) {
		this.ticketClass = ticketClass;
	}

	public String getLegId() {
		return legId;
	}

	public void setLegId(String legId) {
		this.legId = legId;
	}

	public String getDeparturePortCode() {
		return departurePortCode;
	}

	public void setDeparturePortCode(String departurePortCode) {
		this.departurePortCode = departurePortCode;
	}

	public String getArrivalPortCode() {
		return arrivalPortCode;
	}

	public void setArrivalPortCode(String arrivalPortCode) {
		this.arrivalPortCode = arrivalPortCode;
	}

	public int getRouteIndex() {
		return routeIndex;
	}

	public void setRouteIndex(int routeIndex) {
		this.routeIndex = routeIndex;
	}

	public int getLegIndex() {
		return legIndex;
	}

	public void setLegIndex(int legIndex) {
		this.legIndex = legIndex;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

}
