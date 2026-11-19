package com.riskified.golden;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riskified.TransStatus;
import com.riskified.models.CryptoWalletType;
import com.riskified.models.DeliveredTo;
import com.riskified.models.KycType;
import com.riskified.models.OrderSource;
import com.riskified.models.ProductType;
import com.riskified.models.TransportMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

/**
 * The value constants and {@link TransStatus} hold exactly the values the API docs list
 * (src/test/resources/schema/post-auth-flow-submit.json). Refreshing the docs with a new value fails this test.
 */
public class DocumentedValuesTest {

    private static JsonNode schemas;

    @BeforeClass
    public static void loadDocs() throws Exception {
        try (InputStream in = DocumentedValuesTest.class.getResourceAsStream("/schema/post-auth-flow-submit.json")) {
            schemas = new ObjectMapper().readTree(in).path("components").path("schemas");
        }
    }

    @Test
    public void orderSource() throws Exception {
        assertEquals(documented("base_order", "source"), constants(OrderSource.class));
    }

    @Test
    public void productType() throws Exception {
        Set<String> documented = new TreeSet<String>();
        for (String lineItem : new String[]{"physical_line_item", "digital_line_item", "events_line_item", "travel_line_item",
                "rides_line_item", "accommodation_line_item", "remittance_line_item", "crypto_line_item", "giftcard_line_item"}) {
            documented.addAll(documented(lineItem, "product_type"));
        }
        assertEquals(documented, constants(ProductType.class));
    }

    @Test
    public void transportMethod() throws Exception {
        Set<String> documented = documented("rides_line_item", "transport_method");
        documented.addAll(documented("travel_line_item", "transport_method"));
        assertEquals(documented, constants(TransportMethod.class));
    }

    @Test
    public void deliveredTo() throws Exception {
        assertEquals(documented("physical_line_item", "delivered_to"), constants(DeliveredTo.class));
    }

    @Test
    public void kycType() throws Exception {
        assertEquals(documented("kyc_details", "kyc_type"), constants(KycType.class));
    }

    @Test
    public void cryptoWalletType() throws Exception {
        assertEquals(documented("crypto_wallet", "type"), constants(CryptoWalletType.class));
    }

    @Test
    public void transStatus() {
        Set<String> names = new TreeSet<String>();
        for (TransStatus status : TransStatus.values()) {
            names.add(status.name());
        }
        assertEquals(documented("authentication_result", "trans_status"), names);
    }

    private static Set<String> documented(String schema, String property) {
        JsonNode values = schemas.path(schema).path("properties").path(property).path("enum");
        if (!values.isArray() || values.size() == 0) {
            throw new IllegalStateException("No enum for " + schema + "." + property + " in the docs");
        }
        Set<String> result = new TreeSet<String>();
        for (JsonNode value : values) {
            result.add(value.asText());
        }
        return result;
    }

    private static Set<String> constants(Class<?> holder) throws IllegalAccessException {
        Set<String> result = new TreeSet<String>();
        for (Field field : holder.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && field.getType() == String.class) {
                result.add((String) field.get(null));
            }
        }
        return result;
    }
}
