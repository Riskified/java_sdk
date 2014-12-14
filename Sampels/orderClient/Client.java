package orderClient;

import java.util.Date;

import riskified.RiskifiedClient;
import models.Address;
import models.ArrayOrders;
import models.CancelOrder;
import models.Order;
import models.Refund;
import models.RefundDetails;
import models.RefundOrder;
import models.Response;


public class Client {
    public static void main(String[] args) {
    	Order order = new Order();
    	order.id = "1";
    	order.created_at = new Date();
    	Address billing = new Address();
    	billing.address1 = "Tel Aviv";
    	order.billing_address = billing;
    	order.shipping_address = billing;
    	
    	ArrayOrders orders = new ArrayOrders();
    	orders.orders.add(order);
    	orders.orders.add(order);
    	
    	CancelOrder cancel = new CancelOrder();
    	cancel.id = "omer";
    	cancel.cancel_reason = "test";
    	cancel.cancelled_at = new Date();
    	
    	Refund refund = new Refund();
    	refund.order = new RefundOrder();
    	refund.order.id = "omer";
    	RefundDetails refundDetail = new RefundDetails();
    	refundDetail.refund_id = "omer1";
    	refundDetail.amount = (float) 200;
    	refundDetail.currency = "USD";
    	
    	refund.order.refunds.add(refundDetail);

		String authKey = "123";  	
		String shopUrl = "shop.com";
		RiskifiedClient client = new RiskifiedClient(shopUrl, authKey);
		Response res;
		try {
			res = client.createOrder(order);
			if (res.status == 200) {
				System.out.println(res.order.id);
				System.out.println(res.order.status);
				System.out.println(res.order.description);
			}
			else {
				System.out.println(res.status);
				System.out.println(res.error.message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    }

