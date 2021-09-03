package com.ry.secure;


import com.riskified.Environment;
import com.riskified.RiskifiedError;
import com.riskified._type;
import com.riskified.models.*;
import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.Validation;
import com.ry.secure.crypto.codec.AegisDecoderException;
import com.ry.secure.crypto.rootkey.RootKeyUtil;
import com.ry.secure.crypto.rootkey.aes.AesCryptUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import com.riskified.RiskifiedClient;


public class AesGcm {

    private AesConfigBean aesConfigBean;

    public AesGcm() {
    }


    private void initAesConfig() {
        Properties propertiesFirst = this.getProperties("configs-project/configs.properties");
        if (propertiesFirst == null) {
            propertiesFirst = this.getProperties("config/secure.properties");
        }

        Properties propertiesSecond = this.getProperties("configs-project/configs.properties");
        if (propertiesSecond == null) {
            propertiesSecond = this.getProperties("config/secure.properties");
        }

        Properties propertiesThird = this.getProperties("configs-project/configs.properties");
        if (propertiesThird == null) {
            propertiesThird = this.getProperties("config/secure.properties");
        }

        String workKey = propertiesFirst.getProperty("aes.gcm.workKey");
        String first = propertiesFirst.getProperty("aes.gcm.first");
        String salt = propertiesFirst.getProperty("aes.gcm.salt");
        String second = propertiesSecond.getProperty("aes.gcm.second");
        String third = propertiesThird.getProperty("aes.gcm.third");
        this.aesConfigBean = new AesConfigBean();
        this.aesConfigBean.setWorkKey(workKey);
        this.aesConfigBean.setFirst(first);
        this.aesConfigBean.setSecond(second);
        this.aesConfigBean.setThird(third);
        this.aesConfigBean.setSalt(salt);
    }
    private Properties getProperties(String filePath) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = null;
        Properties properties = new Properties();

        Properties var5;
        try {
            is = cl.getResourceAsStream(filePath);
            if (is == null) {
                var5 = null;
                return var5;
            }

            properties.load(is);
            var5 = properties;
        } catch (Exception var15) {
            throw new RuntimeException(var15);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var14) {
                    ;
                }
            }

        }

        return var5;
    }


    public static void main(String[] arg) throws AegisDecoderException, GeneralSecurityException, IOException, ParseException, RiskifiedError, FieldBadFormatException {
        testAesGcmDecrypt();
    }

    public static void testAesGcmDecrypt() throws IOException, GeneralSecurityException, AegisDecoderException, ParseException, FieldBadFormatException, RiskifiedError {
        AesGcm ag = new AesGcm();
        ag.initAesConfig();
        // Initializing Riskified
        RiskifiedClient riskifiedClient = new RiskifiedClient("Shop URL ","AuthToken", Environment.SANDBOX, Validation.NONE);


        /**
         * // adviseOrder API example
         **/

        CheckoutOrder adviseOrder = generateAdviseOrder();
        String adviseOrderData = riskifiedClient.getCheckoutOrderJson(adviseOrder);
        System.out.println("adviseOrderData = " + adviseOrderData);
        String adviseEncryptedContent = ag.encrypt(adviseOrderData);
        System.out.println("adviseEncryptedContent = " + adviseEncryptedContent);
        riskifiedClient.adviseOrderEncrypted(adviseEncryptedContent);



        /**
         * /decide API example
         **/

        Order decideOrder = generateOrder();
        String decideOrderData = riskifiedClient.getOrderJson(decideOrder);
        System.out.println("decideOrderData = " + decideOrderData);
        String decideOrderEncryptedContent = ag.encrypt(decideOrderData);
        System.out.println("decideOrderEncryptedContent = " + decideOrderEncryptedContent);
        riskifiedClient.analyzeEncryptedOrder(decideOrderEncryptedContent);



        /**
         * /cancel API example
         **/

        CancelOrder cancelOrder = generateCancelOrder(decideOrder);
        cancelOrder.setCancelReason("test");
        cancelOrder.setCancelledAt(new Date());
        String cancelOrderData = riskifiedClient.getOrderJson(cancelOrder);
        System.out.println("cancelOrder JSON = " + cancelOrderData );
        String cancelOrderEncryptedContent = ag.encrypt(cancelOrderData);
        System.out.println("cancelOrderEncryptedContent  = " + cancelOrderEncryptedContent );
        riskifiedClient.cancelEncryptedOrder(cancelOrderEncryptedContent);



        /**
         * /checkout_denied API example
         **/

        CheckoutDeniedOrder checkoutDeniedOrder = generateCheckoutDeniedOrder();
        String checkoutDeniedOrderData = riskifiedClient.getCheckoutOrderJson(checkoutDeniedOrder);
        System.out.println("checkoutDeniedOrderData JSON = " + checkoutDeniedOrderData );
        String checkoutDeniedEncryptedContent = ag.encrypt(checkoutDeniedOrderData);
        System.out.println("checkoutDeniedEncryptedContent  = " + checkoutDeniedEncryptedContent );
        riskifiedClient.checkoutDeniedEncryptedOrder(checkoutDeniedEncryptedContent);


        /**
         *  /refund API example
         **/

        RefundOrder refundOrder = generateRefundOrder(decideOrder);
        String refundOrderData = riskifiedClient.getOrderJson(refundOrder);
        System.out.println("refundOrderData JSON = " + refundOrderData);
        String refundEncryptedContent = ag.encrypt(refundOrderData);
        System.out.println("refundEncryptedContent  = " + refundEncryptedContent );
        riskifiedClient.refundEncryptedOrder(refundEncryptedContent);



/*
//        System.out.println(adviseOrder);
        String enCodeContent = JSON.toJSONString(adviseOrder );
        System.out.println("原文: " + enCodeContent);
        String content = ag.encrypt(enCodeContent);
        System.out.println("加密后密文:" + content);

        // 需要解密的字符串
        String deCodeContent = content;
        String result = ag.decrypt(deCodeContent);
        System.out.println("解密后明文:" + result);

        CheckoutOrder deCodeAdviseOrder = JSON.parseObject(result, CheckoutOrder.class);
        System.out.println(deCodeAdviseOrder);

        System.out.println("------------------------------------------------------------------");

        Order order = generateOrder();
        String enCodeContent2 = JSON.toJSONString(order );
        System.out.println("原文: " + enCodeContent2);
        String content2 = ag.encrypt(enCodeContent2);
        System.out.println("加密后密文:" + content2);
        // 需要解密的字符串
        String deCodeContent2 = content2;
        String result2 = ag.decrypt(deCodeContent2);
        System.out.println("解密后明文:" + result2);
        Order order2 = JSON.parseObject(result, Order.class);
        System.out.println(order2);
        System.out.println("------------------------------------------------------------------");

        CancelOrder cancelOrder = generateCancelOrder(order);
        String enCodeContent3 = JSON.toJSONString(cancelOrder );
        System.out.println("原文: " + enCodeContent3);
        String content3 = ag.encrypt(enCodeContent3);
        System.out.println("加密后密文:" + content3);
        // 需要解密的字符串
        String deCodeContent3 = content3;
        String result3 = ag.decrypt(deCodeContent3);
        System.out.println("解密后明文:" + result3);
        CancelOrder order3 = JSON.parseObject(result, CancelOrder.class);
        System.out.println(order3); */

    }

    /**
     * 加密
     * @param originalString
     * @param key
     * @return
     */
    public String encrypt(String originalString, String key) {
        String result = "";
        try {
            result = AesCryptUtil.encryptAesGcm(originalString, this.aesConfigBean.getWorkKey(),getRootKeyUtil());
        } catch (GeneralSecurityException | AegisDecoderException| IOException  var5) {
            ;
        }
        return result;
    }

    /**
     * 加密
     * @param originalString
     * @return
     */
    public String encrypt(String originalString) {
        return this.encrypt(originalString, this.aesConfigBean.getWorkKey());
    }


    /**
     * 解密
     * @param encryptedString
     * @param key
     * @return
     */
    public String decrypt(String encryptedString, String key) {
        String result = "";
        try {
            result = AesCryptUtil.decryptAesGcm(encryptedString, this.aesConfigBean.getWorkKey(),getRootKeyUtil());
        } catch (GeneralSecurityException | AegisDecoderException | IOException var5) {
            ;
        }
        return result;
    }

    /**
     * 解密
     * @param encryptedString
     * @return
     */
    public String decrypt(String encryptedString) {
        return this.decrypt(encryptedString, this.aesConfigBean.getWorkKey());
    }

    /**
     *
     * @return RootKeyUtil
     * @throws IOException 实例化失败抛出 IOException
     */
    public RootKeyUtil getRootKeyUtil() throws IOException {
        return RootKeyUtil.newInstance(this.aesConfigBean.getFirst(), this.aesConfigBean.getSecond(),
                this.aesConfigBean.getThird(), this.aesConfigBean.getSalt());
    }




    private static CheckoutOrder generateAdviseOrder() throws ParseException {
        CheckoutOrder order = new CheckoutOrder();

        order.setId("99992328889900");
        order.setName("#123422111000");
        order.setEmail("great.customer_2@example.com");
        order.setCreatedAt(parseDate("06-24-2021 00:00:00.0"));
        order.setClosedAt(null);
        order.setCurrency("CAD");
        order.setUpdatedAt(parseDate("06-24-2021 00:00:00.0"));
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
        order.setId("#12000000000346");
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

    private static CancelOrder generateCancelOrder(Order order) {
        CancelOrder cancel = new CancelOrder();
        cancel.setId(order.getId());
//        cancel.setCancelReason("test");
//        cancel.setCancelledAt(new Date());
        return cancel;
    }

    private static CheckoutDeniedOrder generateCheckoutDeniedOrder() throws ParseException {

        AuthorizationError authorizationError = new AuthorizationError("card_declined", parseDate("15-12-2016 00:00:00.0"));
        authorizationError.setMessage("expired credit card.");

        CreditCardPaymentDetails creditCardPaymentDetails = new CreditCardPaymentDetails("666666", "full", "m", "4444", "visa");
        creditCardPaymentDetails.setAuthorizationError(authorizationError);

        CheckoutDeniedOrder checkoutDeniedOrder = new CheckoutDeniedOrder("cd123456");
        checkoutDeniedOrder.setPaymentDetails(Arrays.asList(creditCardPaymentDetails));

        return checkoutDeniedOrder;
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



    private static Date parseDate(String date) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        return dt.parse(date);
    }
}




