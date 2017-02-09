package com.riskified;

import com.riskified.models.ArrayOrders;
import com.riskified.models.CancelOrder;
import com.riskified.models.CheckoutOrder;
import com.riskified.models.DecisionDetails;
import com.riskified.models.DecisionOrder;
import com.riskified.models.DecisionType;
import com.riskified.models.Order;
import com.riskified.models.ResOrder;
import com.riskified.models.Response;
import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpResponseException;
import org.junit.BeforeClass;
import org.junit.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class RiskifiedClientTest {
    private RiskifiedClient riskifiedClient;
    private static String shopUrl;
    private static String authKey;
    private static Validation validation;
    private static Environment environment;

    @BeforeClass
    public static void runOnceBeforeAnyTestCaseIsRun() throws IOException {
        Properties properties = new Properties();
        properties.load(RiskifiedClientTest.class.getClassLoader().getResourceAsStream("riskified_sdk.properties"));
        shopUrl = "test.pass.com";
        authKey = "ad6b6e6376fb1e3521e44ca28451d58b9605d932";
        String validationProperty = properties.getProperty("validation");
        validation = null;
        if (StringUtils.isNotEmpty(validationProperty)) {
            validation = Validation.valueOf(validationProperty.toUpperCase());
        }

        String environmentProperty = properties.getProperty("environment");
        if (StringUtils.isNotEmpty(environmentProperty)) {
            environment = Environment.valueOf(environmentProperty.toUpperCase());
        }
    }

    @Test
    public void testRiskifiedClientConstruction_withoutRequestAndConnectionTimeout() throws RiskifiedError {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).build();
        assertNotNull(riskifiedClient);
        assertEquals("Invalid shop url", shopUrl, riskifiedClient.getShopUrl());
        assertEquals("Invalid request timeout ", 10000, riskifiedClient.getRequestTimeout());
        assertEquals("Invalid connection timeout", 5000, riskifiedClient.getConnectionTimeout());
        assertEquals(Validation.ALL, riskifiedClient.getValidation());
    }

    @Test
    public void testRiskifiedClientConstruction_withNonDefaultRequestTimeout_connectionTimeout_validationType() throws RiskifiedError {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setRequestTimeout(7).setConnectionTimeout(11).setValidation(Validation.IGNORE_MISSING).build();
        assertNotNull(riskifiedClient);
        assertEquals("Invalid shop url", shopUrl, riskifiedClient.getShopUrl());
        assertEquals("Invalid request timeout ", 7, riskifiedClient.getRequestTimeout());
        assertEquals("Invalid connection timeout", 11, riskifiedClient.getConnectionTimeout());
        assertEquals(Validation.IGNORE_MISSING, riskifiedClient.getValidation());
    }

    @Test
    public void testCreateOrderWithNoValidation() throws RiskifiedError, IOException, FieldBadFormatException {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setValidation(Validation.NONE).build();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        Response response = riskifiedClient.createOrder(order);
        assertNull(parseResponse(RiskifiedOperation.CREATE, response, order.getId()));
    }

    @Test
    public void testSubmitOrderWithNoValidation() throws RiskifiedError, IOException, FieldBadFormatException {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setValidation(Validation.NONE).build();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        Response response = riskifiedClient.submitOrder(order);
        assertNull(parseResponse(RiskifiedOperation.SUBMIT, response, order.getId()));
    }

    /**
     * Throws an exception because the 'historical' event has been disabled.
     * @throws RiskifiedError
     * @throws IOException
     * @throws FieldBadFormatException
     */
    @Test (expected = HttpResponseException.class)
    public void testHistoricalOrdersWithNoValidation() throws RiskifiedError, IOException, FieldBadFormatException {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setValidation(Validation.NONE).build();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        ArrayOrders orders = new ArrayOrders();
        orders.setOrders(Arrays.asList(order));
        riskifiedClient.historicalOrders(orders);
    }

    @Ignore("need to align with server") @Test
    public void testCancelOrderAfterCreateWithNoValidation() throws RiskifiedError, IOException, FieldBadFormatException {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setValidation(Validation.NONE).build();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        Response response = riskifiedClient.createOrder(order);
        assertNull(parseResponse(RiskifiedOperation.CREATE, response, order.getId()));

        CancelOrder cancelOrder = new CancelOrder();
        cancelOrder.setId(order.getId());
        cancelOrder.setCancelledAt(order.getClosedAt());
        cancelOrder.setCancelReason("Test cancel message.");
        response = riskifiedClient.cancelOrder(cancelOrder);
        assertNull(parseResponse(RiskifiedOperation.CANCEL, response, order.getId()));
    }

    @Ignore("need to align with server") @Test
    public void testUpdateAfterCreateWithNoValidation() throws RiskifiedError, IOException, FieldBadFormatException {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setValidation(Validation.NONE).build();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        Response response = riskifiedClient.createOrder(order);
        assertNull(parseResponse(RiskifiedOperation.CREATE, response, order.getId()));

        order.setUpdatedAt(new Date());
        response = riskifiedClient.updateOrder(order);
        assertNull(parseResponse(RiskifiedOperation.UPDATE, response, order.getId()));
    }

    @Ignore("need to align with server") @Test
    public void testUpdateAfterSubmitWithNoValidation() throws RiskifiedError, IOException, FieldBadFormatException {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setValidation(Validation.NONE).build();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        Response response = riskifiedClient.submitOrder(order);
        assertNull(parseResponse(RiskifiedOperation.SUBMIT, response, order.getId()));

        order.setUpdatedAt(new Date());
        response = riskifiedClient.updateOrder(order);
        assertNull(parseResponse(RiskifiedOperation.UPDATE, response, order.getId()));
    }

    @Ignore("need to align with server") @Test
    public void testDecisionAfterCreateWithNoValidation() throws RiskifiedError, IOException, FieldBadFormatException {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setValidation(Validation.NONE).build();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        Response response = riskifiedClient.createOrder(order);
        assertNull(parseResponse(RiskifiedOperation.CREATE, response, order.getId()));

        DecisionType externalStatus = DecisionType.cancelled;
        Date decidedAt = new Date();
        String reason = "Test decision - Cancelled the test order after create";
        DecisionDetails decisionDetails = new DecisionDetails(externalStatus, decidedAt, reason);
        DecisionOrder decisionOrder = new DecisionOrder(order.getId(), decisionDetails);
        response = riskifiedClient.decisionOrder(decisionOrder);
        assertNull(parseResponse(RiskifiedOperation.DECISION, response, order.getId()));
    }

    @Ignore("need to align with server") @Test
    public void testDecisionAfterSubmitWithNoValidation() throws RiskifiedError, IOException, FieldBadFormatException {
        riskifiedClient = new RiskifiedClient.RiskifiedClientBuilder(shopUrl, authKey, Environment.SANDBOX).setValidation(Validation.NONE).build();
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        Response response = riskifiedClient.submitOrder(order);
        assertNull(parseResponse(RiskifiedOperation.SUBMIT, response, order.getId()));

        DecisionType externalStatus = DecisionType.cancelled;
        Date decidedAt = new Date();
        String reason = "Test decision - Cancelled the test order after submit";
        DecisionDetails decisionDetails = new DecisionDetails(externalStatus, decidedAt, reason);
        DecisionOrder decisionOrder = new DecisionOrder(order.getId(), decisionDetails);
        response = riskifiedClient.decisionOrder(decisionOrder);
        assertNull(parseResponse(RiskifiedOperation.DECISION, response, order.getId()));
    }

    private String parseResponse(RiskifiedOperation riskifiedOperation, Response response, String orderId) {
        String detailsOfWhatWentWrong = null;
        if (response != null) {
            ResOrder order = response.getOrder();
            com.riskified.models.Error error = response.getError();
            if (error != null || order == null) {
                if (riskifiedOperation == RiskifiedOperation.HISTORIC_REPORT) {
                    if (response.getReceived() != 1) {
                        detailsOfWhatWentWrong = String.format("Unable to %s order at Riskified. Received %d orders instead of 1", riskifiedOperation.toString(), response.getReceived());
                    }
                } else {
                    //Something went wrong during submission.
                    String errorMessage = (error != null) ? error.getMessage() : null;
                    String warnings = (response.getWarnings() != null) ? (String.join(",",response.getWarnings())) : null;
                    detailsOfWhatWentWrong = String.format("Unable to %s order at Riskified. Error message: %s, Warnings: %s", riskifiedOperation.toString(), errorMessage, warnings);
                }
            } else {
                boolean isValid = orderId.equals(order.getId());
                if (!isValid) {
                    detailsOfWhatWentWrong = String.format("Unable to %s order at Riskified. status: %s, description: %s.", riskifiedOperation.toString(), order.getStatus(), order.getDescription());
                }
            }
        } else {
            //Could not contact Riskified. Something happened during the request.
            detailsOfWhatWentWrong = String.format("Unable to %s order. Failed during contact.", riskifiedOperation.toString());
        }
        return detailsOfWhatWentWrong;
    }

    public static enum RiskifiedOperation {
        CANCEL,
        SUBMIT,
        HISTORIC_REPORT,
        UPDATE, DECISION, CHECKOUT_ORDER, CREATE
    }


}
