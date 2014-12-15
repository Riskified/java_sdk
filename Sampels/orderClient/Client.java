package orderClient;

import java.util.Date;

import main.java.com.riskified.RiskifiedClient;
import main.java.com.riskified.models.Address;
import main.java.com.riskified.models.ArrayOrders;
import main.java.com.riskified.models.CancelOrder;
import main.java.com.riskified.models.Order;
import main.java.com.riskified.models.RefundDetails;
import main.java.com.riskified.models.RefundOrder;
import main.java.com.riskified.models.Response;

public class Client {
  public static void main(String[] arg) {
    Order order = new Order();
    order.id = "1";
    order.createdAt = new Date();
    Address billing = new Address();
    billing.address1 = "Tel Aviv";
    order.billingAddress = billing;
    order.shippingAddress = billing;

    ArrayOrders orders = new ArrayOrders();
    orders.orders.add(order);
    orders.orders.add(order);

    CancelOrder cancel = new CancelOrder();
    cancel.id = "omer";
    cancel.cancelReason = "test";
    cancel.cancelledAt = new Date();

    RefundOrder refund = new RefundOrder();
    refund.id = "omer";
    RefundDetails refundDetail = new RefundDetails();
    refundDetail.refundId = "omer1";
    refundDetail.amount = (float) 200;
    refundDetail.currency = "USD";

    refund.refunds.add(refundDetail);

    String authKey = "123";
    String shopUrl = "shop.url";
    Response res;
    try {
      RiskifiedClient client = new RiskifiedClient(shopUrl, authKey);

      res = client.createOrder(order);
      if (res.status == 200) {
        System.out.println(res.order.id);
        System.out.println(res.order.status);
        System.out.println(res.order.description);
      } else {
        System.out.println(res.status);
        System.out.println(res.error.message);
      }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
