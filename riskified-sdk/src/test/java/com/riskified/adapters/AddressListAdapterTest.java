package com.riskified.adapters;

import com.google.gson.*;
import com.riskified.models.Address;
import com.riskified.models.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Unit tests for AddressListAdapterFactory to verify backward compatibility handling.
 * Tests the factory-created adapter through Order deserialization.
 */
public class AddressListAdapterTest {

    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Test
    public void testLegacyFormatSingleObject() {
        // Legacy JSON with single address object
        String json = "{\"id\":\"order-123\",\"email\":\"customer@example.com\"," +
                "\"shipping_address\":{\"first_name\":\"John\",\"last_name\":\"Doe\"," +
                "\"address1\":\"123 Main St\",\"city\":\"New York\",\"country\":\"US\",\"phone\":\"555-1234\"}}";

        Order order = gson.fromJson(json, Order.class);

        assertNotNull("Order should not be null", order);
        assertNotNull("Shipping address should not be null", order.getShippingAddress());
        assertEquals("Shipping address should have exactly 1 element", 1, order.getShippingAddress().size());

        Address address = order.getShippingAddress().get(0);
        assertEquals("First name should match", "John", address.getFirstName());
        assertEquals("Last name should match", "Doe", address.getLastName());
        assertEquals("Address1 should match", "123 Main St", address.getAddress1());
        assertEquals("City should match", "New York", address.getCity());
        assertEquals("Country should match", "US", address.getCountry());
        assertEquals("Phone should match", "555-1234", address.getPhone());
    }

    @Test
    public void testCurrentFormatArray() {
        // Current JSON with address array
        String json = "{\"id\":\"order-456\",\"email\":\"customer@example.com\"," +
                "\"shipping_address\":[{\"first_name\":\"Jane\",\"last_name\":\"Smith\"," +
                "\"address1\":\"456 Oak Ave\",\"city\":\"Los Angeles\",\"country\":\"US\",\"phone\":\"555-5678\"}]}";

        Order order = gson.fromJson(json, Order.class);

        assertNotNull("Order should not be null", order);
        assertNotNull("Shipping address should not be null", order.getShippingAddress());
        assertEquals("Shipping address should have exactly 1 element", 1, order.getShippingAddress().size());

        Address address = order.getShippingAddress().get(0);
        assertEquals("First name should match", "Jane", address.getFirstName());
        assertEquals("Last name should match", "Smith", address.getLastName());
        assertEquals("Address1 should match", "456 Oak Ave", address.getAddress1());
        assertEquals("City should match", "Los Angeles", address.getCity());
        assertEquals("Country should match", "US", address.getCountry());
        assertEquals("Phone should match", "555-5678", address.getPhone());
    }

    @Test
    public void testCurrentFormatMultipleAddresses() {
        // Current JSON with multiple addresses
        String json = "{\"id\":\"order-789\",\"email\":\"customer@example.com\"," +
                "\"shipping_address\":[" +
                "{\"first_name\":\"Alice\",\"last_name\":\"Johnson\",\"address1\":\"789 Pine Rd\",\"city\":\"Chicago\",\"country\":\"US\",\"phone\":\"555-1111\"}," +
                "{\"first_name\":\"Bob\",\"last_name\":\"Williams\",\"address1\":\"321 Elm St\",\"city\":\"Boston\",\"country\":\"US\",\"phone\":\"555-2222\"}" +
                "]}";

        Order order = gson.fromJson(json, Order.class);

        assertNotNull("Order should not be null", order);
        assertNotNull("Shipping address should not be null", order.getShippingAddress());
        assertEquals("Shipping address should have exactly 2 elements", 2, order.getShippingAddress().size());

        Address address1 = order.getShippingAddress().get(0);
        assertEquals("First address first name should match", "Alice", address1.getFirstName());
        assertEquals("First address address1 should match", "789 Pine Rd", address1.getAddress1());

        Address address2 = order.getShippingAddress().get(1);
        assertEquals("Second address first name should match", "Bob", address2.getFirstName());
        assertEquals("Second address address1 should match", "321 Elm St", address2.getAddress1());
    }

    @Test
    public void testNullShippingAddress() {
        // JSON with null shipping address
        String json = "{\"id\":\"order-999\",\"email\":\"customer@example.com\",\"shipping_address\":null}";

        Order order = gson.fromJson(json, Order.class);

        assertNotNull("Order should not be null", order);
        assertNull("Shipping address should be null", order.getShippingAddress());
    }

    @Test
    public void testMissingShippingAddress() {
        // JSON without shipping_address field
        String json = "{\"id\":\"order-888\",\"email\":\"customer@example.com\"}";

        Order order = gson.fromJson(json, Order.class);

        assertNotNull("Order should not be null", order);
        assertNull("Shipping address should be null when field is missing", order.getShippingAddress());
    }

    @Test
    public void testMixedShippingAndbillingAddresses() {
        // JSON with both shipping and billing addresses in different formats
        String json = "{\"id\":\"order-777\",\"email\":\"customer@example.com\"," +
                "\"shipping_address\":{\"first_name\":\"Charlie\",\"last_name\":\"Brown\",\"address1\":\"111 Maple Dr\",\"city\":\"Seattle\",\"country\":\"US\",\"phone\":\"555-3333\"}," +
                "\"billing_address\":[{\"first_name\":\"Diana\",\"last_name\":\"Davis\",\"address1\":\"222 Birch Ln\",\"city\":\"Portland\",\"country\":\"US\",\"phone\":\"555-4444\"}]}";

        Order order = gson.fromJson(json, Order.class);

        assertNotNull("Order should not be null", order);

        // Verify shipping address (legacy format - single object)
        assertNotNull("Shipping address should not be null", order.getShippingAddress());
        assertEquals("Shipping address should have exactly 1 element", 1, order.getShippingAddress().size());
        assertEquals("Shipping address first name should match", "Charlie", order.getShippingAddress().get(0).getFirstName());

        // Verify billing address (current format - array)
        assertNotNull("Billing address should not be null", order.getBillingAddress());
        assertEquals("Billing address should have exactly 1 element", 1, order.getBillingAddress().size());
        assertEquals("Billing address first name should match", "Diana", order.getBillingAddress().get(0).getFirstName());
    }

    @Test
    public void testRoundTripSerialization() {
        // Create an order with addresses
        Order order = new Order();
        order.setId("order-555");
        order.setEmail("test@example.com");

        Address address = new Address("Test", "User", "999 Test St", "Testville", "555-9999", "US");

        order.setShippingAddress(Arrays.asList(address));

        // Serialize to JSON
        String json = gson.toJson(order);

        // Verify it's in array format (not object format)
        assertTrue("Serialized JSON should contain shipping_address as array", json.contains("\"shipping_address\":[{"));
        assertFalse("Serialized JSON should NOT have shipping_address as direct object",
                json.matches(".*\"shipping_address\":\\{\"first_name\".*"));

        // Deserialize back
        Order deserialized = gson.fromJson(json, Order.class);

        assertNotNull("Deserialized order should not be null", deserialized);
        assertNotNull("Deserialized shipping address should not be null", deserialized.getShippingAddress());
        assertEquals("Deserialized shipping address should have 1 element", 1, deserialized.getShippingAddress().size());
        assertEquals("First name should match after round-trip", "Test", deserialized.getShippingAddress().get(0).getFirstName());
    }

    @Test(expected = JsonParseException.class)
    public void testInvalidTypeThrowsException() {
        // JSON with invalid type for shipping_address (string instead of object/array)
        String json = "{\"id\":\"order-666\",\"email\":\"customer@example.com\",\"shipping_address\":\"invalid\"}";

        // This should throw JsonParseException due to invalid format
        gson.fromJson(json, Order.class);
    }

    @Test(expected = JsonParseException.class)
    public void testInvalidNumberTypeThrowsException() {
        // JSON with invalid type for shipping_address (number instead of object/array)
        String json = "{\"id\":\"order-333\",\"email\":\"customer@example.com\",\"shipping_address\":12345}";

        // This should throw JsonParseException due to invalid format
        gson.fromJson(json, Order.class);
    }

    @Test
    public void testEmptyArray() {
        // JSON with empty array for shipping_address
        String json = "{\"id\":\"order-444\",\"email\":\"customer@example.com\",\"shipping_address\":[]}";

        Order order = gson.fromJson(json, Order.class);

        assertNotNull("Order should not be null", order);
        assertNotNull("Shipping address should not be null", order.getShippingAddress());
        assertEquals("Shipping address should be empty list", 0, order.getShippingAddress().size());
    }

    @Test
    public void testEmptyObjectInArray() {
        // JSON with empty address object in array
        String json = "{\"id\":\"order-222\",\"email\":\"customer@example.com\",\"shipping_address\":[{}]}";

        Order order = gson.fromJson(json, Order.class);

        assertNotNull("Order should not be null", order);
        assertNotNull("Shipping address should not be null", order.getShippingAddress());
        assertEquals("Shipping address should have 1 element", 1, order.getShippingAddress().size());
        assertNotNull("Address object should not be null", order.getShippingAddress().get(0));
    }
}
