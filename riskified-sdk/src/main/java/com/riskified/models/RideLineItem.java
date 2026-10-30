package com.riskified.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.riskified.adapters.AddressListAdapterFactory;
import com.riskified.validations.*;

public class RideLineItem extends LineItem {

//    Ride Industry fields
    private Date pickupDate;
    private Float pickupLatitude;
    private Float pickupLongitude;
    // Lists, as documented; a single address object still parses. JSONFormater sends one object in the legacy wire format.
    @JsonAdapter(AddressListAdapterFactory.class)
    private List<Address> pickupAddress;
    private Date dropoffDate;
    private Float dropoffLatitude;
    private Float dropoffLongitude;
    @JsonAdapter(AddressListAdapterFactory.class)
    private List<Address> dropoffAddress;
    private String transportMethod;
    private String priceBy;
    private String vehicleClass;
    private String carrierName;
    private String driverId;
    private String tariff;
    private String noteToDriver;
    private String meetNGreet;
    private String cancellationPolicy;
    // Reads either key; JSONFormater sends both until 7.0.0 (only authorized_payments in the legacy wire format).
    @SerializedName(value = "authorized_payment", alternate = "authorized_payments")
    private Float authorizedPayment;
    private Double damageExcess;
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

    public List<Address> getPickupAddresses() { return pickupAddress; }

    public void setPickupAddresses(List<Address> pickupAddresses) { this.pickupAddress = pickupAddresses; }

    /**
     * @return the first pickup address, or null
     * @deprecated The documented pickup_address is a list; use {@link #getPickupAddresses()}.
     */
    @Deprecated
    public Address getPickupAddress() { return firstOrNull(pickupAddress); }

    /**
     * Replaces the pickup addresses with this one address.
     * @deprecated The documented pickup_address is a list; use {@link #setPickupAddresses(List)}.
     */
    @Deprecated
    public void setPickupAddress(Address pickupAddress) { this.pickupAddress = listOf(pickupAddress); }

    public Date getDropoffDate() { return dropoffDate; }

    public void setDropoffDate(Date dropoffDate) { this.dropoffDate = dropoffDate; }

    public Float getDropoffLatitude() { return dropoffLatitude; }

    public void setDropoffLatitude(Float dropoffLatitude) { this.dropoffLatitude = dropoffLatitude; }

    public Float getDropoffLongitude() { return dropoffLongitude; }

    public void setDropoffLongitude(Float dropoffLongitude) { this.dropoffLongitude = dropoffLongitude; }

    public List<Address> getDropoffAddresses() { return dropoffAddress; }

    public void setDropoffAddresses(List<Address> dropoffAddresses) { this.dropoffAddress = dropoffAddresses; }

    /**
     * @return the first dropoff address, or null
     * @deprecated The documented dropoff_address is a list; use {@link #getDropoffAddresses()}.
     */
    @Deprecated
    public Address getDropoffAddress() { return firstOrNull(dropoffAddress); }

    /**
     * Replaces the dropoff addresses with this one address.
     * @deprecated The documented dropoff_address is a list; use {@link #setDropoffAddresses(List)}.
     */
    @Deprecated
    public void setDropoffAddress(Address dropoffAddress) { this.dropoffAddress = listOf(dropoffAddress); }

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

    public Float getAuthorizedPayment() { return authorizedPayment; }

    public void setAuthorizedPayment(Float authorizedPayment) { this.authorizedPayment = authorizedPayment; }

    /** @deprecated The documented key is {@code authorized_payment}; use {@link #getAuthorizedPayment()}. */
    @Deprecated
    public Float getAuthorizedPayments() { return authorizedPayment; }

    /** @deprecated The documented key is {@code authorized_payment}; use {@link #setAuthorizedPayment(Float)}. */
    @Deprecated
    public void setAuthorizedPayments(Float authorizedPayments) { this.authorizedPayment = authorizedPayments; }

    public int getRouteIndex() { return routeIndex; }

    public void setRouteIndex(int routeIndex) { this.routeIndex = routeIndex; }

    public int getLegIndex() { return legIndex; }

    public void setLegIndex(int legIndex) { this.legIndex = legIndex; }

    public Double getDamageExcess() { return damageExcess; }

    public void setDamageExcess(Double damageExcess) { this.damageExcess = damageExcess; }

    private static Address firstOrNull(List<Address> addresses) {
        return addresses == null || addresses.isEmpty() ? null : addresses.get(0);
    }

    // A mutable list, so callers can still add to what getPickupAddresses() / getDropoffAddresses() return.
    private static List<Address> listOf(Address address) {
        if (address == null) {
            return null;
        }
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(address);
        return addresses;
    }
}
