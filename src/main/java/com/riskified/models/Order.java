/**
 * 
 */
package main.java.com.riskified.models;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.com.riskified.JSONFormmater;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author omer
 *
 */
public class Order implements JsonObject {
	
	public Order() {
		line_items = new ArrayList<LineItems>();
		additional_emails = new ArrayList<String>();
		discount_codes = new ArrayList<DiscountCodes>();
		shipping_lines = new ArrayList<ShippingLines>();
		note_attributes = new ArrayList<Attributes>();
		tax_lines = new ArrayList<TaxLines>();
	}
	
	public String id;	
	public Date created_at;
	public Date updated_at;
	public String currency;
	public String gateway;
	public Float total_price;
	public String browser_ip;
	public Customer customer;
	public List<LineItems> line_items;
	public String name;
	public List<String> additional_emails;
	public String note;
	public String number;
	public String order_number;
	public String cancel_reason;
	public Date cancelled_at;
	public Date closed_at;
	public String cart_token;
	public String checkout_token;
	public String token;
	public String referring_site;
	public Boolean confirmed;
	public Boolean buyer_accepts_marketing;
	public String financial_status;
	public String fulfillment_status;
	public String landing_site;
	public String landing_site_ref;
	public String location_id;
	public String source;
	public String source_identifier;
	public String source_name;
	public String source_url;
	public Float subtotal_price;
	public Boolean taxes_included;
	public Float total_discounts;
	public Float total_line_items_price;
	public Float total_price_usd;
	public Float total_tax;
	public Float total_weight;
	public String user_id;
	public String processing_method;
	public String checkout_id;
	public String tags;
	public String vendor;
	public Address shipping_address;
	public Address billing_address;
	public PaymentDetails payment_details;
	public ClientDetails client_details;
	public List<DiscountCodes> discount_codes;
	public List<ShippingLines> shipping_lines;
	public List<Attributes> note_attributes;
	public List<TaxLines> tax_lines;
	
	public String toJson() {
		return JSONFormmater.toJson(this); 
	}
	
	
}
