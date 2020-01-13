package com.riskified.samples.orderClient;
import java.io.IOException;
import java.util.*;

import com.riskified.Environment;
import com.riskified.validations.Validation;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;

import com.riskified.RiskifiedError;
import com.riskified.RiskifiedClient;
import com.riskified.models.*;
import com.riskified.validations.FieldBadFormatException;

public class AccountActionClient {
    public static void main(String[] arg) throws FieldBadFormatException {

        Login login = generateLogin();
        CustomerCreate customerCreate = generateCustomerCreate();
        CustomerUpdate customerUpdate = generateCustomerUpdate();
        Logout logout = generateLogout();
        ResetPassword resetPassword = generateResetPassword();
        Wishlist wishlist = generateWishlist();
        Contact contact = generateContact();

        try {
            // Riskified client parameters can be set in the constructor, like this:
            // RiskifiedClient client = new RiskifiedClient("<shop_url>", "<auth_token>", Environment.SANDBOX);
            // Or according 'riskified_sdk.properties' configuration file, like this:
            RiskifiedClient client = new RiskifiedClient();

            // To run a different action, please comment out login action and uncomment an action you want to test
            Response resCreateOrder = client.login(login);
//            Response resCreateOrder = client.customerCreate(customerCreate);
//            Response resCreateOrder = client.customerUpdate(customerUpdate);
//            Response resCreateOrder = client.logout(logout);
//            Response resCreateOrder = client.resetPassword(resetPassword);
//            Response resCreateOrder = client.wishlist(wishlist);
//            Response resCreateOrder = client.contact(contact);

            System.out.println("-----------------------------------------");
            System.out.println(resCreateOrder);

            System.out.println("-----------------------------------------");
            System.out.println("Account action response:");
            System.out.println("decision: " + resCreateOrder.getDecision());



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

    private static Login generateLogin() {
        LoginStatus loginStatus = new LoginStatus(LoginStatusType.success);
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        SessionDetails sessionDetails = new SessionDetails(new Date(), "68778783ad298f1c80c3bafcddeea02f", "111.111.111.111", Source.desktopWeb);
        Login login = new Login("207119551", "test@test.com", loginStatus, clientDetails, sessionDetails);
        login.setLoginAtCheckout(true);
        login.setSocialLoginType(SocialType.facebook);
        login.setCustomerCreatedAt(new Date(2020, 01, 06, 13, 00, 00));;
        return login;
    }

    private static CustomerCreate generateCustomerCreate() {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        clientDetails.setAcceptLanguage("en-CA");
        SessionDetails sessionDetails = new SessionDetails(new Date(), "68778783ad298f1c80c3bafcddeea02f", "111.111.111.111", Source.desktopWeb);
        sessionDetails.setReferringSite("http://www.nba.com/");
        Customer customer = new Customer("bob.norman@hostmail.com", "Bob", "Norman");
        customer.setVerifiedEmail(true);
        customer.setId("207119551");
        customer.setCreatedAt(new Date());
        CustomerCreate customerCreate = new CustomerCreate("207119551", clientDetails, sessionDetails, customer);
        IPaymentDetails payment1 = new CreditCardPaymentDetails("123456", "Y", "M", "4242", "Visa");
        customerCreate.setPaymentDetails(Arrays.asList(payment1));
        customerCreate.setPhoneMandatory(false);
        Address addy = new Address("Bob", "Norman", "Chestnut Street 92", "Louisville", "555-625-1199", "United States");
        addy.setAddress2("");
        addy.setCountryCode("US");
        addy.setProvince("Kentucky");
        addy.setProvinceCode("KY");
        addy.setZip("40202");
        customerCreate.setShippingAddress(Arrays.asList(addy));
        customerCreate.setBillingAddress(Arrays.asList(addy));
        return customerCreate;
    }

    private static CustomerUpdate generateCustomerUpdate() {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        clientDetails.setAcceptLanguage("en-CA");
        SessionDetails sessionDetails = new SessionDetails(new Date(), "68778783ad298f1c80c3bafcddeea02f", "111.111.111.111", Source.desktopWeb);
        sessionDetails.setReferringSite("http://www.nba.com/");
        Customer customer = new Customer("bob.norman@hostmail.com", "Bob", "Norman");
        customer.setVerifiedEmail(true);
        customer.setId("207119551");
        customer.setCreatedAt(new Date());
        CustomerUpdate customerUpdate = new CustomerUpdate("207119551", false, clientDetails, sessionDetails, customer);
        IPaymentDetails payment1 = new CreditCardPaymentDetails("123456", "Y", "M", "4242", "Visa");
        customerUpdate.setPaymentDetails(Arrays.asList(payment1));
        Address addy = new Address("Bob", "Norman", "Chestnut Street 92", "Louisville", "555-625-1199", "United States");
        addy.setAddress2("");
        addy.setCountryCode("US");
        addy.setProvince("Kentucky");
        addy.setProvinceCode("KY");
        addy.setZip("40202");
        customerUpdate.setShippingAddress(Arrays.asList(addy));
        customerUpdate.setBillingAddress(Arrays.asList(addy));
        return customerUpdate;
    }

    private static Logout generateLogout() {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        SessionDetails sessionDetails = new SessionDetails(new Date(), "68778783ad298f1c80c3bafcddeea02f", "111.111.111.111", Source.desktopWeb);
        return new Logout("207119551", clientDetails, sessionDetails);
    }

    private static ResetPassword generateResetPassword() {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        SessionDetails sessionDetails = new SessionDetails(new Date(), "68778783ad298f1c80c3bafcddeea02f", "111.111.111.111", Source.desktopWeb);
        return new ResetPassword("207119551", clientDetails, sessionDetails);
    }

    private static Wishlist generateWishlist() {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        SessionDetails sessionDetails = new SessionDetails(new Date(), "68778783ad298f1c80c3bafcddeea02f", "111.111.111.111", Source.desktopWeb);
        LineItem lineItem = new LineItem(199, 1, "IPod Nano - 8gb - green", "632910392");
        lineItem.setBrand("Apple");
        lineItem.setProductType("physical");
        lineItem.setCategory("electronics");
        return new Wishlist("207119551", "add", clientDetails, sessionDetails, lineItem);
    }

    private static Contact generateContact() {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
        SessionDetails sessionDetails = new SessionDetails(new Date(), "68778783ad298f1c80c3bafcddeea02f", "111.111.111.111", Source.desktopWeb);
        ContactMethod contactMethod = new ContactMethod(ContactMethodType.email);
        contactMethod.setEmail("moo@gmail.com");
        Contact contact = new Contact("207119551", contactMethod);
        contact.setClientDetails(clientDetails);
        contact.setSessionDetails(sessionDetails);
        contact.setOrderId("450789469");
        return contact;
    }
}
