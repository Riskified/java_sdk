package com.riskified.models;

import java.lang.reflect.Field;
import java.util.Date;

import com.riskified.validations.*;

public class RideLineItem extends LineItem {

//    Ride Industry fields
    private Date pickupDate;
    private Float pickupLatitude;
    private Float pickupLongitude;
    private Address pickupAddress;
    private Date dropoffDate;
    private Float dropoffLatitude;
    private Float dropoffLongitude;
    private Address dropoffAddress;
    private String transportMethod;
    private String priceBy;
    private String vehicleClass;
    private String carrierName;
    private String driverId;
    private String tariff;
    private String noteToDriver;
    private String meetNGreet;
    private String cancellationPolicy;
    private Float authorizedPayments;
    private int routeIndex;
    private int legIndex;

    public RideLineItem(double price, int quantity, String title, Date pickupDate, int routeIndex, int legIndex) {
        super(price, quantity, title);
        this.pickupDate = pickupDate;
        this.routeIndex = routeIndex;
        this.legIndex = legIndex;
        this.setProductType("ride");
        this.setRequiresShipping(false);
    }

    public void Validate(Validation validationType)
        throws FieldBadFormatException {
        super.validate(validationType);

        if (validationType == Validation.ALL) {
            Validate.notNull(this, this.pickupDate, "Pickup Date");
            Validate.notNull(this, this.routeIndex, "Route Index");
            Validate.notNull(this, this.legIndex, "Leg Index");
        }
    }

    public Date getPickupDate() { return pickupDate; }

    public void setPickupDate(Date pickupDate) { this.pickupDate = pickupDate; }

    public Float getPickupLatitude() { return pickupLatitude; }

    public void setPickupLatitude(Float pickupLatitude) { this.pickupLatitude = pickupLatitude; }

    public Float getPickupLongitude() { return pickupLongitude; }

    public void setPickupLongitude(Float pickupLongitude) { this.pickupLongitude = pickupLongitude; }

    public Address getPickupAddress() { return pickupAddress; }

    public void setPickupAddress(Address pickupAddress) { this.pickupAddress = pickupAddress; }

    public Date getDropoffDate() { return dropoffDate; }

    public void setDropoffDate(Date dropoffDate) { this.dropoffDate = dropoffDate; }

    public Float getDropoffLatitude() { return dropoffLatitude; }

    public void setDropoffLatitude(Float dropoffLatitude) { this.dropoffLatitude = dropoffLatitude; }

    public Float getDropoffLongitude() { return dropoffLongitude; }

    public void setDropoffLongitude(Float dropoffLongitude) { this.dropoffLongitude = dropoffLongitude; }

    public Address getDropoffAddress() { return dropoffAddress; }

    public void setDropoffAddress(Address dropoffAddress) { this.dropoffAddress = dropoffAddress; }

    public String getTransportMethod() { return transportMethod; }

    public void setTransportMethod(String transportMethod) { this.transportMethod = transportMethod; }

    public String getPriceBy() { return priceBy; }

    public void setPriceBy(String priceBy) { this.priceBy = priceBy; }

    public String getVehicleClass() { return vehicleClass; }

    public void setVehicleClass(String vehicleClass) { this.vehicleClass = vehicleClass; }

    public String getCarrierName() { return carrierName; }

    public void setCarrierName(String carrierName) { this.carrierName = carrierName; }

    public String getDriverId() { return driverId; }

    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getTariff() { return tariff; }

    public void setTariff(String tariff) { this.tariff = tariff; }

    public String getNoteToDriver() { return noteToDriver; }

    public void setNoteToDriver(String noteToDriver) { this.noteToDriver = noteToDriver; }

    public String getMeetNGreet() { return meetNGreet; }

    public void setMeetNGreet(String meetNGreet) { this.meetNGreet = meetNGreet; }

    public String getCancellationPolicy() { return cancellationPolicy; }

    public void setCancellationPolicy(String cancellationPolicy) { this.cancellationPolicy = cancellationPolicy; }

    public Float getAuthorizedPayments() { return authorizedPayments; }

    public void setAuthorizedPayments(Float authorizedPayments) { this.authorizedPayments = authorizedPayments; }

    public int getRouteIndex() { return routeIndex; }

    public void setRouteIndex(int routeIndex) { this.routeIndex = routeIndex; }

    public int getLegIndex() { return legIndex; }

    public void setLegIndex(int legIndex) { this.legIndex = legIndex; }
}
