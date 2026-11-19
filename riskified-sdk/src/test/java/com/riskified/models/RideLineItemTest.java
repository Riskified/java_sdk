package com.riskified.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.riskified.JSONFormater;
import com.riskified.JSONFormaterTestAccess;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RideLineItemTest {

    private static RideLineItem ride() {
        return new RideLineItem(42.5, 1, "Airport ride", new Date(0), 0, 1);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void deprecatedAccessorsShareTheValue() {
        RideLineItem item = ride();
        item.setAuthorizedPayments(50.0f);
        assertEquals(Float.valueOf(50.0f), item.getAuthorizedPayment());

        item.setAuthorizedPayment(60.0f);
        assertEquals(Float.valueOf(60.0f), item.getAuthorizedPayments());
    }

    @Test
    public void sendsBothKeys() {
        RideLineItem item = ride();
        item.setAuthorizedPayment(50.0f);

        JsonObject json = JsonParser.parseString(JSONFormater.toJson(item)).getAsJsonObject();

        assertEquals(50.0, json.get("authorized_payment").getAsDouble(), 0);
        assertEquals(50.0, json.get("authorized_payments").getAsDouble(), 0);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void sendsBothKeysWhenSetThroughTheDeprecatedSetter() {
        RideLineItem item = ride();
        item.setAuthorizedPayments(50.0f);

        JsonObject json = JsonParser.parseString(JSONFormater.toJson(item)).getAsJsonObject();

        assertEquals(50.0, json.get("authorized_payment").getAsDouble(), 0);
        assertEquals(50.0, json.get("authorized_payments").getAsDouble(), 0);
    }

    @Test
    public void legacyWireFormatSendsOnlyThePluralKey() {
        RideLineItem item = ride();
        item.setAuthorizedPayment(50.0f);

        JsonObject json = JsonParser.parseString(JSONFormaterTestAccess.toJson(item, true)).getAsJsonObject();

        assertFalse(json.has("authorized_payment"));
        assertEquals(50.0, json.get("authorized_payments").getAsDouble(), 0);
    }

    @Test
    public void sendsNeitherKeyWhenUnset() {
        JsonObject json = JsonParser.parseString(JSONFormater.toJson(ride())).getAsJsonObject();

        assertFalse(json.has("authorized_payment"));
        assertFalse(json.has("authorized_payments"));
    }

    @Test
    public void readsEitherKey() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        assertEquals(Float.valueOf(50.0f), gson.fromJson("{\"authorized_payment\":50.0}", RideLineItem.class).getAuthorizedPayment());
        assertEquals(Float.valueOf(50.0f), gson.fromJson("{\"authorized_payments\":50.0}", RideLineItem.class).getAuthorizedPayment());
        assertNull(gson.fromJson("{}", RideLineItem.class).getAuthorizedPayment());
    }

    // Pickup and dropoff addresses

    private static Address address(String street) {
        return new Address("Jane", "Doe", street, "New York", "+15555550100", "United States");
    }

    @Test
    @SuppressWarnings("deprecation")
    public void deprecatedAddressSetterWrapsInAOneElementList() {
        RideLineItem item = ride();
        Address pickup = address("1 Pickup St");
        item.setPickupAddress(pickup);
        item.setDropoffAddress(address("2 Dropoff St"));

        assertEquals(1, item.getPickupAddresses().size());
        assertTrue(pickup == item.getPickupAddresses().get(0));
        assertEquals("2 Dropoff St", item.getDropoffAddresses().get(0).getAddress1());
        item.getPickupAddresses().add(address("3 Extra St"));
        assertEquals(2, item.getPickupAddresses().size());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void deprecatedAddressGetterReturnsTheFirstOrNull() {
        RideLineItem item = ride();
        assertNull(item.getPickupAddress());

        item.setPickupAddresses(Arrays.asList(address("1 First St"), address("2 Second St")));
        assertEquals("1 First St", item.getPickupAddress().getAddress1());

        item.setDropoffAddresses(new ArrayList<Address>());
        assertNull(item.getDropoffAddress());

        item.setPickupAddress(null);
        assertNull(item.getPickupAddresses());
    }

    @Test
    public void sendsAddressesAsArrays() {
        RideLineItem item = ride();
        item.setPickupAddresses(Arrays.asList(address("1 First St"), address("2 Second St")));
        item.setDropoffAddresses(Arrays.asList(address("3 Third St")));

        JsonObject json = JsonParser.parseString(JSONFormater.toJson(item)).getAsJsonObject();

        assertEquals(2, json.getAsJsonArray("pickup_address").size());
        assertEquals("1 First St", json.getAsJsonArray("pickup_address").get(0).getAsJsonObject().get("address1").getAsString());
        assertEquals(1, json.getAsJsonArray("dropoff_address").size());
    }

    @Test
    public void legacyWireFormatSendsTheFirstAddressAsAnObject() {
        RideLineItem item = ride();
        item.setPickupAddresses(Arrays.asList(address("1 First St"), address("2 Second St")));
        item.setDropoffAddresses(Arrays.asList(address("3 Third St")));

        JsonObject json = JsonParser.parseString(JSONFormaterTestAccess.toJson(item, true)).getAsJsonObject();

        assertEquals("1 First St", json.getAsJsonObject("pickup_address").get("address1").getAsString());
        assertEquals("3 Third St", json.getAsJsonObject("dropoff_address").get("address1").getAsString());
    }

    @Test
    public void legacyWireFormatLeavesOutAnEmptyAddressList() {
        RideLineItem item = ride();
        item.setPickupAddresses(new ArrayList<Address>());

        JsonObject json = JsonParser.parseString(JSONFormaterTestAccess.toJson(item, true)).getAsJsonObject();

        assertFalse(json.has("pickup_address"));
        assertFalse(json.has("dropoff_address"));
    }

    @Test
    public void readsAnAddressAsAnObjectOrAnArray() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        RideLineItem fromObject = gson.fromJson("{\"pickup_address\":{\"address1\":\"1 First St\"}}", RideLineItem.class);
        RideLineItem fromArray = gson.fromJson("{\"pickup_address\":[{\"address1\":\"1 First St\"},{\"address1\":\"2 Second St\"}]}", RideLineItem.class);

        assertEquals(1, fromObject.getPickupAddresses().size());
        assertEquals("1 First St", fromObject.getPickupAddresses().get(0).getAddress1());
        assertEquals(2, fromArray.getPickupAddresses().size());
    }
}
