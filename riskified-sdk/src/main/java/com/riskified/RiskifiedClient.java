package com.riskified;

import com.google.gson.Gson;
import com.riskified.models.ArrayOrders;
import com.riskified.models.CancelOrder;
import com.riskified.models.CheckoutDeniedOrder;
import com.riskified.models.CheckoutOrder;
import com.riskified.models.CheckoutOrderWrapper;
import com.riskified.models.CheckoutResponse;
import com.riskified.models.DecisionOrder;
import com.riskified.models.FulfillmentOrder;
import com.riskified.models.Order;
import com.riskified.models.OrderWrapper;
import com.riskified.models.RefundOrder;
import com.riskified.models.Response;
import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validation;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


/**
 * Riskified API Client
 * The client implements the API for Riskified as described in:
 * http://apiref.riskified.com/
 */
public class RiskifiedClient {
    private Validation validationType;
    private Environment environmentType;
    private String baseUrl;
    private String shopUrl;
    private SHA256Handler sha256Handler;

    /**
     * Riskified API client
     * use configuration file: "src/main/resources/riskified_sdk.properties"
     * uses the keys: shopUrl, authKey, environment, debugRiskifiedHostUrl
     * see full doc on GitHub
     * @throws RiskifedError When there was a critical error, look at the exception to see more data
     */
    public RiskifiedClient() throws RiskifedError {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("riskified_sdk.properties"));
        } catch (IOException e) {
            throw new RiskifedError("There was an error reading the config file in: src/main/resources/riskified_sdk.properties");
        }

        String shopUrl = properties.getProperty("shopUrl");
        String authKey = properties.getProperty("authKey");
        String environment = properties.getProperty("environment");
        String validation = properties.getProperty("validation");


        if (validation.equals("none")) {
            validationType = Validation.none;
        } else if (validation.equals("ignoreMissing")) {
            validationType = Validation.ignoreMissing;
        } else {
            validationType = Validation.all;
        }

        if (environment.equals("debug")) {
            environmentType = Environment.debug;
        } else if (environment.equals("production")) {
            environmentType = Environment.production;
        } else {
            environmentType = Environment.sandbox;
        }

        if (environmentType == Environment.debug) {
            String url = properties.getProperty("debugRiskifiedHostUrl");
            if (url == null || url.isEmpty()) {
                init(shopUrl, authKey, "http://localhost:3000", validationType);
            } else {
                init(shopUrl, authKey, url, validationType);
            }
        } else {
            init(shopUrl, authKey, getBaseUrlFromEnvironment(environmentType), validationType);
        }
    }

    /**
     * Riskified API client
     * don't use config file
     * @param shopUrl The shop url you use to login to Riskified
     * @param authKey From the advance settings in Riskified web site
     * @param environment The Riskifed environment (sandbox / production)
     * @throws RiskifedError When there was a critical error, look at the exception to see more data
     */
    public RiskifiedClient(String shopUrl, String authKey, Environment environmentType) throws RiskifedError {
        init(shopUrl, authKey, getBaseUrlFromEnvironment(environmentType), Validation.all);
    }

    /**
     * Riskified API client
     * don't use config file
     * @param shopUrl The shop url you use to login to Riskified
     * @param authKey From the advance settings in Riskified web site
     * @param environment The Riskifed environment (sandbox / production)
     * @throws RiskifedError When there was a critical error, look at the exception to see more data
     */
    public RiskifiedClient(String shopUrl, String authKey, Environment environmentType, Validation validationType) throws RiskifedError {
        init(shopUrl, authKey, getBaseUrlFromEnvironment(environmentType), validationType);
    }

    private static String getBaseUrlFromEnvironment(Environment environmentType) {
        if (environmentType == Environment.sandbox) {
            return "https://sandbox.riskified.com";
        }
        if (environmentType == Environment.production) {
            return "https://wh.riskified.com";
        }
        return "http://localhost:3000";

    }


    /**
     * Change the Riskified server url
     * You shouldn't use this regular
     * @param url the new server url
     */
    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }

    public Validation getValidationType() {
        return validationType;
    }

    public void setValidationType(Validation validationType) {
        this.validationType = validationType;
    }

    private void init(String shopUrl, String authKey, String baseUrl, Validation validationType) throws RiskifedError {
        this.baseUrl = baseUrl;
        this.shopUrl = shopUrl;
        this.sha256Handler = new SHA256Handler(authKey);
        this.validationType = validationType;
    }


    /**
     * Send a new checkout order to Riskified
     * @param order The checkout order to create (Checkout order has the same fields like Order but all fields are optional)
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response checkoutOrder(CheckoutOrder order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/checkout_create";
        validate(order, Validation.ignoreMissing);
        return postCheckoutOrder(new CheckoutOrderWrapper<CheckoutOrder>(order), url);
    }

    /**
     * Mark a previously checkout order has been denied.
     * @param order The checkout denied order details, mark as denied and also can specify why it was denied.
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response checkoutDeniedOrder(CheckoutDeniedOrder order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/checkout_denied";
        validate(order);
        return postCheckoutOrder(new CheckoutOrderWrapper<CheckoutDeniedOrder>(order), url);
    }

    /**
     * Send a new order to Riskified
     * Depending on your current plan, the newly created order might not be submitted automatically for review.
     * @param order An order to create
     * Any missing fields (such as BIN number or AVS result code) that are unavailable during the time of the request should be skipped or passed as null
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response createOrder(Order order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/create";
        validate(order);
        return postOrder(new OrderWrapper<Order>(order), url);
    }

    /**
     * Submit a new or existing order to Riskified for review
     * Forces the order to be submitted for review, regardless of your current plan.
     * @param order An order to submit for review.
     * Any missing fields (such as BIN number or AVS result code) that are unavailable during the time of the request should be skipped or passed as null.
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response submitOrder(Order order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {

        return submitOrder(order, false);
    }

    /**
     * Submit a new or existing order to Riskified for review
     * Forces the order to be submitted for review, regardless of your current plan.
     * @param order An order to submit for review.
     * Any missing fields (such as BIN number or AVS result code) that are unavailable during the time of the request should be skipped or passed as null.
     * @param shouldValidateLikeCreation Mark if should validate the model of Order before submitting (should be true if the order is new and going to be created).
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response submitOrder(Order order, boolean shouldValidateLikeCreation) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/submit";
        if (shouldValidateLikeCreation) {
            validate(order);
        } else {
            validate(order, Validation.ignoreMissing);
        }
        return postOrder(new OrderWrapper<Order>(order), url);
    }

    /**
     * Update details of an existing order.
     * Orders are differentiated by their id field. To update an existing order, include its id and any up-to-date data.
     * @param order A (possibly incomplete) order to update
     * The order must have an id field referencing an existing order and at least one additional field to update.
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response updateOrder(Order order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/update";
        validate(order, Validation.ignoreMissing);
        return postOrder(new OrderWrapper<Order>(order), url);
    }

    /**
     * Mark a previously submitted order as cancelled.
     * If the order has not yet been reviewed, it is excluded from future review.
     * If the order has already been reviewed and approved, canceling it will also trigger a full refund on any associated charges.
     * An order can only be cancelled during a relatively short time window after its creation.
     * @param order The order to cancel
     * @see CancelOrder
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response cancelOrder(CancelOrder order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/cancel";
        validate(order);
        return postOrder(new OrderWrapper<CancelOrder>(order), url);
    }

    /**
     * Issue a partial refund for an existing order.
     * Any associated charges will be updated to reflect the new order total amount.
     * @param order The refund Order
     * @see RefundOrder
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response refundOrder(RefundOrder order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/refund";
        validate(order);
        return postOrder(new OrderWrapper<RefundOrder>(order), url);
    }

    /**
     * Mark a previously submitted order that is was fulfilled.
     * @param order The fulfillment order details
     * @see FulfillmentOrder
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response fulfillOrder(FulfillmentOrder order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/fulfill";
        validate(order);
        return postOrder(new OrderWrapper<FulfillmentOrder>(order), url);
    }

    /**
     * Set the decision made for order that was not submitted.
     * @param order The decision order details
     * @see DecisionOrder
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response decisionOrder(DecisionOrder order) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/decision";
        validate(order);
        return postOrder(new OrderWrapper<DecisionOrder>(order), url);
    }

    /**
     * Send an array (batch) of existing/historical orders to Riskified.
     * Use the financial_status field to provide information regarding each order status:
     * * 'approved' - approved orders
     * * 'declined-fraud' - declined orders (refunded or voided) as suspected fraud
     * * 'declined' - declined orders (refunded or voided) without connection to fraud
     * * 'chargeback' - orders that received a chargeback
     *
     * Orders sent will be used to build analysis models to better analyze newly received orders.
     *
     * @param orders A list of historical orders to send
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response historicalOrders(ArrayOrders orders) throws ClientProtocolException, IOException, HttpResponseException, FieldBadFormatException {
        String url = baseUrl + "/api/historical";
        validate(orders, Validation.ignoreMissing);
        return postOrder(orders, url);
    }

    private Response postCheckoutOrder(Object data, String url) throws ClientProtocolException, IOException, FieldBadFormatException {

        HttpPost request = createPostRequest(url);
        addDataToRequest(data, request);
        HttpResponse response;
        HttpClient client = HttpClientBuilder.create().build();
        response = client.execute(request);
        String postBody = EntityUtils.toString(response.getEntity(), "UTF-8");
        int status = response.getStatusLine().getStatusCode();

        Response responseObject = getCheckoutResponseObject(postBody);

        switch (status) {
            case 200:
                return responseObject;
            case 400:
                throw new HttpResponseException(500, responseObject.getError().getMessage());
            case 401:
                throw new HttpResponseException(500, responseObject.getError().getMessage());
            case 404:
                throw new HttpResponseException(500, responseObject.getError().getMessage());
            default:
                throw new HttpResponseException(500, "Contact Riskified support");
        }

    }

    private Response postOrder(Object data, String url) throws ClientProtocolException, IOException {
        HttpPost request = createPostRequest(url);
        addDataToRequest(data, request);
        HttpResponse response;
        HttpClient client = HttpClientBuilder.create().build();
        response = client.execute(request);
        String postBody = EntityUtils.toString(response.getEntity(), "UTF-8");
        int status = response.getStatusLine().getStatusCode();
        Response responseObject = getResponseObject(postBody);
        switch (status) {
            case 200:
                return responseObject;
            case 400:
                throw new HttpResponseException(500, responseObject.getError().getMessage());
            case 401:
                throw new HttpResponseException(500, responseObject.getError().getMessage());
            case 404:
                throw new HttpResponseException(500, responseObject.getError().getMessage());
            default:
                throw new HttpResponseException(500, "Contact Riskified support");
        }
    }

    private Response getResponseObject(String postBody) throws IOException {
        Gson gson = new Gson();
        Response res = gson.fromJson(postBody, Response.class);
        return res;
    }

    private CheckoutResponse getCheckoutResponseObject(String postBody) throws IOException {
        Gson gson = new Gson();
        CheckoutResponse res = gson.fromJson(postBody, CheckoutResponse.class);
        res.setOrder(res.getCheckout());
        return res;
    }


    private void addDataToRequest(Object data, HttpPost postRequest) {
        String jsonData = JSONFormmater.toJson(data);
        String hmac = sha256Handler.createSHA256(jsonData);
        postRequest.setHeader("X_RISKIFIED_HMAC_SHA256", hmac);

        StringEntity input;
        try {
            input = new StringEntity(jsonData);
            input.setContentType("application/json");
            postRequest.setEntity(input);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private HttpPost createPostRequest(String url) {
        HttpPost postRequest = new HttpPost(url);
        postRequest.setHeader(HttpHeaders.ACCEPT, "application/vnd.riskified.com; version=2");
        postRequest.setHeader("X_RISKIFIED_SHOP_DOMAIN", shopUrl);
        return postRequest;
    }

    private void validate(IValidated objToValidated) throws FieldBadFormatException {
        if (this.validationType != Validation.none) {
            objToValidated.validate(this.validationType);
        }
    }

    private void validate(IValidated objToValidated, Validation validationType) throws FieldBadFormatException {
        if (validationType != Validation.none) {
            objToValidated.validate(validationType);
        }
    }


}
