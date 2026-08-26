package com.riskified;

import com.google.gson.annotations.JsonAdapter;
import com.riskified.adapters.NaiveDateTypeAdapter;
import com.riskified.models.AccommodationLineItem;
import com.riskified.models.AccountBalance;
import com.riskified.models.AuthenticationResult;
import com.riskified.models.AuthorizationError;
import com.riskified.models.BaseOrder;
import com.riskified.models.CancelOrder;
import com.riskified.models.ChargebackDetails;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.Customer;
import com.riskified.models.DecisionDetails;
import com.riskified.models.DisputeDetails;
import com.riskified.models.EventLineItem;
import com.riskified.models.FulfillmentDetails;
import com.riskified.models.KycDetails;
import com.riskified.models.LineItem;
import com.riskified.models.Login;
import com.riskified.models.Order;
import com.riskified.models.Passenger;
import com.riskified.models.RefundDetails;
import com.riskified.models.RideLineItem;
import com.riskified.models.SessionDetails;
import com.riskified.models.TravelLineItem;
import com.riskified.models.WalletPaymentDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * The date split.
 *
 * <p>
 * The contract carries two date formats. 25 fields are offset-bearing
 * ({@code 2026-08-13T10:00:00+00:00}) and 13 are naive, with no offset at all
 * ({@code 2026-08-13T10:00:00}). The reference implementation reaches the split through two CLR
 * types and no configured converter, so the split follows no principle and has to be reproduced
 * field by field. Both lists are in {@code docs/flows/01-model-catalog.md} section 3.
 *
 * <p>
 * Java has a single {@link Date}, so a single registered type adapter cannot express the split. The
 * 13 naive fields carry {@code @JsonAdapter(NaiveDateTypeAdapter.class)}, which Gson gives
 * precedence over the globally registered adapter; everything else takes the offset format.
 */
public class DateSplitTest {

    /** 1970-01-01T00:00:00Z plus 10h — a fixed instant, so the expected strings are literals. */
    private static final Date FIXED_INSTANT = new Date(36000000L);
    private static final String EXPECTED_OFFSET_RENDERING = "1970-01-01T10:00:00+00:00";
    private static final String EXPECTED_NAIVE_RENDERING = "1970-01-01T10:00:00";

    private TimeZone originalTimeZone;

    @Before
    public void setUp() {
        originalTimeZone = TimeZone.getDefault();
    }

    @After
    public void tearDown() {
        TimeZone.setDefault(originalTimeZone);
    }

    /**
     * One offset-bearing field and one naive field in the same payload — the case the fleet report
     * asks for as the first golden fixture.
     */
    @Test
    public void testOneOffsetFieldAndOneNaiveFieldInTheSamePayload() {
        Order inputOrder = new Order();
        inputOrder.setId("ORDER-1");
        inputOrder.setCreatedAt(FIXED_INSTANT);
        RideLineItem inputRide = new RideLineItem(42.5, 1, "Airport transfer", FIXED_INSTANT, 0, 0);
        List<LineItem> inputLineItems = new ArrayList<LineItem>();
        inputLineItems.add(inputRide);
        inputOrder.setLineItems(inputLineItems);

        String actualJson = JSONFormater.toJson(inputOrder);

        // created_at is one of the 25 — offset present.
        assertTrue(actualJson, actualJson.contains("\"created_at\":\"" + EXPECTED_OFFSET_RENDERING + "\""));
        // pickup_date is one of the 13 — no offset, and no trailing Z either.
        assertTrue(actualJson, actualJson.contains("\"pickup_date\":\"" + EXPECTED_NAIVE_RENDERING + "\""));
        assertFalse("pickup_date must not carry an offset: " + actualJson,
                actualJson.contains("\"pickup_date\":\"" + EXPECTED_OFFSET_RENDERING + "\""));
        assertFalse("no naive field may be rendered with a trailing Z: " + actualJson,
                actualJson.contains("\"pickup_date\":\"" + EXPECTED_NAIVE_RENDERING + "Z\""));
    }

    /**
     * The same {@link Date} must serialize identically whatever the JVM default timezone is. It did
     * not before: the formatter used the default timezone, so two machines produced two different
     * payloads — and two different HMACs — for the same object.
     */
    @Test
    public void testSerializationIsIndependentOfTheJvmDefaultTimezone() {
        Order inputOrder = new Order();
        inputOrder.setCreatedAt(FIXED_INSTANT);
        RideLineItem inputRide = new RideLineItem(1.0, 1, "Ride", FIXED_INSTANT, 0, 0);
        List<LineItem> inputLineItems = new ArrayList<LineItem>();
        inputLineItems.add(inputRide);
        inputOrder.setLineItems(inputLineItems);

        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Kiritimati")); // UTC+14
        String actualJsonFarEast = JSONFormater.toJson(inputOrder);
        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Niue")); // UTC-11
        String actualJsonFarWest = JSONFormater.toJson(inputOrder);

        assertEquals(actualJsonFarEast, actualJsonFarWest);
        assertTrue(actualJsonFarEast,
                actualJsonFarEast.contains("\"created_at\":\"" + EXPECTED_OFFSET_RENDERING + "\""));
        assertTrue(actualJsonFarEast,
                actualJsonFarEast.contains("\"pickup_date\":\"" + EXPECTED_NAIVE_RENDERING + "\""));
    }

    /**
     * All 13 naive fields of {@code docs/flows/01-model-catalog.md} section 3, by declaring class
     * and Java field name. Two of the 25 offset-bearing fields have no Java counterpart at all and
     * are recorded in {@link #testOffsetBearingFieldsCarryNoNaiveAdapter()}.
     */
    private static List<Object[]> naiveFields() {
        return Arrays.asList(new Object[][] {
                // LineItem.cs:167 delivered_at
                { LineItem.class, "deliveredAt" },
                // AccommodationLineItem.cs:74 check_in_date, :77 check_out_date
                { AccommodationLineItem.class, "checkInDate" },
                { AccommodationLineItem.class, "checkOutDate" },
                // EventTicketLineItem.cs:81 event_date
                { EventLineItem.class, "eventDate" },
                // RideTicketLineItem.cs:86 pickup_date, :98 dropoff_date
                { RideLineItem.class, "pickupDate" },
                { RideLineItem.class, "dropoffDate" },
                // TravelTicketLineItem.cs:125 departure_date, :131 arrival_date
                { TravelLineItem.class, "departureDate" },
                { TravelLineItem.class, "arrivalDate" },
                // Passenger.cs:54 date_of_birth, :72 document_issue_date, :75 document_expiration_date
                { Passenger.class, "dateOfBirth" },
                { Passenger.class, "documentIssueDate" },
                { Passenger.class, "documentExpirationDate" },
                // AuthenticationResult.cs:41 created_at
                { AuthenticationResult.class, "createdAt" },
                // Login.cs:21 customer_created_at
                { Login.class, "customerCreatedAt" },
        });
    }

    /**
     * The 23 offset-bearing fields that have a Java counterpart.
     *
     * <p>
     * Two of the contract's 25 are not modelled by this SDK at all —
     * {@code Customer.verified_email_at} ({@code Customer.cs:143}) and
     * {@code Customer.first_purchase_at} ({@code Customer.cs:167}). They are missing fields, not
     * misformatted ones, and are out of scope here; when they are added they must take the offset
     * format, which is the default, so no annotation is needed.
     */
    private static List<Object[]> offsetBearingFields() {
        return Arrays.asList(new Object[][] {
                // OrderBase.cs:35 closed_at, :41 created_at, :80 updated_at
                { BaseOrder.class, "closedAt" },
                { BaseOrder.class, "createdAt" },
                { BaseOrder.class, "updatedAt" },
                // OrderCancellation.cs:53 cancelled_at. Java also declares cancelled_at on the order
                // base, which .NET does not; it takes the same format.
                { CancelOrder.class, "cancelledAt" },
                { BaseOrder.class, "cancelledAt" },
                // Customer.cs:113 created_at, :116 updated_at, :149 verified_phone_at, :182 date_of_birth
                { Customer.class, "createdAt" },
                { Customer.class, "updatedAt" },
                { Customer.class, "verifiedPhoneAt" },
                { Customer.class, "dateOfBirth" },
                // CreditCardPaymentDetails.cs:110/:113, WalletPaymentDetails.cs:106/:109
                { CreditCardPaymentDetails.class, "storedPaymentCreatedAt" },
                { CreditCardPaymentDetails.class, "storedPaymentUpdatedAt" },
                { WalletPaymentDetails.class, "storedPaymentCreatedAt" },
                { WalletPaymentDetails.class, "storedPaymentUpdatedAt" },
                // ChargebackDetails.cs:65 chargeback_at, :123 respond_by
                { ChargebackDetails.class, "chargebackAt" },
                { ChargebackDetails.class, "respondBy" },
                // DisputeDetails.cs:56 disputed_at, :62 expected_resolution_date
                { DisputeDetails.class, "disputedAt" },
                { DisputeDetails.class, "expectedResolutionDate" },
                // AuthorizationError.cs:38 created_at
                { AuthorizationError.class, "createdAt" },
                // AccountBalance.cs:51 updated_at
                { AccountBalance.class, "updatedAt" },
                // DecisionDetails.cs:53 decided_at
                { DecisionDetails.class, "decidedAt" },
                // FulfillmentDetails.cs:57 created_at
                { FulfillmentDetails.class, "createdAt" },
                // KycDetails.cs:18 updated_at. NOTE: the Java field is named updateAt, so it derives
                // the wire key update_at rather than updated_at — a separate, unfixed divergence.
                { KycDetails.class, "updateAt" },
                // SessionDetails.cs:18 created_at
                { SessionDetails.class, "createdAt" },
                // PartialRefundDetails.cs:42 refunded_at
                { RefundDetails.class, "refundedAt" },
        });
    }

    /** Every one of the 13 naive fields carries the naive adapter. */
    @Test
    public void testAllThirteenNaiveFieldsCarryTheNaiveAdapter() throws NoSuchFieldException {
        List<Object[]> inputFields = naiveFields();

        assertEquals("the contract lists exactly 13 naive fields", 13, inputFields.size());
        for (Object[] inputField : inputFields) {
            Class<?> declaringClass = (Class<?>) inputField[0];
            String fieldName = (String) inputField[1];
            Field actualField = declaringClass.getDeclaredField(fieldName);
            assertEquals(Date.class, actualField.getType());
            JsonAdapter actualAnnotation = actualField.getAnnotation(JsonAdapter.class);
            assertNotNull(declaringClass.getSimpleName() + "." + fieldName
                    + " is a naive date field and must carry @JsonAdapter(NaiveDateTypeAdapter.class)",
                    actualAnnotation);
            assertEquals(declaringClass.getSimpleName() + "." + fieldName,
                    NaiveDateTypeAdapter.class, actualAnnotation.value());
        }
    }

    /** None of the offset-bearing fields carries it — they fall through to the global adapter. */
    @Test
    public void testOffsetBearingFieldsCarryNoNaiveAdapter() throws NoSuchFieldException {
        List<Object[]> inputFields = offsetBearingFields();

        // 23 of the contract's 25, plus one field Java declares that .NET does not
        // (cancelled_at on the order base, where .NET has it only on OrderCancellation).
        assertEquals(24, inputFields.size());
        for (Object[] inputField : inputFields) {
            Class<?> declaringClass = (Class<?>) inputField[0];
            String fieldName = (String) inputField[1];
            Field actualField = declaringClass.getDeclaredField(fieldName);
            assertEquals(Date.class, actualField.getType());
            JsonAdapter actualAnnotation = actualField.getAnnotation(JsonAdapter.class);
            assertFalse(declaringClass.getSimpleName() + "." + fieldName
                    + " is offset-bearing and must not use the naive adapter",
                    actualAnnotation != null && actualAnnotation.value() == NaiveDateTypeAdapter.class);
        }
    }

    /**
     * No {@link Date} field anywhere in the model package carries the naive adapter unless it is one
     * of the 13. Without this, a future field could pick up the annotation by copy-paste and diverge
     * in the direction the reflective test above cannot see.
     */
    @Test
    public void testOnlyTheThirteenListedFieldsUseTheNaiveAdapter() {
        List<String> expectedAnnotated = new ArrayList<String>();
        for (Object[] naiveField : naiveFields()) {
            expectedAnnotated.add(((Class<?>) naiveField[0]).getName() + "#" + naiveField[1]);
        }

        List<String> actualAnnotated = new ArrayList<String>();
        for (Class<?> modelClass : modelClassesWithDateFields()) {
            for (Field field : modelClass.getDeclaredFields()) {
                if (field.getType() != Date.class) {
                    continue;
                }
                JsonAdapter annotation = field.getAnnotation(JsonAdapter.class);
                if (annotation != null && annotation.value() == NaiveDateTypeAdapter.class) {
                    actualAnnotated.add(modelClass.getName() + "#" + field.getName());
                }
            }
        }

        java.util.Collections.sort(expectedAnnotated);
        java.util.Collections.sort(actualAnnotated);
        assertEquals(expectedAnnotated, actualAnnotated);
    }

    /**
     * Every model class in this SDK that declares a {@link Date} field. Reflection cannot enumerate
     * a package, so the list is explicit — which is the point: adding a {@link Date} field to a new
     * class is a moment to decide which of the two formats it takes.
     */
    private static List<Class<?>> modelClassesWithDateFields() {
        return Arrays.<Class<?>> asList(
                AccommodationLineItem.class, AccountBalance.class, AuthenticationResult.class,
                AuthorizationError.class, com.riskified.models.BankWirePaymentDetails.class,
                BaseOrder.class, CancelOrder.class, ChargebackDetails.class,
                CreditCardPaymentDetails.class, Customer.class, DecisionDetails.class,
                DisputeDetails.class, EventLineItem.class, FulfillmentDetails.class,
                KycDetails.class, LineItem.class, Login.class, Passenger.class,
                com.riskified.models.Recipient.class, RefundDetails.class, RideLineItem.class,
                SessionDetails.class, TravelLineItem.class, com.riskified.models.Verification.class,
                com.riskified.models.VerificationData.class, WalletPaymentDetails.class);
    }

    /** A naive date read back yields the instant it was written from. */
    @Test
    public void testNaiveDateRoundTrips() {
        Date actualParsed = NaiveDateTypeAdapter.parse(EXPECTED_NAIVE_RENDERING);

        assertEquals(FIXED_INSTANT, actualParsed);
    }

    /** An offset-bearing string is still readable by the naive adapter — responses may send either. */
    @Test
    public void testNaiveAdapterAlsoReadsOffsetBearingStrings() {
        Date actualParsed = NaiveDateTypeAdapter.parse("1970-01-01T12:00:00+02:00");

        assertEquals(FIXED_INSTANT, actualParsed);
    }
}
