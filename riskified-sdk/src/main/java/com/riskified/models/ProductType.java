package com.riskified.models;

/**
 * Documented values for {@code LineItem.product_type}.
 *
 * Gift card and crypto line items use {@link DigitalLineItem}; remittance line items use a plain {@link LineItem}
 * with a {@link Recipient}.
 *
 * @see LineItem#setProductType(String)
 */
public final class ProductType {

    public static final String PHYSICAL = "physical";
    public static final String DIGITAL = "digital";
    public static final String EVENT = "event";
    public static final String TRAVEL = "travel";
    public static final String RIDE = "ride";
    public static final String ACCOMMODATION = "accommodation";
    public static final String REMITTANCE = "remittance";
    public static final String CRYPTO = "crypto";
    public static final String GIFTCARD = "giftcard";

    private ProductType() {
    }
}
