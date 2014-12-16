package main.java.com.riskified;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import main.java.com.riskified.models.ArrayOrders;
import main.java.com.riskified.models.CancelOrder;
import main.java.com.riskified.models.Order;
import main.java.com.riskified.models.OrderWrapper;
import main.java.com.riskified.models.RefundOrder;
import main.java.com.riskified.models.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;


/**
 * Riskified API Client
 * The client implements the API for Riskified as described in:
 * http://apiref.riskified.com/
 */
public class RiskifiedClient {

  private static final String baseUrl = "http://sandbox.riskifeid.com";

  private String shopUrl;
  private Mac encoder;

  /**
   * @param shopUrl The shop url you use to login to Riskified
   * @param authKey From the advance settings in Riskified web site
   * @throws InvalidKeyException If the given authKey is inappropriate for initializing this MAC
   * @throws NoSuchAlgorithmException There is a problem with
   * @throws RiskifedError 
   */
  public RiskifiedClient(String shopUrl, String authKey) throws InvalidKeyException,
      NoSuchAlgorithmException, RiskifedError {
    this.shopUrl = shopUrl;
    encoder = createSHA256Key(authKey);
  }

  /**
   * Send a new order to Riskified
   * Depending on your current plan, the newly created order might not be submitted automatically for review.
   * @param order An order to create 
   * Any missing fields (such as BIN number or AVS result code) that are unavailable during the time of the request should be skipped or passed as null
   * @return 
   * @throws Exception
   */
  public Response createOrder(Order order) throws Exception {
    String url = baseUrl + "/api/create";
    return postOrder(new OrderWrapper<Order>(order), url);
  }

  /**
   * Submit a new or existing order to Riskified for review
   * Forces the order to be submitted for review, regardless of your current plan.
   * @param order An order to submit for review.
   * Any missing fields (such as BIN number or AVS result code) that are unavailable during the time of the request should be skipped or passed as null.
   * @return
   * @throws Exception
   */
  public Response submitOrder(Order order) throws Exception {
    String url = baseUrl + "/api/submit";
    return postOrder(new OrderWrapper<Order>(order), url);
  }

  /**
   * Update details of an existing order.
   * Orders are differentiated by their id field. To update an existing order, include its id and any up-to-date data.
   * @param order A (possibly incomplete) order to update
   * The order must have an id field referencing an existing order and at least one additional field to update.
   * @return
   * @throws Exception
   */
  public Response updateOrder(Order order) throws Exception {
    String url = baseUrl + "/api/update";
    return postOrder(new OrderWrapper<Order>(order), url);
  }

  /**
   * Mark a previously submitted order as cancelled.
   * If the order has not yet been reviewed, it is excluded from future review.
   * If the order has already been reviewed and approved, cancelling it will also trigger a full refund on any associated charges.
   * An order can only be cancelled during a relatively short time window after its creation.
   * @param order The order to cancel
   * @see CancelOrder
   * @return
   * @throws Exception
   */
  public Response cancelOrder(CancelOrder order) throws Exception {
    String url = baseUrl + "/api/cancel";
    return postOrder(new OrderWrapper<CancelOrder>(order), url);
  }

  /**
   * Issue a partial refund for an existing order.
   * Any associated charges will be updated to reflect the new order total amount.
   * @param order The refund Order
   * @see Refund
   * @return
   * @throws Exception
   */
  public Response refundOrder(RefundOrder order) throws Exception {
    String url = baseUrl + "/api/refund";
    return postOrder(new OrderWrapper<RefundOrder>(order), url);
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
   * @param orders - A list of historical orders to send
   * @return
   * @throws Exception
   */
  public Response historicalOrder(ArrayOrders orders) throws Exception {
    String url = baseUrl + "/api/historical";
    return postOrder(orders, url);
  }

  private Response postOrder(Object data, String url) throws ClientProtocolException, IOException, HttpResponseException {
    HttpPost request = createPostRequest(url);
    addDataToRequest(data, request);
    HttpResponse response;
    HttpClient client = HttpClientBuilder.create().build();
    response = client.execute(request);
    String postBody = EntityUtils.toString(response.getEntity(), "UTF-8");
    int status = response.getStatusLine().getStatusCode();
    switch (status) {
      case 200: 
        return getResponseObject(postBody);
      case 400:
        throw new HttpResponseException(500, postBody);
      case 401:
        throw new HttpResponseException(500, postBody);
      case 404:
        throw new HttpResponseException(500, postBody);
      default:
        throw new HttpResponseException(500, "connect Riskified support");
    }
  }

  /**
   * @param postBody
   * @return
   * @throws IOException
   */
  private Response getResponseObject(String postBody) throws IOException {
    Gson gson = new Gson();
    Response res = gson.fromJson(postBody, Response.class);
    return res;
  }


  private Mac createSHA256Key(String authKey) throws InvalidKeyException, RiskifedError {
    Key sk = new SecretKeySpec(authKey.getBytes(), "HmacSHA256");
    Mac mac;
    try {
      mac = Mac.getInstance(sk.getAlgorithm());
    } catch (NoSuchAlgorithmException e) {
      throw new RiskifedError(e);
    }
    mac.init(sk);
    return mac;
  }


  private void addDataToRequest(Object data, HttpPost postRequest) {
    String jsonData = JSONFormmater.toJson(data);
    String hmac = createSHA256ForOrder(jsonData);
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


  private String createSHA256ForOrder(String data) {
    final byte[] hmac = encoder.doFinal(data.getBytes());
    return toHexString(hmac);
  }

  private static String toHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);

    Formatter formatter = new Formatter(sb);
    for (byte b : bytes) {
      formatter.format("%02x", b);
    }

    formatter.close();

    return sb.toString();
  }
}
