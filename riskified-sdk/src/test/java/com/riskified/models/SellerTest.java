package com.riskified.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.riskified.JSONFormater;
import com.riskified.JSONFormaterTestAccess;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SellerTest {

    private static JsonElement startingPrice(String json) {
        return JsonParser.parseString(json).getAsJsonObject().get("starting_price");
    }

    private static String sent(float price) {
        Seller seller = new Seller(new Customer("seller@example.com", "Sam", "Seller"));
        seller.setStartingPrice(price);
        JsonElement value = startingPrice(JSONFormater.toJson(seller));
        assertTrue("expected a string, got " + value, value.getAsJsonPrimitive().isString());
        return value.getAsString();
    }

    @Test
    public void sendsAStringWithTwoDecimals() {
        assertEquals("99.50", sent(99.5f));
        assertEquals("100.00", sent(100f));
        assertEquals("0.10", sent(0.1f));
    }

    @Test
    public void roundsHalfUpFromTheFloatsDecimalForm() {
        assertEquals("100.00", sent(99.995f));
        assertEquals("1.23", sent(1.234f));
        assertEquals("1.24", sent(1.235f));
    }

    @Test
    public void neverUsesAnExponent() {
        assertEquals("10000000000.00", sent(1.0E10f));
        assertEquals("0.00", sent(1.0E-5f));
    }

    @Test
    public void unsetPriceIsSentAsZero() {
        assertEquals("0.00", sent(0f));
    }

    @Test
    public void legacyWireFormatSendsTheNumber() {
        Seller seller = new Seller(new Customer("seller@example.com", "Sam", "Seller"));
        seller.setStartingPrice(99.5f);

        JsonElement value = startingPrice(JSONFormaterTestAccess.toJson(seller, true));

        assertTrue(value.getAsJsonPrimitive().isNumber());
        assertEquals(99.5, value.getAsDouble(), 0);
    }

    @Test
    public void appliesToASellerNestedInALineItem() {
        Seller seller = new Seller(new Customer("seller@example.com", "Sam", "Seller"));
        seller.setStartingPrice(42f);
        LineItem item = new LineItem(10.0, 1, "Widget", "product-1");
        item.setSeller(seller);

        JsonElement value = JsonParser.parseString(JSONFormater.toJson(item)).getAsJsonObject()
                .getAsJsonObject("seller").get("starting_price");

        assertEquals("42.00", value.getAsString());
    }
}
