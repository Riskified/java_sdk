package orderClient;

import java.util.Date;

import main.java.com.riskified.RiskifiedClient;
import main.java.com.riskified.models.Address;
import main.java.com.riskified.models.ArrayOrders;
import main.java.com.riskified.models.CancelOrder;
import main.java.com.riskified.models.CreditCardPaymentDetails;
import main.java.com.riskified.models.DiscountCodes;
import main.java.com.riskified.models.LineItem;
import main.java.com.riskified.models.Order;
import main.java.com.riskified.models.RefundDetails;
import main.java.com.riskified.models.RefundOrder;
import main.java.com.riskified.models.Response;
import main.java.com.riskified.models.ShippingLine;

public class Client {
  public static void main(String[] arg) {
    Order order = new Order();
    order.id = "b1234";
    order.name = "#b1234";
    order.email = "great.customer@example.com";
    order.createdAt = new Date(114,01,10,11,00,00);
    order.closedAt = null;
    order.currency = "CAD";
    order.updatedAt = new Date(114,01,10,11,00,00);
    order.gateway = "mypaymentprocessor";
    order.browserIp = "124.185.86.55";
    order.totalPrice = 113.23;
    order.totalDiscounts = 5;
    order.cartToken = "1sdaf23j212";
    order.additionalEmails.add("my@email.com");
    order.additionalEmails.add("second@email.co.uk");
    order.note = "Shipped to my hotel.";
    order.referringSite = "google.com";
    
    order.lineItems.add(new LineItem(100, 1, "ACME Widget", "101", "ABCD"));
    order.lineItems.add(new LineItem(200, 4, "ACME Spring", "202", "EFGH"));
    
    order.discountCodes.add(new DiscountCodes(19.95, "12"));
    
    order.shippingLines.add(new ShippingLine(123, "free"));
    
    order.paymentDetails = new CreditCardPaymentDetails("370002", "y", "n", "xxxx-xxxx-xxxx-1234", "VISA");
    
    order.billingAddress = new Address("John", "Doe", "108 Main Street", "Kansas Computers", "United States", "US");
    order.billingAddress.phone = "1234567";
    order.billingAddress.city = "NYC";
    order.billingAddress.name = "John Doe";
    order.billingAddress.address2 = "Apartment 12";
    order.billingAddress.province = "New York";
    order.billingAddress.provinceCode = "NY";
    order.billingAddress.zip = "64155";
    
    order.shippingAddress = new Address("John", "Doe", "108 Main Street", "Kansas Computers", "United States", "US");
    order.shippingAddress.phone = "1234567";
    order.shippingAddress.city = "NYC";
    order.shippingAddress.name = "John Doe";
    order.shippingAddress.address2 = "Apartment 12";
    order.shippingAddress.province = "New York";
    order.shippingAddress.provinceCode = "NY";
    order.shippingAddress.zip = "64155"; 
    
    Order updateOrder = new Order();
    updateOrder.id = order.id;
    updateOrder.email = "another.email@example.com";

    ArrayOrders orders = new ArrayOrders();
    orders.orders.add(order);
    orders.orders.add(order);

    CancelOrder cancel = new CancelOrder();
    cancel.id = "omer";
    cancel.cancelReason = "test";
    cancel.cancelledAt = new Date();

    RefundOrder refund = new RefundOrder();
    refund.id = order.id;
    RefundDetails refundDetail = new RefundDetails();
    refundDetail.refundId = "refund_001";
    refundDetail.amount = 33.12;
    refundDetail.currency = "USD";
    refundDetail.reason = "Product Missing";
    refund.refunds.add(refundDetail);

    String authKey = "123";
    String shopUrl = "shop.url";
    Response res;
    try {
      RiskifiedClient client = new RiskifiedClient();

      res = client.createOrder(order);
      System.out.println(res.order.id);
      System.out.println(res.order.status);
      System.out.println(res.order.description);
        
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
