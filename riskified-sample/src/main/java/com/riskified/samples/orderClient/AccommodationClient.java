package com.riskified.samples.orderClient;
import java.io.IOException;
import java.util.*;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;

import com.riskified.RiskifiedError;
import com.riskified.RiskifiedClient;
import com.riskified.models.*;
import com.riskified.validations.FieldBadFormatException;
import com.riskified.Environment;

public class AccommodationClient {
    public static void main(String[] arg) throws FieldBadFormatException {

        Order order = generateOrder();

        try {
            // Riskified client parameters can be set in the constructor, like this:
        	 RiskifiedClient client = new RiskifiedClient("<shop_url>", "<auth_token>", Environment.SANDBOX);
            // Or according 'riskified_sdk.properties' configuration file, like this:

            Response resCreateOrder = client.createOrder(order);

            System.out.println("-----------------------------------------");
            System.out.println("Create order response:");
            System.out.println("id: " + resCreateOrder.getOrder().getId());
            System.out.println("status: " + resCreateOrder.getOrder().getStatus());
            System.out.println("description: " + resCreateOrder.getOrder().getDescription());

        } catch (RiskifiedError e) {
        	printError(e);
        } catch (HttpResponseException e) {
        	printError(e);
        } catch (ClientProtocolException e) {
        	printError(e);
        } catch (IOException e) {
        	printError(e);
        }
    }

    private static void printError(Exception e) {
    	System.out.println("[Sample failed]");
        e.printStackTrace();
    }

    private static Order generateOrder() {
        Order order = new Order();
        order.setId("123456");
        order.setName("#1234");
        order.setEmail("sample.sampleson@sample.com");
        order.setCreatedAt(new Date(114, 01, 10, 11, 00, 00));
        order.setClosedAt(new Date(114, 01, 10, 11, 00, 00));
        order.setCurrency("USD");
        order.setUpdatedAt(new Date(114, 01, 10, 11, 00, 00));
        order.setGateway("mypaymentprocessor");
        order.setBrowserIp("124.185.86.55");
        order.setTotalPrice(180);
        order.setTotalDiscounts(20);
        order.setCartToken("1sdaf23j212");
        order.setAdditionalEmails(Arrays.asList("my@email.com", "second@email.co.uk"));
        order.setNote("Shipped to my hotel.");
        order.setReferringSite("google.com");

        AccommodationLineItem accommodationLineItem = new AccommodationLineItem(300, "Holiday Inn", "hotel", "New York City", "US", new Date(114, 01, 10, 11, 00, 00), new Date(114, 01, 10, 11, 00, 00));
        accommodationLineItem.setRoomType("Presidential Suite");
        accommodationLineItem.setRating(3.9f);
        accommodationLineItem.setNumberOfGuests(2);
        accommodationLineItem.setCancellationPolicy("No");
        accommodationLineItem.setAccommodationType("Hotel");

        order.setLineItems(Arrays.<LineItem>asList(accommodationLineItem));

        order.setDiscountCodes(Arrays.asList(new DiscountCode(20, "10")));

        order.setPaymentDetails(Arrays.asList(new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA")));

        Address address = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        address.setCompany("Kansas Computers");
        address.setCountryCode("US");
        address.setName("John Doe");
        address.setAddress2("Second street");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("64155");
        order.setBillingAddress(address);

        return order;
    }

    private static Date getDate(int year, int month, int day) {
    	return getDate(year, month, day, 0, 0, 0);
    }

    private static Date getDate(int year, int month, int day, int hour, int minute, int second) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, month);
    	cal.set(Calendar.DAY_OF_MONTH, day);
    	cal.set(Calendar.HOUR_OF_DAY, hour);
    	cal.set(Calendar.MINUTE, minute);
    	cal.set(Calendar.SECOND, second);
    	return cal.getTime();
    }
}
