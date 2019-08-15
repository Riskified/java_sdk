package com.riskified.samples.orderClient;
import java.io.IOException;
import java.util.*;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;

import com.riskified.RiskifiedError;
import com.riskified.RiskifiedClient;
import com.riskified.models.*;
import com.riskified.validations.FieldBadFormatException;

public class SimpleClient {
    public static void main(String[] arg) throws FieldBadFormatException {

        Order order = generateOrder();

        try {
            // Riskified client parameters can be set in the constructor, like this:
            // RiskifiedClient client = new RiskifiedClient("<shop_url>", "<auth_token>", Environment.SANDBOX);
            // Or according 'riskified_sdk.properties' configuration file, like this:
            RiskifiedClient client = new RiskifiedClient();

            Response resCreateOrder = client.createOrder(order);

            System.out.println("-----------------------------------------");
            System.out.println("Create order response:");
            System.out.println("id: " + resCreateOrder.getOrder().getId());
            System.out.println("status: " + resCreateOrder.getOrder().getStatus());
            System.out.println("description: " + resCreateOrder.getOrder().getDescription());
            System.out.println("old status: " + resCreateOrder.getOrder().getOldStatus());
            System.out.println("decision code: " + resCreateOrder.getOrder().getDecisionCode());
            System.out.println("category: " + resCreateOrder.getOrder().getCategory());

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
        order.setId("javasdktest00");
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

        LineItem lineItem = new LineItem(200, 4, "ACME Spring", "AAA2");
        lineItem.setRegistryType(RegistryType.other);

        TravelLineItem travelLineItem = new TravelLineItem(340, 1, "Flight from Israel to France", "211", "B11", 1, 1);
        travelLineItem.setDeparturePortCode("LLBG");
        travelLineItem.setDepartureCountryCode("IL");
        travelLineItem.setDepartureCity("Tel Aviv");
        travelLineItem.setDepartureDate(getDate(2014, Calendar.MARCH, 5, 12, 30, 0));
        travelLineItem.setArrivalPortCode("LBG");
        travelLineItem.setArrivalCountryCode("FR");
        travelLineItem.setArrivalCity("Paris");
        travelLineItem.setArrivalDate(getDate(2014, Calendar.MARCH, 5, 15, 30, 0));
        travelLineItem.setTicketClass("economy");
        travelLineItem.setCarrierCode("AF");
        travelLineItem.setCarrierName("Air France");
        travelLineItem.setRequiresShipping(false);

        RideLineItem rideLineItem = new RideLineItem(75, 1, "Ride from Airport", new Date(2019, 01, 15, 12, 00, 00), 1, 1);
        rideLineItem.setPickupAddress(new Address(null, null, "123 Farewell St., 12345", "New York", "1227560912", "USA"));
        rideLineItem.setTransportMethod("car");
        rideLineItem.setVehicleClass("executive");
        rideLineItem.setNoteToDriver("Wait in back entrance");

        order.setLineItems(Arrays.asList(new LineItem(100, 1, "ACME Widget", "101"), lineItem, travelLineItem, rideLineItem));

        Passenger passenger = new Passenger("john","smith");
        passenger.setDateOfBirth(getDate(1988, Calendar.MARCH, 5));
        passenger.setNationalityCode("IL");
        passenger.setInsuranceType("full");
        passenger.setInsurancePrice(11);
        passenger.setDocumentNumber("123456");
        passenger.setDocumentType("Passport");
        passenger.setDocumentIssueDate(getDate(1988, Calendar.MARCH, 5));
        passenger.setDocumentExpirationDate(getDate(2020, Calendar.MARCH, 5));
        passenger.setPassengerType("Adult");

        order.setPassengers(Arrays.asList(passenger));

        Seller seller = new Seller(customer);
        seller.setPriceNegotiated(true);
        seller.setStartingPrice(400);


        order.setDiscountCodes(Arrays.asList(new DiscountCode(19.95, "12")));

        order.setShippingLines(Arrays.asList(new ShippingLine(123, "free")));

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

        address = new Address("John", "Doe", "108 Main Street", "NYC", "1234567", "United States");
        address.setCompany("Kansas Computers");
        address.setCountryCode("US");
        address.setName("John Doe");
        address.setAddress2("Apartment 12");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("64155");
        order.setShippingAddress(address);

        Custom custom = new Custom("D2C");
        order.setCustom(custom);

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
