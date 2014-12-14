package main.java.com.riskified;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import main.java.com.riskified.models.ArrayOrders;
import main.java.com.riskified.models.CancelOrder;
import main.java.com.riskified.models.JsonObject;
import main.java.com.riskified.models.Order;
import main.java.com.riskified.models.Refund;
import main.java.com.riskified.models.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;


public class RiskifiedClient {
	
	private final static String baseUrl = "http://localhost:3000" ;

	private String shopUrl;
	private Mac encoder;
		
	public RiskifiedClient(String shopUrl, String authKey) throws InvalidKeyException, NoSuchAlgorithmException{
		this.shopUrl = shopUrl;
		encoder = createSHA256Key(authKey);
	}
	

	
	public Response createOrder(Order order) throws Exception {
		String url = baseUrl + "/api/create";
		return postOrder((JsonObject)order, url);
		}
	
	public Response submitOrder(Order order) throws Exception {
		String url = baseUrl + "/api/submit";
		return postOrder((JsonObject)order, url);	
	}
	
	public Response updateOrder(Order order) throws Exception {
		String url = baseUrl + "/api/update";
		return postOrder((JsonObject)order, url);	
	}
	
	public Response cancelOrder(CancelOrder order) throws Exception {
		String url = baseUrl + "/api/cancel";
		return postOrder((JsonObject)order, url);	
	}
	
	public Response refundOrder(Refund order) throws Exception {
		String url = baseUrl + "/api/refund";
		return postOrder((JsonObject)order, url);	
	}
	
	public Response historicalOrder(ArrayOrders orders) throws Exception {
		String url = baseUrl + "/api/historical";
		return postOrder((JsonObject)orders, url);	
	}

	private Response postOrder(JsonObject order, String url) throws Exception {
		HttpPost request = createPostRequest(url);
		addOrderToRequest(order, request);
		
		HttpResponse response;
		try {
			response = new DefaultHttpClient().execute(request);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 500){
				throw new Exception();
			}
			String responseString = EntityUtils.toString(entity, "UTF-8");
			Gson gson = new Gson();
			Response res = gson.fromJson(responseString, Response.class);
			res.status = response.getStatusLine().getStatusCode();
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;	

	}

	private Response postOrder(ArrayList<Order> order, String url) {
		return null;
	}

	/**
	 * @param authKey
	 * @return 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	private Mac createSHA256Key(String authKey)
			throws NoSuchAlgorithmException, InvalidKeyException {
		Key sk = new SecretKeySpec(authKey.getBytes(), "HmacSHA256");
		Mac mac = Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
		return mac;
	}
	

	
	/**
	 * @param order
	 * @param postRequest
	 * @param hmac
	 * @throws UnsupportedEncodingException
	 */
	private void addOrderToRequest(JsonObject order, HttpPost postRequest) {
		String hmac = createSHA256ForOrder(order);
		postRequest.setHeader("X_RISKIFIED_HMAC_SHA256", hmac);

		StringEntity input;
		try {
			input = new StringEntity(order.toJson());
			input.setContentType("application/json");
			postRequest.setEntity(input);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param url
	 * @param shopUrl
	 * @return
	 */
	private HttpPost createPostRequest(String url) {
		HttpPost postRequest = new HttpPost(url);
		postRequest.setHeader(HttpHeaders.ACCEPT, "application/vnd.riskified.com; version=2");
		postRequest.setHeader("X_RISKIFIED_SHOP_DOMAIN", shopUrl);
		return postRequest;
	}

	/**
	 * @param <T>
	 * @param order
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 */
	private String createSHA256ForOrder(JsonObject order) {
		final byte[] hmac = encoder.doFinal(order.toJson().getBytes());
		return toHexString(hmac);
	}
    
    public static String toHexString(byte[] bytes) {  
        StringBuilder sb = new StringBuilder(bytes.length * 2);  

        Formatter formatter = new Formatter(sb);  
        for (byte b : bytes) {  
            formatter.format("%02x", b);  
        }  
        
        formatter.close();
        
        return sb.toString();  
    } 
}
