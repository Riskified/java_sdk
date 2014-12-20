package com.riskified.samples.orderClient;

import com.riskified.RiskifiedClient;
import com.riskified.models.Address;
import com.riskified.models.ArrayOrders;
import com.riskified.models.CancelOrder;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.DiscountCodes;
import com.riskified.models.LineItem;
import com.riskified.models.Order;
import com.riskified.models.RefundDetails;
import com.riskified.models.RefundOrder;
import com.riskified.models.Response;
import com.riskified.models.ShippingLine;

import java.util.Arrays;
import java.util.Date;

public class Client {
    public static void main(String[] arg) {
        Order order = new Order();
        order.setId("1234");
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
                new LineItem(100, 1, "ACME Widget", "101", "ABCD"),
                new LineItem(200, 4, "ACME Spring", "202", "EFGH")));

        order.setDiscountCodes(Arrays.asList(new DiscountCodes(19.95, "12")));

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

        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setEmail("another.email@example.com");

        ArrayOrders orders = new ArrayOrders();
        orders.getOrders().add(order);
        orders.getOrders().add(order);

        CancelOrder cancel = new CancelOrder();
        cancel.setId(order.getId());
        cancel.setCancelReason("test");
        cancel.setCancelledAt(new Date());

        RefundOrder refund = new RefundOrder();
        refund.setId(order.getId());
        RefundDetails refundDetail = new RefundDetails();
        refundDetail.setRefundId("refund_001");
        refundDetail.setAmount(33.12);
        refundDetail.setCurrency("USD");
        refundDetail.setReason("Product Missing");
        refund.setRefunds(Arrays.asList(refundDetail));

        Response res;
        try {
            RiskifiedClient client = new RiskifiedClient();
            res = client.createOrder(order);
            //System.out.println(res.getReceived()); // for historicalOrders only
            System.out.println(res.getOrder().getId());
            System.out.println(res.getOrder().getStatus());
            System.out.println(res.getOrder().getDescription());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
