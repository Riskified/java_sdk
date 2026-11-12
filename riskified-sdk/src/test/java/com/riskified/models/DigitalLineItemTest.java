package com.riskified.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.riskified.JSONFormater;
import com.riskified.JSONFormaterTestAccess;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DigitalLineItemTest {

    private static DigitalLineItem item(String cardType, String cardSubType) {
        DigitalLineItem item = new DigitalLineItem(50.0, 1, "Gift card");
        item.setCardType(cardType);
        item.setCardSubType(cardSubType);
        return item;
    }

    private static JsonObject sent(DigitalLineItem item) {
        return JsonParser.parseString(JSONFormater.toJson(item)).getAsJsonObject();
    }

    private static JsonObject sentLegacy(DigitalLineItem item) {
        return JsonParser.parseString(JSONFormaterTestAccess.toJson(item, true)).getAsJsonObject();
    }

    @Test
    public void subTypeOnlyIsAlsoSentAsCardType() {
        JsonObject json = sent(item(null, "birthday"));

        assertEquals("birthday", json.get("card_type").getAsString());
        assertEquals("birthday", json.get("card_sub_type").getAsString());
    }

    @Test
    public void cardTypeOnlyIsAlsoSentAsSubType() {
        JsonObject json = sent(item("gift", null));

        assertEquals("gift", json.get("card_type").getAsString());
        assertEquals("gift", json.get("card_sub_type").getAsString());
    }

    @Test
    public void bothSetKeepTheirOwnValues() {
        JsonObject json = sent(item("gift", "birthday"));

        assertEquals("gift", json.get("card_type").getAsString());
        assertEquals("birthday", json.get("card_sub_type").getAsString());
    }

    @Test
    public void neitherSetSendsNeither() {
        JsonObject json = sent(item(null, null));

        assertFalse(json.has("card_type"));
        assertFalse(json.has("card_sub_type"));
    }

    @Test
    public void legacyWireFormatSendsOnlyWhatWasSet() {
        JsonObject subTypeOnly = sentLegacy(item(null, "birthday"));
        JsonObject typeOnly = sentLegacy(item("gift", null));

        assertFalse(subTypeOnly.has("card_type"));
        assertEquals("birthday", subTypeOnly.get("card_sub_type").getAsString());
        assertEquals("gift", typeOnly.get("card_type").getAsString());
        assertFalse(typeOnly.has("card_sub_type"));
    }

    @Test
    public void leavesTheMerchantsObjectUnchanged() {
        DigitalLineItem item = item(null, "birthday");

        JSONFormater.toJson(item);

        assertEquals(null, item.getCardType());
    }
}
