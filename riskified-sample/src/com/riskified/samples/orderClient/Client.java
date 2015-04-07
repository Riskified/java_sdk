package com.riskified.samples.orderClient;
import java.util.Calendar;
import com.riskified.RiskifedError;
import com.riskified.RiskifiedClient;
import com.riskified.models.Address;
import com.riskified.models.ArrayOrders;
import com.riskified.models.AuthorizationError;
import com.riskified.models.AuthorizationErrorType;
import com.riskified.models.CancelOrder;
import com.riskified.models.CheckoutDeniedOrder;
import com.riskified.models.CheckoutOrder;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.Customer;
import com.riskified.models.DecisionDetails;
import com.riskified.models.DecisionOrder;
import com.riskified.models.DecisionType;
import com.riskified.models.DiscountCode;
import com.riskified.models.FulfillmentDetails;
import com.riskified.models.FulfillmentOrder;
import com.riskified.models.LineItem;
import com.riskified.models.Order;
import com.riskified.models.RefundDetails;
import com.riskified.models.RefundOrder;
import com.riskified.models.Response;
import com.riskified.models.Seller;
import com.riskified.models.ShippingLine;
import com.riskified.models.SocialDetails;
import com.riskified.models.TravelLineItem;
import com.riskified.validations.FieldBadFormatException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Client {
    public static void main(String[] arg) throws FieldBadFormatException {

        CheckoutOrder checkoutOrder = generateCheckoutOrder();

        CheckoutDeniedOrder checkoutDeniedOrder = generateCheckoutDeniedOrder();

        Order order = generateOrder();

        Order updateOrder = generateUpdateOrder(order);

        ArrayOrders orders = generateHistoricalOrders(order);

        CancelOrder cancelOrder = generateCancelOrder(order);

        RefundOrder refundOrder = generateRefundOrder(order);

        FulfillmentOrder fulfillmentOrder = generateFulfillmentOrder();

        DecisionOrder decisionOrder = generateDecisionOrder();

        try {
            // Riskified client parameters can be set in the constructor, like this:
            // RiskifiedClient client = new RiskifiedClient("test.pass.com", "ad6b6e6376fb1e3521e44ca28451d58b9605d932", Environment.DEBUG);
            // Or according 'riskified_sdk.properties' configuration file, like this:
            RiskifiedClient client = new RiskifiedClient();

            Response resCheckoutOrder = client.checkoutOrder(checkoutOrder);

            System.out.println("Checkout order response:");
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

            Response resUpdateOrder = client.updateOrder(updateOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Update order response:");
            System.out.println("id: " + resUpdateOrder.getOrder().getId());
            System.out.println("status: " + resUpdateOrder.getOrder().getStatus());
            System.out.println("description: " + resUpdateOrder.getOrder().getDescription());

            Response resCancelOrder = client.cancelOrder(cancelOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Cancel order response:");
            System.out.println("id: " + resCancelOrder.getOrder().getId());
            System.out.println("status: " + resCancelOrder.getOrder().getStatus());
            System.out.println("description: " + resCancelOrder.getOrder().getDescription());

            Response resRefundOrder = client.refundOrder(refundOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Refund order response:");
            System.out.println("id: " + resRefundOrder.getOrder().getId());
            System.out.println("status: " + resRefundOrder.getOrder().getStatus());
            System.out.println("description: " + resRefundOrder.getOrder().getDescription());

            Response resFulfillmentOrder = client.fulfillOrder(fulfillmentOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Fulfillment order response:");
            System.out.println("id: " + resFulfillmentOrder.getOrder().getId());
            System.out.println("status: " + resFulfillmentOrder.getOrder().getStatus());
            System.out.println("description: " + resFulfillmentOrder.getOrder().getDescription());


            Response resDecision = client.decisionOrder(decisionOrder);

            System.out.println("-----------------------------------------");
            System.out.println("decision order response:");
            System.out.println("id: " + resDecision.getOrder().getId());
            System.out.println("status: " + resDecision.getOrder().getStatus());
            System.out.println("description: " + resDecision.getOrder().getDescription());


        } catch (RiskifedError e) {
            e.printStackTrace();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static DecisionOrder generateDecisionOrder() {
        DecisionDetails decision = new DecisionDetails();
        decision.setExternalStatus(DecisionType.chargebackFraud);
        decision.setReason("Fraud + used proxy");
        decision.setDecidedAt(new Date(114, 01, 10, 11, 00, 00));
        DecisionOrder decisionOrder = new DecisionOrder("1235", decision);
        return decisionOrder;
    }

    private static ArrayOrders generateHistoricalOrders(Order order) {
        ArrayOrders orders = new ArrayOrders();
        orders.getOrders().add(order);
        orders.getOrders().add(order);
        return orders;
    }

    private static RefundOrder generateRefundOrder(Order order) {
        RefundOrder refund = new RefundOrder();
        refund.setId(order.getId());
        RefundDetails refundDetail = new RefundDetails();
        refundDetail.setRefundId("refund_001");
        refundDetail.setRefundedAt(new Date(114, 01, 10, 11, 00, 00));
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

    private static FulfillmentOrder generateFulfillmentOrder() {
        List<FulfillmentDetails> fulfillments = new ArrayList<FulfillmentDetails>();
        FulfillmentDetails fulfilmentDetails = new FulfillmentDetails("33", new Date(114, 01, 10, 11, 00, 00), "success");

        fulfilmentDetails.setLineItems(Arrays.asList(
        new LineItem(100, 1, "ACME Widget", 101, "ABCD"),
        new LineItem(200, 4, "ACME Spring", 202, "EFGH")));

        fulfilmentDetails.setTrackingCompany("UPS");
        fulfilmentDetails.setTrackingNumbers("11X63b");

        fulfillments.add(fulfilmentDetails);
        FulfillmentOrder fulfillmentOrder = new FulfillmentOrder("1235", fulfillments);
        return fulfillmentOrder;
    }

    private static CheckoutOrder generateCheckoutOrder() {
        CheckoutOrder order = new CheckoutOrder();

        order.setId("1235");
        order.setName("#1234");
        order.setEmail("great.customer@example.com");
        order.setCreatedAt(new Date(114, 01, 10, 11, 00, 00));
        order.setClosedAt(null);
        order.setCurrency("CAD");
        order.setUpdatedAt(new Date(114, 01, 10, 11, 00, 00));
        order.setGateway("mypaymentprocessor");
        order.setBrowserIp("124.185.86.55");
        order.setTotalPrice(113.23);
        order.setTotalDiscounts(5);
        order.setCartToken("1sdaf23j212");
        order.setAdditionalEmails(Arrays.asList("my@email.com", "second@email.co.uk"));
        order.setNote("Shipped to my hotel.");
        order.setReferringSite("google.com");

        order.setLineItems(Arrays.asList(
        new LineItem(100, 1, "ACME Widget", 101, "ABCD"),
        new LineItem(200, 4, "ACME Spring", 202, "EFGH")));

        order.setDiscountCodes(Arrays.asList(new DiscountCode(19.95, "12")));

        order.setShippingLines(Arrays.asList(new ShippingLine(123, "free")));

        order.setPaymentDetails(new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA"));

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

    private static Order generateOrder() {
        Order order = new Order();
        order.setId("1235");
        order.setName("#1234");
        order.setEmail("great.customer@example.com");
        order.setCreatedAt(new Date(114, 01, 10, 11, 00, 00));
        order.setClosedAt(new Date(114, 01, 10, 11, 00, 00));
        order.setCurrency("CAD");
        order.setUpdatedAt(new Date(114, 01, 10, 11, 00, 00));
        order.setGateway("mypaymentprocessor");
        order.setBrowserIp("124.185.86.55");
        order.setTotalPrice(120.22);
        order.setTotalDiscounts(5);
        order.setCartToken("1sdaf23j212");
        order.setAdditionalEmails(Arrays.asList("my@email.com", "second@email.co.uk"));
        order.setNote("Shipped to my hotel.");
        order.setReferringSite("google.com");

        Customer customer = new Customer("great.customer@example.com", "john", "smith", "999", new Date(114, 01, 10, 11, 00, 00), true, 10);
        SocialDetails social = new SocialDetails("Facebook", "john.smith", "http://www.facebook.com/john.smith");
        social.setEmail("john.smith@facebook.com");
        customer.getSocial().add(social);
        order.setCustomer(customer);

        LineItem lineItem = new LineItem(200, 4, "ACME Spring", 202, "EFGH");
        
        TravelLineItem travelLineItem = new TravelLineItem(340, 1, "Flight from Israel to France", 211, "EGGG");
        travelLineItem.setDepartureAirportCode("LLBG");
        travelLineItem.setDepartureCountryCode("IL");
        travelLineItem.setDepartureCity("Tel Aviv");
        travelLineItem.setDepartureDate(getDate(2014, Calendar.MARCH, 5));
        travelLineItem.setArrivalAirportCode("LBG");
        travelLineItem.setArrivalCountryCode("IL");
        travelLineItem.setArrivalCity("Tel Aviv");
        travelLineItem.setArrivalDate(getDate(2014, Calendar.MARCH, 5));
        
        
        
        Seller seller = new Seller(customer);
        seller.setPriceNegotiated(true);
        seller.setStartingPrice(400);
        order.setLineItems(Arrays.asList(
        new LineItem(100, 1, "ACME Widget", 101, "ABCD"),
        lineItem, travelLineItem));

        order.setDiscountCodes(Arrays.asList(new DiscountCode(19.95, "12")));

        order.setShippingLines(Arrays.asList(new ShippingLine(123, "free")));

        order.setPaymentDetails(new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA"));

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

    private static CheckoutDeniedOrder generateCheckoutDeniedOrder() {

        AuthorizationError authorizationError = new AuthorizationError(AuthorizationErrorType.expiredCard, new Date(114, 01, 10, 11, 00, 00));
        authorizationError.setMessage("expired creadit card.");

        CheckoutDeniedOrder checkoutDeniedOrder = new CheckoutDeniedOrder("1234", authorizationError);

        return checkoutDeniedOrder;
    }
    
    private static Date getDate(int year, int month, int day) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, month);
    	cal.set(Calendar.DAY_OF_MONTH, day);
    	return cal.getTime();
    }
}
