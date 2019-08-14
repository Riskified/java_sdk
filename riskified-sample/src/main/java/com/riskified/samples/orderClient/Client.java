package com.riskified.samples.orderClient;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;

import com.riskified.RiskifiedError;
import com.riskified._type;
import com.riskified.RiskifiedClient;
import com.riskified.models.*;
import com.riskified.validations.FieldBadFormatException;

public class Client {
    public static void main(String[] arg) throws FieldBadFormatException, ParseException {

        CheckoutOrder adviseOrder = generateAdviseOrder();

        CheckoutOrder checkoutOrder = generateCheckoutOrder();

        CheckoutDeniedOrder checkoutDeniedOrder = generateCheckoutDeniedOrder();

        Order order = generateOrder();

        Order updateOrder = generateUpdateOrder(order);

        RefundOrder refundOrder = generateRefundOrder(order);

        CancelOrder cancelOrder = generateCancelOrder(order);

        FulfillmentOrder fulfillmentOrder = generateFulfillmentOrder(order);

        DecisionOrder decisionOrder = generateDecisionOrder(order);

        ArrayOrders orders = generateHistoricalOrders(order);

        try {
            // Riskified client parameters can be set in the constructor, like this:
            // RiskifiedClient client = new RiskifiedClient("<shop_url>", "<auth_token>", Environment.SANDBOX);
            // Or according 'riskified_sdk.properties' configuration file, like this:
            RiskifiedClient client = new RiskifiedClient();
            
         /*   Response resAdviseOrder = client.adviseOrder(adviseOrder);

            System.out.println("Advise order response:");
            System.out.println("id: " + resAdviseOrder.getOrder().getId());
            System.out.println("status: " + resAdviseOrder.getOrder().getStatus());
            System.out.println("score: " + resAdviseOrder.getOrder().getScore());
            System.out.println("auth_type: " + resAdviseOrder.getOrder().getAuthenticationType().getAuthType());

            System.out.println("-----------------------------------------");  */

            
            Response resCheckoutOrder = client.checkoutOrder(checkoutOrder);

            System.out.println("Checkout create order response:");
            System.out.println("id: " + resCheckoutOrder.getOrder().getId());
            System.out.println("status: " + resCheckoutOrder.getOrder().getStatus());
            System.out.println("description: " + resCheckoutOrder.getOrder().getDescription());



            Response resCheckoutDeniedOrder = client.checkoutDeniedOrder(checkoutDeniedOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Checkout denied order response:");
            System.out.println("id: " + resCheckoutDeniedOrder.getOrder().getId());
            System.out.println("status: " + resCheckoutDeniedOrder.getOrder().getStatus());
            System.out.println("description: " + resCheckoutDeniedOrder.getOrder().getDescription());


            Response resCreateOrder = client.createOrder(order);

            System.out.println("-----------------------------------------");
            System.out.println("Create order response:");
            System.out.println("id: " + resCreateOrder.getOrder().getId());
            System.out.println("status: " + resCreateOrder.getOrder().getStatus());
            System.out.println("description: " + resCreateOrder.getOrder().getDescription());


            Response resSubmitOrder = client.submitOrder(order);

            System.out.println("-----------------------------------------");
            System.out.println("Submit order response:");
            System.out.println("id: " + resSubmitOrder.getOrder().getId());
            System.out.println("status: " + resSubmitOrder.getOrder().getStatus());
            System.out.println("description: " + resSubmitOrder.getOrder().getDescription());

            Response resUpdateOrder = client.updateOrder(updateOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Update order response:");
            System.out.println("id: " + resUpdateOrder.getOrder().getId());
            System.out.println("status: " + resUpdateOrder.getOrder().getStatus());
            System.out.println("description: " + resUpdateOrder.getOrder().getDescription());

            Response resRefundOrder = client.refundOrder(refundOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Refund order response:");
            System.out.println("id: " + resRefundOrder.getOrder().getId());
            System.out.println("status: " + resRefundOrder.getOrder().getStatus());
            System.out.println("description: " + resRefundOrder.getOrder().getDescription());

            Response resCancelOrder = client.cancelOrder(cancelOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Cancel order response:");
            System.out.println("id: " + resCancelOrder.getOrder().getId());
            System.out.println("status: " + resCancelOrder.getOrder().getStatus());
            System.out.println("description: " + resCancelOrder.getOrder().getDescription());

            Response resFulfillmentOrder = client.fulfillOrder(fulfillmentOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Fulfillment order response:");
            System.out.println("id: " + resFulfillmentOrder.getOrder().getId());
            System.out.println("status: " + resFulfillmentOrder.getOrder().getStatus());
            System.out.println("description: " + resFulfillmentOrder.getOrder().getDescription());


            Response resDecision = client.decisionOrder(decisionOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Decision order response:");
            System.out.println("id: " + resDecision.getOrder().getId());
            System.out.println("status: " + resDecision.getOrder().getStatus());
            System.out.println("description: " + resDecision.getOrder().getDescription());


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

    private static DecisionOrder generateDecisionOrder(Order order) throws ParseException {
        DecisionDetails decision = new DecisionDetails();
        decision.setExternalStatus(DecisionType.chargebackFraud);
        decision.setReason("Fraud + used proxy");
        decision.setDecidedAt(parseDate("15-12-2016 00:00:00.0"));
        DecisionOrder decisionOrder = new DecisionOrder(order.getId(), decision);
        CreditCardPaymentDetails paymentDetails = new CreditCardPaymentDetails(null, null, null, null, null);
        paymentDetails.setAuthorizationId("251379942");
        decisionOrder.setPaymentDetails(Arrays.asList(paymentDetails));
        return decisionOrder;
    }

    private static ArrayOrders generateHistoricalOrders(Order order) {
        ArrayOrders orders = new ArrayOrders();
        orders.getOrders().add(order);
        orders.getOrders().add(order);
        return orders;
    }

    private static RefundOrder generateRefundOrder(Order order) throws ParseException {
        RefundOrder refund = new RefundOrder();
        refund.setId(order.getId());
        RefundDetails refundDetail = new RefundDetails();
        refundDetail.setRefundId("refund_001");
        refundDetail.setRefundedAt(parseDate("15-12-2016 00:00:00.0"));
        refundDetail.setAmount(33.12);
        refundDetail.setCurrency("USD");
        refundDetail.setReason("Product Missing");
        refund.setRefunds(Arrays.asList(refundDetail));
        return refund;
    }

    private static CancelOrder generateCancelOrder(Order order) {
        CancelOrder cancel = new CancelOrder();
        cancel.setId(order.getId());
        cancel.setCancelReason("test");
        cancel.setCancelledAt(new Date());
        return cancel;
    }

    private static Order generateUpdateOrder(Order order) {
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setEmail("another.email@example.com");

        return updateOrder;
    }

    private static FulfillmentOrder generateFulfillmentOrder(Order order) throws ParseException {
        List<FulfillmentDetails> fulfillments = new ArrayList<FulfillmentDetails>();
        FulfillmentDetails fulfilmentDetails = new FulfillmentDetails("33", parseDate("15-12-2016 00:00:00.0"), "success");

        fulfilmentDetails.setLineItems(Arrays.asList(
        new LineItem(100, 1, "ACME Widget", "101"),
        new LineItem(200, 4, "ACME Spring", "202")));

        fulfilmentDetails.setTrackingCompany("UPS");
        fulfilmentDetails.setTrackingNumbers("11X63b");

        fulfillments.add(fulfilmentDetails);
        FulfillmentOrder fulfillmentOrder = new FulfillmentOrder(order.getId(), fulfillments);
        return fulfillmentOrder;
    }

    private static CheckoutOrder generateCheckoutOrder() throws ParseException {
        CheckoutOrder order = new CheckoutOrder();

        order.setId("221211112");
        order.setName("#1234");
        order.setEmail("great.customer@example.com");
        order.setCreatedAt(parseDate("15-12-2016 00:00:00.0"));
        order.setClosedAt(null);
        order.setCurrency("CAD");
        order.setUpdatedAt(parseDate("15-12-2016 00:00:00.0"));
        order.setGateway("mypaymentprocessor");
        order.setBrowserIp("124.185.86.55");
        order.setTotalPrice(113.23);
        order.setTotalDiscounts(5);
        order.setCartToken("1sdaf23j212");
        order.setAdditionalEmails(Arrays.asList("my@email.com", "second@email.co.uk"));
        order.setNote("Shipped to my hotel.");
        order.setReferringSite("google.com");

        order.setLineItems(Arrays.asList(
        new LineItem(100, 1, "ACME Widget", "101"),
        new LineItem(200, 4, "ACME Spring", "202")));

        order.setDiscountCodes(Arrays.asList(new DiscountCode(19.95, "12")));

        order.setShippingLines(Arrays.asList(new ShippingLine(123, "free")));
        CreditCardPaymentDetails cr = new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA");
        cr.setInstallmentMonths(6);
        cr.setPaymentPlan("at&t");


        order.setPaymentDetails(Arrays.asList(new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA"), cr ));


        Address address = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        address.setCompany("Kansas Computers");
        address.setCountryCode("US");
        address.setName("John Doe");
        address.setAddress2("Apartment 12");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("64155");
        order.setBillingAddress(address);

        address = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        address.setCompany("Kansas Computers");
        address.setCountryCode("US");
        address.setName("John Doe");
        address.setAddress2("Apartment 12");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("64155");
        order.setShippingAddress(address);


        return order;
    }

    private static CheckoutOrder generateAdviseOrder() throws ParseException {
        CheckoutOrder order = new CheckoutOrder();

        order.setId("99992328882");
        order.setName("#123422111");
        order.setEmail("great.customer_2@example.com");
        order.setCreatedAt(parseDate("04-04-2018 00:00:00.0"));
        order.setClosedAt(null);
        order.setCurrency("CAD");
        order.setUpdatedAt(parseDate("04-05-2018 00:00:00.0"));
        order.setBrowserIp("125.185.86.55");
        order.setTotalPrice(123.23);
        order.setTotalDiscounts(4);
        order.setCartToken("1sdaf23j212oodee");
        order.setAdditionalEmails(Arrays.asList("my@email.com", "second@email.co.uk"));
        order.setNote("Shipped to my hotel.");
        order.setReferringSite("google.com");

        order.setLineItems(Arrays.asList(
        new LineItem(100, 1, "ACME Widget", "101"),
        new LineItem(200, 4, "ACME Spring", "202")));

        order.setDiscountCodes(Arrays.asList(new DiscountCode(19.95, "12")));

        order.setShippingLines(Arrays.asList(new ShippingLine(123, "free")));

        CreditCardPaymentDetails creditCardPaymentDetails = new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA");
        creditCardPaymentDetails.setType(_type.credit_card);
        creditCardPaymentDetails.setAcquirerBin("232323");
        creditCardPaymentDetails.setGateway("goGateway");
        creditCardPaymentDetails.setMid("212212121");
        creditCardPaymentDetails.setId("1");

        order.setPaymentDetails(Arrays.asList(creditCardPaymentDetails));
        

        Address address = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        address.setCompany("Kansas Computers");
        address.setCountryCode("US");
        address.setName("John Doe");
        address.setAddress2("Apartment 12");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("64155");
        order.setBillingAddress(address);

        address = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        address.setCompany("Kansas Computers");
        address.setCountryCode("US");
        address.setName("John Doe");
        address.setAddress2("Apartment 12");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("64155");
        order.setShippingAddress(address);


        return order;
    }

    
    private static Order generateOrder() throws ParseException {
        Order order = new Order();
        order.setId("#12000000000345");
        order.setName("#12345");
        order.setEmail("great.customer@example.com");
        order.setCreatedAt(parseDate("15-12-2016 00:00:00.0"));
        order.setClosedAt(parseDate("15-12-2016 00:00:00.0"));
        order.setCurrency("CAD");
        order.setUpdatedAt(parseDate("15-12-2016 00:00:00.0"));
        order.setGateway("mypaymentprocessor");
        order.setBrowserIp("124.185.86.55");
        order.setTotalPrice(120.22);
        order.setTotalDiscounts(5);
        order.setCartToken("1sdaf23j212");
        order.setAdditionalEmails(Arrays.asList("my@email.com", "second@email.co.uk"));
        order.setNote("Shipped to my hotel.");
        order.setReferringSite("google.com");

        Customer customer = new Customer("great.customer@example.com", "john", "smith", "999", parseDate("15-12-2016 00:00:00.0"), true, 10);
        SocialDetails social = new SocialDetails("Facebook", "john.smith", "http://www.facebook.com/john.smith");
        social.setEmail("john.smith@facebook.com");
        customer.getSocial().add(social);
        order.setCustomer(customer);

        LineItem lineItem = new LineItem(200, 4, "ACME Spring", "AAA2");
        lineItem.setColor("black");
        
        TravelLineItem travelLineItem = new TravelLineItem(340, 1, "Flight from Israel to France", "211", "B11", 1, 1);
        travelLineItem.setDeparturePortCode("LLBG");
        travelLineItem.setDepartureCountryCode("IL");
        travelLineItem.setDepartureCity("Tel Aviv");
        travelLineItem.setDepartureDate(parseDate("15-12-2016 00:00:00.0"));
        travelLineItem.setArrivalPortCode("LBG");
        travelLineItem.setArrivalCountryCode("FR");
        travelLineItem.setArrivalCity("Paris");
        travelLineItem.setArrivalDate(parseDate("15-12-2016 00:00:00.0"));
        travelLineItem.setTicketClass("economy");
        travelLineItem.setCarrierCode("AF");
        travelLineItem.setCarrierName("Air France");
        travelLineItem.setRequiresShipping(false);
        
        order.setShippingLines(Arrays.asList(new ShippingLine(123, "free")));
        CreditCardPaymentDetails cr = new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA");
        cr.setInstallmentMonths(6);
        cr.setPaymentPlan("at&t");
        
        
        order.setPaymentDetails(Arrays.asList(new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA"), cr ));

        
        order.setLineItems(Arrays.asList(new LineItem(100, 1, "ACME Widget", "101"), lineItem, travelLineItem));

        Passenger passenger = new Passenger("john","smith");
        passenger.setDateOfBirth(parseDate("15-12-2016 00:00:00.0"));
        passenger.setNationalityCode("IL");
        passenger.setInsuranceType("full");
        passenger.setInsurancePrice(11);
        passenger.setDocumentNumber("123456");
        passenger.setDocumentType("Passport");
        passenger.setDocumentIssueDate(parseDate("15-12-2016 00:00:00.0"));
        passenger.setDocumentExpirationDate(parseDate("15-12-2016 00:00:00.0"));
        passenger.setPassengerType("Adult");
        
        order.setPassengers(Arrays.asList(passenger));
        
        Seller seller = new Seller(customer);
        seller.setPriceNegotiated(true);
        seller.setStartingPrice(400);
        
        
        order.setDiscountCodes(Arrays.asList(new DiscountCode(19.95, "12")));

        order.setShippingLines(Arrays.asList(new ShippingLine(123, "free")));
        


        order.setPaymentDetails(Arrays.asList(new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA") ));

        Address address = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        address.setCompany("Kansas Computers");
        address.setCountryCode("US");
        address.setName("John Doe");
        address.setAddress2("Second street");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("64155");
        order.setBillingAddress(address);

        address = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        address.setCompany("Kansas Computers");
        address.setCountryCode("US");
        address.setName("John Doe");
        address.setAddress2("Apartment 12");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("64155");
        order.setShippingAddress(address);
        
        return order;
    }

    private static CheckoutDeniedOrder generateCheckoutDeniedOrder() throws ParseException {

        AuthorizationError authorizationError = new AuthorizationError("card_declined", parseDate("15-12-2016 00:00:00.0"));
        authorizationError.setMessage("expired credit card.");

        CreditCardPaymentDetails creditCardPaymentDetails = new CreditCardPaymentDetails("666666", "full", "m", "4444", "visa");
        creditCardPaymentDetails.setAuthorizationError(authorizationError);

        CheckoutDeniedOrder checkoutDeniedOrder = new CheckoutDeniedOrder("cd12345");
        checkoutDeniedOrder.setPaymentDetails(Arrays.asList(creditCardPaymentDetails));

        return checkoutDeniedOrder;
    }
    
    private static Date parseDate(String date) throws ParseException {
    	SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"); 
    	
    	return dt.parse(date); 
    }
}
