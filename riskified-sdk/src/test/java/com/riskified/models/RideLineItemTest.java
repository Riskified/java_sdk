package com.riskified.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.riskified.JSONFormater;
import com.riskified.JSONFormaterTestAccess;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

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
}
