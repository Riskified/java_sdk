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
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Properties;


/**
 * Riskified API Client
 * The client implements the API for Riskified as described in:
 * http://apiref.riskified.com/
 */
public class RiskifiedClient {
    private Validation validation = Validation.ALL;
    private Environment environment = Environment.SANDBOX;
    private String baseUrl;
    private String shopUrl;
    private SHA256Handler sha256Handler;
    private int requestTimeout = 10000;
    private int connectionTimeout = 5000;
    private String authKey;

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
        String environmentType = properties.getProperty("environment");
        String validationType = properties.getProperty("validation");

        if (validationType.equals("NONE")) {
            validation = Validation.NONE;
        } else if (validationType.equals("IGNORE_MISSING")) {
            validation = Validation.IGNORE_MISSING;
        } else if(validationType.equals("ALL")) {
        	validation = Validation.ALL;
        }

        if (environmentType.equals("DEBUG")) {
            environment = Environment.DEBUG;
        } else if (environmentType.equals("PRODUCTION")) {
            environment = Environment.PRODUCTION;
        } else if (environmentType.equals("SANDBOX")) {
        	environment = Environment.SANDBOX;
        }

        if (environment == Environment.DEBUG) {
            String url = properties.getProperty("debugRiskifiedHostUrl", Utils.DEBUG_ENVIRONMENT);
            init(shopUrl, authKey, url, validation);
        } else {
            init(shopUrl, authKey, Utils.getBaseUrlFromEnvironment(environment), validation);
        }
    }

    /**
     * Riskified API client
     * don't use config file
     * @param shopUrl The shop url you use to login to Riskified
     * @param authKey From the advance settings in Riskified web site
     * @param environment The Riskifed environment (SANDBOX / PRODUCTION)
     * @throws RiskifedError When there was a critical error, look at the exception to see more data
     */
    public RiskifiedClient(String shopUrl, String authKey, Environment environment) throws RiskifedError {
        init(shopUrl, authKey, Utils.getBaseUrlFromEnvironment(environment), Validation.ALL);
    }

    /**
     * Riskified API client
     * don't use config file
     * @param shopUrl The shop url you use to login to Riskified
     * @param authKey From the advance settings in Riskified web site
     * @param environment The Riskifed environment (SANDBOX / PRODUCTION)
     * @throws RiskifedError When there was a critical error, look at the exception to see more data
     */
    public RiskifiedClient(String shopUrl, String authKey, Environment environment, Validation validation) throws RiskifedError {
        init(shopUrl, authKey, Utils.getBaseUrlFromEnvironment(environment), validation);
    }

    private void init(String shopUrl, String authKey, String baseUrl, Validation validationType) throws RiskifedError {
        this.baseUrl = baseUrl;
        this.shopUrl = shopUrl;
        this.sha256Handler = new SHA256Handler(authKey);
        this.validation = validationType;
    }

    /**
     * Send a new checkout order to Riskified
     * @param order The checkout order to create (Checkout order has the same fields like Order but ALL fields are optional)
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response checkoutOrder(CheckoutOrder order) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/checkout_create";
        
        // Validation.ALL is not relevant when checkout.
        if(validation != validation.NONE) {
            validate(order, Validation.IGNORE_MISSING);
        }
        
        return postCheckoutOrder(new CheckoutOrderWrapper<CheckoutOrder>(order), url);
    }
    
    /**
     * Send a new checkout order to Riskified
     * @param order The checkout order to create (Checkout order has the same fields like Order but ALL fields are optional)
     * @param validation Determines what type of validation will take place
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response checkoutOrder(CheckoutOrder order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/checkout_create";
        validate(order, validation);
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
    public Response checkoutDeniedOrder(CheckoutDeniedOrder order) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/checkout_denied";
        validate(order);
        return postCheckoutOrder(new CheckoutOrderWrapper<CheckoutDeniedOrder>(order), url);
    }
    
    /**
     * Mark a previously checkout order has been denied.
     * @param order The checkout denied order details, mark as denied and also can specify why it was denied.
     * @param validation Determines what type of validation will take place
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response checkoutDeniedOrder(CheckoutDeniedOrder order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/checkout_denied";
        validate(order, validation);
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
    public Response createOrder(Order order) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/create";
        validate(order);
        return postOrder(new OrderWrapper<Order>(order), url);
    }
    
    /**
     * Send a new order to Riskified
     * Depending on your current plan, the newly created order might not be submitted automatically for review.
     * @param order An order to create
     * @param validation Determines what type of validation will take place
     * Any missing fields (such as BIN number or AVS result code) that are unavailable during the time of the request should be skipped or passed as null
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response createOrder(Order order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/create";
        validate(order, validation);
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
    public Response submitOrder(Order order) throws IOException, FieldBadFormatException {
        return submitOrder(order, validation);
    }

    /**
     * Submit a new or existing order to Riskified for review
     * Forces the order to be submitted for review, regardless of your current plan.
     * @param order An order to submit for review.
     * Any missing fields (such as BIN number or AVS result code) that are unavailable during the time of the request should be skipped or passed as null.
     * @param validation Determines what type of validation will take place
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response submitOrder(Order order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/submit";
        validate(order, validation);
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
    public Response updateOrder(Order order) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/update";
        
        // Validation.ALL is not relevant when updating.
        if(validation != validation.NONE) {
            validate(order, Validation.IGNORE_MISSING);
        }
        
        return postOrder(new OrderWrapper<Order>(order), url);
    }
    
    /**
     * Update details of an existing order.
     * Orders are differentiated by their id field. To update an existing order, include its id and any up-to-date data.
     * @param order A (possibly incomplete) order to update
     * The order must have an id field referencing an existing order and at least one additional field to update.
     * @param validation Determines what type of validation will take place
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response updateOrder(Order order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/update";
        validate(order, validation);
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
    public Response cancelOrder(CancelOrder order) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/cancel";
        validate(order);
        return postOrder(new OrderWrapper<CancelOrder>(order), url);
    }
    
    /**
     * Mark a previously submitted order as cancelled.
     * If the order has not yet been reviewed, it is excluded from future review.
     * If the order has already been reviewed and approved, canceling it will also trigger a full refund on any associated charges.
     * An order can only be cancelled during a relatively short time window after its creation.
     * @param order The order to cancel
     * @param validation Determines what type of validation will take place
     * @see CancelOrder
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response cancelOrder(CancelOrder order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/cancel";
        validate(order, validation);
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
    public Response refundOrder(RefundOrder order) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/refund";
        validate(order);
        return postOrder(new OrderWrapper<RefundOrder>(order), url);
    }
    
    /**
     * Issue a partial refund for an existing order.
     * Any associated charges will be updated to reflect the new order total amount.
     * @param order The refund Order
     * @param validation Determines what type of validation will take place
     * @see RefundOrder
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response refundOrder(RefundOrder order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/refund";
        validate(order, validation);
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
    public Response fulfillOrder(FulfillmentOrder order) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/fulfill";
        validate(order);
        return postOrder(new OrderWrapper<FulfillmentOrder>(order), url);
    }
    
    /**
     * Mark a previously submitted order that is was fulfilled.
     * @param order The fulfillment order details
     * @param validation Determines what type of validation will take place
     * @see FulfillmentOrder
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response fulfillOrder(FulfillmentOrder order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/fulfill";
        validate(order, validation);
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
    public Response decisionOrder(DecisionOrder order) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/decision";
        validate(order);
        return postOrder(new OrderWrapper<DecisionOrder>(order), url);
    }
    
    /**
     * Set the decision made for order that was not submitted.
     * @param order The decision order details
     * @param validation Determines what type of validation will take place
     * @see DecisionOrder
     * @see Response
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response decisionOrder(DecisionOrder order, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/decision";
        validate(order, validation);
        return postOrder(new OrderWrapper<DecisionOrder>(order), url);
    }

    /**
     * Send an array (batch) of existing/historical orders to Riskified.
     * Use the decision field to provide information regarding each order status.
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
    public Response historicalOrders(ArrayOrders orders) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/historical";
        validate(orders);
        return postOrder(orders, url);
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
     * @param validation Determines what type of validation will take place
     * @return Response object, including the status from Riskified server
     * @throws ClientProtocolException in case of a problem or the connection was aborted
     * @throws IOException in case of an http protocol error
     * @throws HttpResponseException The server respond status wasn't 200
     * @throws FieldBadFormatException
     */
    public Response historicalOrders(ArrayOrders orders, Validation validation) throws IOException, FieldBadFormatException {
        String url = baseUrl + "/api/historical";
        validate(orders, validation);
        return postOrder(orders, url);
    }

    private Response postCheckoutOrder(Object data, String url) throws IOException, FieldBadFormatException {
        HttpPost request = createPostRequest(url);
        addDataToRequest(data, request);
        HttpResponse response;
        HttpClient client = constructHttpClient();
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

    private HttpClient constructHttpClient() {
        RequestConfig.Builder requestBuilder = RequestConfig.custom().setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(requestTimeout);
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());
        return builder.build();
    }

    private Response postOrder(Object data, String url) throws IOException {
        HttpPost request = createPostRequest(url);
        addDataToRequest(data, request);
        HttpResponse response;
        HttpClient client = constructHttpClient();
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

    private void addDataToRequest(Object data, HttpPost postRequest) throws IllegalStateException, UnsupportedEncodingException {
        String jsonData = JSONFormmater.toJson(data);
        
    	String hmac = sha256Handler.createSHA256(jsonData);
        postRequest.setHeader("X_RISKIFIED_HMAC_SHA256", hmac);
		
        StringEntity input;
        input = new StringEntity(jsonData, Charset.forName("UTF-8"));
		input.setContentType("application/json");
		postRequest.setEntity(input);
    }

    private HttpPost createPostRequest(String url) {
        HttpPost postRequest = new HttpPost(url);
        postRequest.setHeader(HttpHeaders.ACCEPT, "application/vnd.riskified.com; version=2");
        postRequest.setHeader("X_RISKIFIED_SHOP_DOMAIN", shopUrl);
        
        return postRequest;
    }

    private void validate(IValidated objToValidated) throws FieldBadFormatException {
        if (this.validation != Validation.NONE) {
            objToValidated.validate(this.validation);
        }
    }

    private void validate(IValidated objToValidated, Validation validationType) throws FieldBadFormatException {
        if (validationType != Validation.NONE) {
            objToValidated.validate(validationType);
        }
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getAuthKey() {
        return authKey;
    }

    /**
     * Change the Riskified server url
     * You shouldn't use this regular
     * @param url the new server url
     */
    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    public static class RiskifiedClientBuilder {
        private String shopUrl;
        private String authKey;
        private Environment environment;
        private Integer requestTimeout;
        private Integer connectionTimeout;
        private Validation validation;

        /**
         * Required arguments to build a RiskifiedClient
         * @param shopUrl
         * @param authKey
         * @param environment
         */
        public RiskifiedClientBuilder(String shopUrl, String authKey, Environment environment) {
            this.shopUrl = shopUrl;
            this.authKey = authKey;
            this.environment = environment;
        }

        public RiskifiedClientBuilder setRequestTimeout(Integer requestTimeout) {
            this.requestTimeout = requestTimeout;
            return this;
        }

        public RiskifiedClientBuilder setConnectionTimeout(Integer connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public RiskifiedClientBuilder setValidation(Validation validation) {
            this.validation = validation;
            return this;
        }

        public RiskifiedClient build() throws RiskifedError {
            return new RiskifiedClient(this);
        }
    }

    public RiskifiedClient(RiskifiedClientBuilder riskifiedClientBuilder) throws RiskifedError {
        this.shopUrl = riskifiedClientBuilder.shopUrl;
        this.authKey = riskifiedClientBuilder.authKey;
        this.environment = riskifiedClientBuilder.environment;

        if (riskifiedClientBuilder.validation != null) {
            this.validation = riskifiedClientBuilder.validation;
        }

        if (riskifiedClientBuilder.requestTimeout != null) {
            this.requestTimeout = riskifiedClientBuilder.requestTimeout;
        }

        if (riskifiedClientBuilder.connectionTimeout != null) {
            this.connectionTimeout = riskifiedClientBuilder.connectionTimeout;
        }

        this.sha256Handler = new SHA256Handler(authKey);
        this.baseUrl = Utils.getBaseUrlFromEnvironment(environment);
    }
}
