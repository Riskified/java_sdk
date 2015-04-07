package com.riskified.models;

import java.util.Date;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validation;

public class TravelLineItem extends LineItem {
	
	private int flightIndex;
	
	private String departureAirportCode;
	private String arrivalAirportCode;
	private Date departureDate;
	private Date arrivalDate;
	private String departureCountryCode;
	private String arrivalCountryCode;
	private String departureCity;
	private String arrivalCity;
	private String ticketClass;
	private String flightNumber;
	private String flightCarierName;
	private PassengerDetails passenger;
	
	
	
	public TravelLineItem(double price, int quantity, String title, int productId, String sku) {
		super(price, quantity, title, productId, sku);
	}

	public void validate(Validation validationType) throws FieldBadFormatException {
		super.validate(validationType);
	}
	
	public int getFlightIndex() {
		return flightIndex;
	}

	public void setFlightIndex(int flightIndex) {
		this.flightIndex = flightIndex;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
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

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightCarierName() {
		return flightCarierName;
	}

	public void setFlightCarierName(String flightCarierName) {
		this.flightCarierName = flightCarierName;
	}

	public PassengerDetails getPassenger() {
		return passenger;
	}

	public void setPassenger(PassengerDetails passenger) {
		this.passenger = passenger;
	}
	

}
