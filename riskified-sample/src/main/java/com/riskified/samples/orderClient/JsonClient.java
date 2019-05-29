package com.riskified.samples.orderClient;

import java.io.*;
import java.text.*;
import java.util.*;

import com.riskified.JSONFormater;
import com.riskified.models.*;

public class JsonClient {
	
	public static void main(String[] args) throws ParseException, FileNotFoundException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		String orderId = "11313414";
		
		Order order = new Order();
        order.setId(orderId);
        order.setEmail("test@gmail.com");
        order.setCreatedAt(dateFormat.parse("15/12/2014 12:20:00"));
        order.setCurrency("EUR");
        order.setGateway("mypaymentprocessor");
        order.setBrowserIp("124.185.86.55");
        order.setTotalPrice(950);
        order.setCartToken("1sdaf23j212");
        order.setCustomer(
        		new Customer("great.customer@example.com", "john", "smith", "999", 
        		dateFormat.parse("1/1/2014 12:20:00"), true, 10));

        ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
        LineItem lineItem = new LineItem(200, 4, "Cool shirt", "AAA2");
        lineItem.setCategory("Shirts - Men");
        lineItems.add(lineItem);       
        order.setLineItems(lineItems);
       
        order.setShippingLines(Arrays.asList(new ShippingLine(25, "express")));
        order.setPaymentDetails(Arrays.asList(new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA")));

        Address billingAddress = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        billingAddress.setCompany("Kansas Computers");
        billingAddress.setCountryCode("US");
        billingAddress.setProvinceCode("NY");
        billingAddress.setZip("64155");
        order.setBillingAddress(billingAddress);

        Address shippingAddress = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        shippingAddress.setCompany("Kansas Computers");
        shippingAddress.setCountryCode("US");
        shippingAddress.setProvinceCode("NY");
        shippingAddress.setZip("64155");
        order.setShippingAddress(shippingAddress);
        
        String orderJson = JSONFormater.toJson(order);
        
        // Save the order json to a file
        PrintWriter fileWriter = new PrintWriter(orderId + ".json");
        fileWriter.println(orderJson);
        fileWriter.close();
	}
}
