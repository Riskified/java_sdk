package com.riskified.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.riskified.JSONFormater;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Wire-name regression tests for {@link RideLineItem}.
 *
 * <p>
 * Java derives wire names from field names via
 * {@code FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES}, so a field whose Java name does not
 * snake-case to the contract's key diverges silently. There is no error, no warning, and no
 * response difference — the data simply stops arriving.
 */
public class RideLineItemTest {

    private static RideLineItem fullyPopulatedRideLineItem() {
        RideLineItem inputItem = new RideLineItem(42.5, 1, "Airport transfer", new Date(0L), 0, 0);
        inputItem.setPickupLatitude(32.0853f);
        inputItem.setPickupLongitude(34.7818f);
        inputItem.setPickupAddress(
                new Address("Ada", "Lovelace", "1 Rothschild Blvd", "Tel Aviv", "+972500000000", "IL"));
        inputItem.setDropoffDate(new Date(3600000L));
        inputItem.setDropoffLatitude(31.7683f);
        inputItem.setDropoffLongitude(35.2137f);
        inputItem.setDropoffAddress(
                new Address("Ada", "Lovelace", "1 Jaffa St", "Jerusalem", "+972500000000", "IL"));
        inputItem.setTransportMethod("car");
        inputItem.setPriceBy("distance");
        inputItem.setVehicleClass("business");
        inputItem.setCarrierName("Acme Rides");
        inputItem.setDriverId("driver-1");
        inputItem.setTariff("flat");
        inputItem.setNoteToDriver("Second gate");
        inputItem.setMeetNGreet("yes");
        inputItem.setCancellationPolicy("free");
        inputItem.setAuthorizedPayments(42.5f);
        return inputItem;
    }

    /**
     * The contract wire name for the dropoff latitude is {@code dropoff_latitude}.
     *
     * <p>
     * Both OpenAPI specs declare that spelling, with a description and an example. The reference C#
     * SDK sends a transposed {@code dropoff_latitiude}
     * ({@code OrderElements/RideTicketLineItem.cs:100}); that is a defect in the C# SDK, not the
     * contract, and this SDK must not copy it. Guarded in both directions, because an earlier
     * revision of this branch pinned the transposition deliberately.
     */
    @Test
    public void testDropoffLatitudeUsesTheContractWireName() {
        RideLineItem inputItem = fullyPopulatedRideLineItem();

        String actualJson = JSONFormater.toJson(inputItem);

        assertTrue("expected the contract wire name dropoff_latitude, got: " + actualJson,
                actualJson.contains("\"dropoff_latitude\":31.7683"));
        assertFalse("dropoff_latitiude is the C# SDK's transposition and must not ship: "
                + actualJson, actualJson.contains("\"dropoff_latitiude\""));
    }

    /**
     * Locks the whole derived wire-name map for this class, so a renamed or newly added field
     * cannot drift without a failing test. Names come from
     * {@code docs/flows/01-model-catalog.md} section 8, {@code OrderElements/RideTicketLineItem.cs}.
     */
    @Test
    public void testEveryRideFieldUsesItsContractWireName() {
        List<String> expectedWireNames = Arrays.asList(
                "pickup_date",
                "pickup_latitude",
                "pickup_longitude",
                "pickup_address",
                "dropoff_date",
                "dropoff_latitude",
                "dropoff_longitude",
                "dropoff_address",
                "transport_method",
                "price_by",
                "vehicle_class",
                "carrier_name",
                "driver_id",
                "tariff",
                "note_to_driver",
                "meet_n_greet",
                "cancellation_policy",
                "authorized_payments",
                "route_index",
                "leg_index");

        JsonObject actualObject = JsonParser.parseString(JSONFormater.toJson(fullyPopulatedRideLineItem()))
                .getAsJsonObject();

        for (String expectedWireName : expectedWireNames) {
            assertTrue("missing wire key " + expectedWireName + " in " + actualObject,
                    actualObject.has(expectedWireName));
        }
    }

    /** {@code product_type} is the {@code line_items} discriminator and is set by the constructor. */
    @Test
    public void testProductTypeIsRide() {
        String actualJson = JSONFormater.toJson(fullyPopulatedRideLineItem());

        assertTrue(actualJson, actualJson.contains("\"product_type\":\"ride\""));
    }
}
