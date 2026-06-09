package com.riskified.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {

    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Test
    public void testPartnerSubMerchantIdNullByDefault() {
        Order order = new Order();
        assertNull(order.getPartnerSubMerchantId());
    }

    @Test
    public void testPartnerSubMerchantIdSerializesWithUnderscores() {
        Order order = new Order();
        order.setId("order-1");
        order.setPartnerSubMerchantId("merchant-abc");
        String json = gson.toJson(order);
        assertTrue(json.contains("\"partner_sub_merchant_id\":\"merchant-abc\""));
    }

    @Test
    public void testPartnerSubMerchantIdDeserializes() {
        String json = "{\"id\":\"order-1\",\"partner_sub_merchant_id\":\"merchant-xyz\"}";
        Order order = gson.fromJson(json, Order.class);
        assertEquals("merchant-xyz", order.getPartnerSubMerchantId());
    }

    @Test
    public void testAiAgentSerializesWithUnderscores() {
        Order order = new Order();
        order.setId("order-1");
        order.setAiAgent(AiAgent.chatgpt);
        String json = gson.toJson(order);
        assertTrue(json.contains("\"ai_agent\":\"chatgpt\""));
    }
}
