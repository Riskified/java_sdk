package com.riskified.models;

/**
 * Documented values for {@code transport_method} on travel and ride line items.
 *
 * Travel line items accept only {@link #BUS}, {@link #PLANE}, {@link #SHIP}, {@link #TRAIN} and {@link #TRAM};
 * ride line items ({@link RideLineItem#setTransportMethod(String)}) accept all of them.
 *
 * @see TravelLineItem#setTransportMethod(String)
 */
public final class TransportMethod {

    public static final String BUS = "bus";
    public static final String CAR = "car";
    public static final String CRUISE = "cruise";
    public static final String PLANE = "plane";
    public static final String RV = "rv";
    public static final String SHIP = "ship";
    public static final String TAXI = "taxi";
    public static final String TRAIN = "train";
    public static final String TRAM = "tram";

    private TransportMethod() {
    }
}
