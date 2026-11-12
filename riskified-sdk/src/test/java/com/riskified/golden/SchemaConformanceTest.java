package com.riskified.golden;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SchemaLocation;
import com.networknt.schema.SchemaValidatorsConfig;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.riskified.JSONFormater;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Validates the {@link GoldenOrders} samples against the OpenAPI definitions from the API docs
 * (vendored in {@code src/test/resources/schema/}). Undocumented keys are allowed.
 *
 * <p>Any violation fails the test, except deliberate docs differences listed, with a reason, in
 * {@code schema/accepted-differences.json}. For a new violation, fix the SDK, or, if the difference is
 * decided, add it there.
 */
@RunWith(Parameterized.class)
public class SchemaConformanceTest {

    private static final Path ACCEPTED_DIFFERENCES = Paths.get("src", "test", "resources", "schema", "accepted-differences.json");

    private static final String POST_SUBMIT = "post-auth-flow-submit";
    private static final String POST_DECIDE = "post-auth-flow-decide";
    private static final String PRE_DECIDE = "pre-auth-flow-decide";
    private static final String POST_CHECKOUT_DENIED = "post-auth-flow-checkout_denied";
    private static final String PRE_CHECKOUT_DENIED = "pre-auth-flow-checkout_denied";
    private static final String PRE_DECISION = "pre-auth-flow-decision";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JsonSchemaFactory FACTORY = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
    private static final SchemaValidatorsConfig CONFIG = SchemaValidatorsConfig.builder().locale(Locale.ENGLISH).build();

    // Docs mismatches kept on purpose (schema/accepted-differences.json). Left out before comparing.
    private static List<AcceptedDifference> acceptedDifferences;
    private static TimeZone originalTimeZone;

    private final Check check;

    public SchemaConformanceTest(String id, Check check) {
        this.check = check;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> checks() {
        List<Check> checks = new ArrayList<Check>();

        // Whole request bodies. The line-item samples are minimal order shells, so only their line item is checked.
        for (String sample : new String[]{"card_order", "paypal_order", "wallet_order", "bank_wire_order"}) {
            for (String doc : new String[]{POST_SUBMIT, POST_DECIDE, PRE_DECIDE}) {
                checks.add(new Check(sample, "", doc, "order_wrapper"));
            }
        }
        checks.add(new Check("checkout_denied_order", "", POST_CHECKOUT_DENIED, "checkout_denied_order_wrapper"));
        checks.add(new Check("checkout_denied_order", "", PRE_CHECKOUT_DENIED, "checkout_denied_order_wrapper"));
        checks.add(new Check("decision_order", "", PRE_DECISION, "decision_order_wrapper"));

        // The docs combine payment and line-item types with anyOf, and extra keys are allowed, so a whole-body
        // check passes almost anything. Checking each part against its own type is what surfaces real gaps.
        String payment = "/order/payment_details/0";
        checks.add(new Check("card_order", payment, POST_SUBMIT, "credit_card_payment_details_post_auth"));
        checks.add(new Check("card_order", payment, POST_DECIDE, "credit_card_payment_details_post_auth"));
        checks.add(new Check("card_order", payment, PRE_DECIDE, "credit_card_payment_details_pre_auth"));
        checks.add(new Check("paypal_order", payment, POST_SUBMIT, "paypal_payment_details_details_post_auth"));
        checks.add(new Check("paypal_order", payment, POST_DECIDE, "paypal_payment_details_details_post_auth"));
        checks.add(new Check("paypal_order", payment, PRE_DECIDE, "paypal_payment_details_details_pre_auth"));
        for (String doc : new String[]{POST_SUBMIT, POST_DECIDE, PRE_DECIDE}) {
            checks.add(new Check("wallet_order", payment, doc, "digital_wallets_payment_details"));
            checks.add(new Check("bank_wire_order", payment, doc, "bank_wire_payment_details"));
        }
        checks.add(new Check("checkout_denied_order", "/checkout/payment_details/0", POST_CHECKOUT_DENIED,
                "credit_card_payment_details_post_auth_checkout_denied"));
        checks.add(new Check("checkout_denied_order", "/checkout/payment_details/0", PRE_CHECKOUT_DENIED,
                "credit_card_payment_details_pre_auth_checkout_denied"));
        checks.add(new Check("decision_order", payment, PRE_DECISION, "credit_card_payment_details_pre_auth_decision"));
        checks.add(new Check("card_order_tokenized", payment, POST_SUBMIT, "credit_card_payment_details_post_auth"));
        checks.add(new Check("card_order_tokenized", payment, POST_DECIDE, "credit_card_payment_details_post_auth"));
        checks.add(new Check("card_order_tokenized", payment, PRE_DECIDE, "credit_card_payment_details_pre_auth"));
        checks.add(new Check("wallet_checkout_denied_order", "/checkout/payment_details/0", POST_CHECKOUT_DENIED,
                "digital_wallets_payment_details_post_auth_checkout_denied"));
        checks.add(new Check("wallet_checkout_denied_order", "/checkout/payment_details/0", PRE_CHECKOUT_DENIED,
                "digital_wallets_payment_details_pre_auth_checkout_denied"));
        // Pre-auth /checkout_denied requires authorization_error on card and wallet details; these get it from the root.
        checks.add(new Check("checkout_denied_root_error_order", "/checkout/payment_details/0", PRE_CHECKOUT_DENIED,
                "credit_card_payment_details_pre_auth_checkout_denied"));
        checks.add(new Check("checkout_denied_root_error_order", "/checkout/payment_details/1", PRE_CHECKOUT_DENIED,
                "digital_wallets_payment_details_pre_auth_checkout_denied"));
        for (String doc : new String[]{POST_SUBMIT, POST_DECIDE, PRE_DECIDE}) {
            checks.add(new Check("alternative_payments_order", payment, doc, "buy_now_pay_later_payment_details"));
            checks.add(new Check("alternative_payments_order", "/order/payment_details/1", doc, "non_chb_payment_details"));
            checks.add(new Check("customer_profile_order", "/order/customer", doc, "customer"));
            checks.add(new Check("invoice_and_address_fields_order", "/order/shipping_address/0", doc, "address"));
            checks.add(new Check("invoice_and_address_fields_order", "/order/billing_address/0", doc, "address"));
            // A property schema of its own, because the minimal order shell lacks the order's required fields.
            checks.add(new Check("invoice_and_address_fields_order", "/order/invoice_id", doc, "base_order/properties/invoice_id"));
            checks.add(new Check("crypto_and_social_order", "/order/customer", doc, "customer"));
            checks.add(new Check("crypto_and_social_order", "/order/customer/crypto_wallets/0", doc, "crypto_wallet"));
            checks.add(new Check("crypto_and_social_order", "/order/customer/social/0", doc, "social_details"));
            checks.add(new Check("crypto_and_social_order", "/order/line_items/0/recipient", doc, "recipient_ACH"));
            checks.add(new Check("crypto_and_social_order", "/order/line_items/1/recipient", doc, "recipient_crypto"));
            checks.add(new Check("crypto_and_social_order", "/order/shipping_lines/0", doc, "shipping_line"));
            checks.add(new Check("rewards_points_order", "/order/payment_details/0", doc, "non_chb_payment_details"));
            checks.add(new Check("rewards_points_order", "/order/payment_details/1", doc, "non_chb_payment_details"));
        }
        // Pre-auth /decide doesn't define authentication_result as a schema of its own.
        for (String doc : new String[]{POST_SUBMIT, POST_DECIDE}) {
            checks.add(new Check("card_order_trans_status_s", "/order/payment_details/0/authentication_result", doc,
                    "authentication_result"));
        }

        String lineItem = "/order/line_items/0";
        checks.add(new Check("card_order", lineItem, POST_SUBMIT, "physical_line_item"));
        checks.add(new Check("digital_line_item_order", lineItem, POST_SUBMIT, "digital_line_item"));
        checks.add(new Check("travel_line_item_order", lineItem, POST_SUBMIT, "travel_line_item"));
        checks.add(new Check("ride_line_item_order", lineItem, POST_SUBMIT, "rides_line_item"));
        checks.add(new Check("event_line_item_order", lineItem, POST_SUBMIT, "events_line_item"));
        checks.add(new Check("accommodation_line_item_order", lineItem, POST_SUBMIT, "accommodation_line_item"));
        checks.add(new Check("line_item_fields_order", "/order/line_items/0", POST_SUBMIT, "physical_line_item"));
        checks.add(new Check("line_item_fields_order", "/order/line_items/1", POST_SUBMIT, "events_line_item"));
        checks.add(new Check("line_item_fields_order", "/order/line_items/2", POST_SUBMIT, "travel_line_item"));
        checks.add(new Check("line_item_fields_order", "/order/line_items/3", POST_SUBMIT, "rides_line_item"));

        Collection<Object[]> parameters = new ArrayList<Object[]>();
        for (Check c : checks) {
            parameters.add(new Object[]{c.id(), c});
        }
        return parameters;
    }

    // DateTimeSerializer formats with the JVM default time zone.
    @BeforeClass
    public static void setUp() throws IOException {
        originalTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        acceptedDifferences = readAcceptedDifferences();
    }

    @AfterClass
    public static void tearDown() {
        TimeZone.setDefault(originalTimeZone);
    }

    @Test
    public void conformsToTheDocs() throws IOException {
        Object payload = GoldenOrders.all().get(check.sample);
        assertTrue("Unknown sample " + check.sample, payload != null);

        JsonNode instance = MAPPER.readTree(JSONFormater.toJson(payload)).at(check.pointer);
        assertTrue("Nothing at " + check.pointer + " in " + check.sample, !instance.isMissingNode());

        JsonSchema schema = FACTORY.getSchema(SchemaLocation.of(
                "classpath:schema/" + check.doc + ".json#/components/schemas/" + check.schema), CONFIG);
        Set<String> actual = new TreeSet<String>();
        for (ValidationMessage message : schema.validate(instance)) {
            // A failing anyOf reports every branch's errors. The parts under anyOf (payment details and line items)
            // have their own targeted checks, so whole-body checks skip them to keep the list readable.
            if (check.pointer.isEmpty() && message.getEvaluationPath().toString().contains("anyOf")) {
                continue;
            }
            if (isAccepted(check.doc, message.getMessage())) {
                continue;
            }
            actual.add(message.getMessage());
        }
        if (!actual.isEmpty()) {
            fail(check.id() + " doesn't conform to the docs: " + actual
                    + "\nFix the SDK, or, if the difference is decided, add it to schema/accepted-differences.json"
                    + " with the reason.");
        }
    }

    private static List<AcceptedDifference> readAcceptedDifferences() throws IOException {
        List<AcceptedDifference> accepted = new ArrayList<AcceptedDifference>();
        for (JsonNode entry : MAPPER.readTree(ACCEPTED_DIFFERENCES.toFile())) {
            accepted.add(new AcceptedDifference(entry.path("doc").asText(), entry.path("message").asText()));
        }
        return accepted;
    }

    private static boolean isAccepted(String doc, String message) {
        for (AcceptedDifference accepted : acceptedDifferences) {
            if (accepted.matches(doc, message)) {
                return true;
            }
        }
        return false;
    }

    // doc "*" matches every doc; a message starting with "*" matches any message that ends with the rest, so
    // "*/date_of_birth: ..." covers /order/customer/date_of_birth and /checkout/passengers/0/date_of_birth.
    static final class AcceptedDifference {
        final String doc;
        final String message;

        AcceptedDifference(String doc, String message) {
            this.doc = doc;
            this.message = message;
        }

        boolean matches(String doc, String message) {
            boolean docMatches = this.doc.equals("*") || this.doc.equals(doc);
            boolean messageMatches = this.message.startsWith("*")
                    ? message.endsWith(this.message.substring(1))
                    : this.message.equals(message);
            return docMatches && messageMatches;
        }
    }

    static final class Check {
        final String sample;
        final String pointer;
        final String doc;
        final String schema;

        Check(String sample, String pointer, String doc, String schema) {
            this.sample = sample;
            this.pointer = pointer;
            this.doc = doc;
            this.schema = schema;
        }

        String id() {
            return sample + (pointer.isEmpty() ? "" : pointer) + " -> " + doc + "#" + schema;
        }

        @Override
        public String toString() {
            return id();
        }
    }
}
