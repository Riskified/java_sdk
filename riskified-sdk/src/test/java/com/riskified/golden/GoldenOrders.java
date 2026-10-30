package com.riskified.golden;

import com.riskified.TransStatus;
import com.riskified.TransStatusReason;
import com.riskified.models.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds one fully populated sample per flow, wrapped the same way {@code RiskifiedClient} wraps it
 * before calling {@code JSONFormater.toJson(...)}.
 *
 * <p>Every value is written out by hand rather than filled in reflectively, so a field added to a
 * model later does not change an existing snapshot. New fields get new samples.
 */
public final class GoldenOrders {

    private GoldenOrders() {
    }

    /** Sample name (also the snapshot file name) to the wrapped payload. */
    public static Map<String, Object> all() {
        Map<String, Object> samples = new LinkedHashMap<String, Object>();
        samples.put("card_order", new OrderWrapper<Order>(cardOrder()));
        samples.put("paypal_order", new OrderWrapper<Order>(paypalOrder()));
        samples.put("wallet_order", new OrderWrapper<Order>(walletOrder()));
        samples.put("bank_wire_order", new OrderWrapper<Order>(bankWireOrder()));
        samples.put("digital_line_item_order", new OrderWrapper<Order>(lineItemOrder("digital", digitalLineItem())));
        samples.put("travel_line_item_order", new OrderWrapper<Order>(lineItemOrder("travel", travelLineItem())));
        samples.put("ride_line_item_order", new OrderWrapper<Order>(lineItemOrder("ride", rideLineItem())));
        samples.put("event_line_item_order", new OrderWrapper<Order>(lineItemOrder("event", eventLineItem())));
        samples.put("accommodation_line_item_order", new OrderWrapper<Order>(lineItemOrder("accommodation", accommodationLineItem())));
        samples.put("checkout_denied_order", new CheckoutOrderWrapper<CheckoutDeniedOrder>(checkoutDeniedOrder()));
        samples.put("decision_order", new OrderWrapper<DecisionOrder>(decisionOrder()));
        // 6.6.0 additions. New samples rather than changes to the ones above, so those snapshots stay identical.
        samples.put("card_order_tokenized", new OrderWrapper<Order>(cardOrderTokenized()));
        samples.put("wallet_checkout_denied_order", new CheckoutOrderWrapper<CheckoutDeniedOrder>(walletCheckoutDeniedOrder()));
        samples.put("alternative_payments_order", new OrderWrapper<Order>(alternativePaymentsOrder()));
        samples.put("customer_profile_order", new OrderWrapper<Order>(customerProfileOrder()));
        samples.put("invoice_and_address_fields_order", new OrderWrapper<Order>(invoiceAndAddressFieldsOrder()));
        samples.put("line_item_fields_order", new OrderWrapper<Order>(lineItemFieldsOrder()));
        samples.put("crypto_and_social_order", new OrderWrapper<Order>(cryptoAndSocialOrder()));
        return samples;
    }

    // Orders

    static Order cardOrder() {
        Order order = new Order();
        populateBaseOrder(order, "golden-card-1");
        order.setPaymentDetails(Collections.singletonList(creditCardPaymentDetails()));
        return order;
    }

    static Order paypalOrder() {
        Order order = new Order();
        populateBaseOrder(order, "golden-paypal-1");
        order.setGateway("paypal");
        order.setPaymentDetails(Collections.singletonList(paypalPaymentDetails()));
        return order;
    }

    static Order walletOrder() {
        Order order = new Order();
        populateBaseOrder(order, "golden-wallet-1");
        order.setPaymentDetails(Collections.singletonList(walletPaymentDetails()));
        return order;
    }

    static Order bankWireOrder() {
        Order order = new Order();
        populateBaseOrder(order, "golden-bank-wire-1");
        order.setGateway("plaid");
        order.setPaymentDetails(Collections.singletonList(bankWirePaymentDetails()));
        return order;
    }

    /** A minimal order shell around one line item; the full order root is covered by the payment samples. */
    static Order lineItemOrder(String kind, LineItem lineItem) {
        Order order = new Order();
        order.setId("golden-" + kind + "-line-item-1");
        order.setCurrency("USD");
        order.setTotalPrice(lineItem.getPrice());
        order.setLineItems(Collections.singletonList(lineItem));
        return order;
    }

    static CheckoutDeniedOrder checkoutDeniedOrder() {
        CheckoutDeniedOrder order = new CheckoutDeniedOrder("golden-checkout-denied-1", authorizationError());
        populateBaseOrder(order, "golden-checkout-denied-1");
        order.setPaymentDetails(Collections.singletonList(creditCardPaymentDetails()));
        return order;
    }

    static DecisionOrder decisionOrder() {
        DecisionDetails decision = new DecisionDetails(DecisionType.approved, date("2024-01-15T11:00:00Z"), "manual review");
        decision.setAmount(129.99);
        decision.setCurrency("USD");
        decision.setNotes("approved after call with customer");

        DecisionOrder order = new DecisionOrder("golden-decision-1", decision);
        order.setGateway("stripe");
        order.setSubmissionReason("manual_decision");
        order.setPaymentDetails(Collections.singletonList(creditCardPaymentDetails()));
        return order;
    }

    static Order cardOrderTokenized() {
        CreditCardPaymentDetails details = creditCardPaymentDetails();
        details.setCreditCardToken("card-token-1");
        details.setBillingAddressId("address-billing");
        details.setInitialPaymentAmount(50.0);
        details.setPaymentFrequency(30);

        Order order = new Order();
        order.setId("golden-card-tokenized-1");
        order.setCurrency("USD");
        order.setTotalPrice(129.99);
        order.setPaymentDetails(Collections.singletonList(details));
        return order;
    }

    static CheckoutDeniedOrder walletCheckoutDeniedOrder() {
        WalletPaymentDetails details = walletPaymentDetails();
        details.setAuthorizationError(authorizationError());

        CheckoutDeniedOrder order = new CheckoutDeniedOrder("golden-wallet-checkout-denied-1");
        order.setCurrency("USD");
        order.setTotalPrice(129.99);
        order.setPaymentDetails(Collections.singletonList(details));
        return order;
    }

    /** A buy-now-pay-later payment plus a gift card, which can't be charged back. */
    static Order alternativePaymentsOrder() {
        BuyNowPayLaterPaymentDetails bnpl = new BuyNowPayLaterPaymentDetails();
        bnpl.setInitialPaymentAmount(32.5);
        bnpl.setInstallments(4);
        bnpl.setPaymentFrequency(14);

        Order order = new Order();
        order.setId("golden-alternative-payments-1");
        order.setCurrency("USD");
        order.setTotalPrice(150.0);
        order.setPaymentDetails(Arrays.asList(bnpl, new NonChbPaymentDetails(PaymentType.GIFT_CARD)));
        return order;
    }

    /** A customer with the documented fields added in 6.6.0, plus the ones the docs require. */
    static Order customerProfileOrder() {
        Customer customer = new Customer("jane.doe@example.com", "Jane", "Doe");
        customer.setCreatedAt(date("2022-05-01T12:00:00Z"));
        customer.setBuyAttempts(3);
        customer.setSellAttempts(1);
        customer.setBuyerAnonymousId("anon-1");
        customer.setReferrerCustomerId("customer-0");
        customer.setFirstPurchaseAt(date("2022-05-02T09:00:00Z"));
        customer.setVerifiedEmailAt(date("2022-05-01T12:05:00Z"));
        customer.setKycVerified(true);
        customer.setPhoneMandatory(false);
        customer.setSpendingLimit(5000.0);
        customer.setSocialSignupType(SocialType.google);

        Order order = new Order();
        order.setId("golden-customer-profile-1");
        order.setCurrency("USD");
        order.setTotalPrice(129.99);
        order.setCustomer(customer);
        return order;
    }

    /** The order's invoice ID and the address fields added in 6.6.0. */
    static Order invoiceAndAddressFieldsOrder() {
        Address shipping = address("shipping");
        shipping.setId("address-shipping");
        shipping.setAdditionalPhone("+15555550101");
        shipping.setNeighborhood("Chelsea");
        Address billing = address("billing");
        billing.setId("address-billing");
        billing.setAdditionalPhone("+15555550102");
        billing.setNeighborhood("Midtown");

        Order order = new Order();
        order.setId("golden-invoice-and-address-1");
        order.setCurrency("USD");
        order.setTotalPrice(129.99);
        order.setInvoiceId("invoice-1");
        order.setShippingAddress(Collections.singletonList(shipping));
        order.setBillingAddress(Collections.singletonList(billing));
        return order;
    }

    /**
     * One line item per class that gained a field in 6.6.0. Each sets only what the docs require plus the new
     * fields, so its schema check isn't mixed up with the known gaps of the fully populated samples.
     */
    static Order lineItemFieldsOrder() {
        LineItem physical = new LineItem(20.0, 1, "Widget", "product-physical");
        requiredLineItemFields(physical, "physical", true);
        physical.setDropPoint("locker-17");
        physical.setShippingAddressId("address-shipping");

        EventLineItem event = new EventLineItem(80.0, 2, "Concert ticket", "product-event");
        requiredLineItemFields(event, "event", false);
        event.setEventDate(date("2024-04-10T19:30:00Z"));
        event.setCity("New York");
        event.setCountryCode("US");
        event.setCancellationPolicy("refundable until 24h before");

        TravelLineItem travel = new TravelLineItem(350.0, 1, "Flight TLV-JFK", "product-travel", "leg-1", 1, 0);
        requiredLineItemFields(travel, "travel", false);
        travel.setDepartureDate(date("2024-03-01T06:00:00Z"));
        travel.setDepartureCity("Tel Aviv");
        travel.setDepartureCountryCode("IL");
        travel.setArrivalCity("New York");
        travel.setArrivalCountryCode("US");
        travel.setTransportMethod("plane");
        travel.setCancellationPolicy("non refundable");

        RideLineItem ride = new RideLineItem(42.5, 1, "Airport ride", date("2024-03-02T07:00:00Z"), 0, 1);
        ride.setProductId("product-ride");
        requiredLineItemFields(ride, "ride", false);
        ride.setDamageExcess(500.0);

        Order order = new Order();
        order.setId("golden-line-item-fields-1");
        order.setCurrency("USD");
        order.setTotalPrice(572.5);
        order.setLineItems(Arrays.<LineItem>asList(physical, event, travel, ride));
        return order;
    }

    /** Crypto wallets, recipients, shipping line and social details fields added in 6.6.0. */
    static Order cryptoAndSocialOrder() {
        CryptoWallet customerWallet = new CryptoWallet("0xc0ffee254729296a45a3885639AC7E10F9d54979", "ethereum");
        customerWallet.setType("custodial");

        Map<String, Object> socialData = new LinkedHashMap<String, Object>();
        socialData.put("verified", true);
        socialData.put("account_age_days", 1200);
        SocialDetails social = new SocialDetails("google", "janedoe");
        social.setId("google-123");
        social.setAuthToken("social-token-1");
        social.setSocialData(socialData);

        Customer customer = new Customer("jane.doe@example.com", "Jane", "Doe");
        customer.setCreatedAt(date("2022-05-01T12:00:00Z"));
        customer.setCryptoWallets(Collections.singletonList(customerWallet));
        customer.setSocial(Collections.singletonList(social));

        Recipient achRecipient = new Recipient();
        achRecipient.setAccountNumber("000987654321");
        achRecipient.setRoutingNumber("110000000");
        achRecipient.setBankCode("021000021");
        LineItem remittance = new LineItem(200.0, 1, "Money transfer", "product-remittance");
        remittance.setProductType("remittance");
        remittance.setRecipient(achRecipient);

        Recipient cryptoRecipient = new Recipient();
        cryptoRecipient.setCryptoWallet(new CryptoWallet("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh", "bitcoin"));
        LineItem crypto = new LineItem(300.0, 1, "Bitcoin purchase", "product-crypto");
        crypto.setProductType("crypto");
        crypto.setRecipient(cryptoRecipient);

        ShippingLine shippingLine = new ShippingLine(5.0, "Standard");
        shippingLine.setCompany("UPS");
        shippingLine.setShippingAddressId("address-shipping");

        Order order = new Order();
        order.setId("golden-crypto-and-social-1");
        order.setCurrency("USD");
        order.setTotalPrice(505.0);
        order.setCustomer(customer);
        order.setLineItems(Arrays.asList(remittance, crypto));
        order.setShippingLines(Collections.singletonList(shippingLine));
        return order;
    }

    static void requiredLineItemFields(LineItem item, String productType, boolean requiresShipping) {
        item.setCategory("general");
        item.setProductType(productType);
        item.setRequiresShipping(requiresShipping);
    }

    static void populateBaseOrder(BaseOrder order, String id) {
        order.setId(id);
        order.setName("#" + id);
        order.setEmail("jane.doe@example.com");
        order.setAdditionalEmails(Arrays.asList("jane.alt@example.com"));
        order.setCreatedAt(date("2024-01-15T10:30:00Z"));
        order.setUpdatedAt(date("2024-01-15T10:35:00Z"));
        order.setClosedAt(date("2024-01-15T10:40:00Z"));
        order.setCancelledAt(date("2024-01-16T09:00:00Z"));
        order.setCancelReason("customer");
        order.setCurrency("USD");
        order.setGateway("stripe");
        order.setBrowserIp("203.0.113.10");
        order.setTotalPrice(129.99);
        order.setSubtotalPrice(119.99);
        order.setTotalDiscounts(10.0);
        order.setTotalLineItemsPrice(129.99);
        order.setTotalPriceUsd(129.99);
        order.setTotalTax(10.0);
        order.setTotalWeight(500.0);
        order.setTaxesIncluded(true);
        order.setNote("leave at the door");
        order.setNumber("1001");
        order.setOrderNumber("1001");
        order.setCartToken("cart-token-1");
        order.setDeviceId("device-1");
        order.setCheckoutToken("checkout-token-1");
        order.setCheckoutId("checkout-1");
        order.setToken("token-1");
        order.setReferringSite("https://www.google.com");
        order.setLandingSite("/products/widget");
        order.setLandingSiteRef("campaign-1");
        order.setConfirmed(true);
        order.setBuyerAcceptsMarketing(false);
        order.setFinancialStatus("authorized");
        order.setFulfillmentStatus("unfulfilled");
        order.setLocationId("location-1");
        order.setSource("desktop_web");
        order.setSourceIdentifier("source-id-1");
        order.setSourceName("web");
        order.setSourceUrl("https://shop.example.com");
        order.setUserId("user-1");
        order.setProcessingMethod("direct");
        order.setTags("vip,repeat");
        order.setVendorId("vendor-1");
        order.setVendorName("Example Vendor");
        order.setVendorIntegrationType("api");
        order.setOrderType("test");
        order.setSubmissionReason("failed_verification");
        order.setPartnerSubMerchantId("sub-merchant-1");
        order.setAiAgent(AiAgent.chatgpt);
        order.setCustomer(customer());
        order.setLineItems(Collections.singletonList(physicalLineItem()));
        order.setShippingAddress(Collections.singletonList(address("shipping")));
        order.setBillingAddress(Collections.singletonList(address("billing")));
        order.setClientDetails(clientDetails());
        order.setDiscountCodes(Collections.singletonList(discountCode()));
        order.setShippingLines(Collections.singletonList(shippingLine()));
        order.setNoteAttributes(Collections.singletonList(attribute("gift_wrap", "yes")));
        order.setTaxLines(Collections.singletonList(taxLine()));
        order.setDecision(new DecisionDetails(DecisionType.approved, date("2024-01-15T10:36:00Z"), "auto"));
        order.setPassengers(Collections.singletonList(passenger()));
        order.setAdditionalData(additionalData());
        order.setNochargeAmount(new NoChargeAmount("refund-1", 5.0, "USD", "goodwill"));
        order.setChargeFreePaymentDetails(Collections.singletonList(new ChargeFreePaymentDetails("gift_card", 5.0)));
        order.setCommunicationDetails(communicationDetails());
        order.setCustom(custom());
    }

    // Payment details

    static CreditCardPaymentDetails creditCardPaymentDetails() {
        CreditCardPaymentDetails details = new CreditCardPaymentDetails("411111", "Y", "M", "XXXX-XXXX-XXXX-1111", "Visa");
        details.setAuthorizationId("auth-1");
        details.setStoredPaymentCreatedAt(date("2023-06-01T08:00:00Z"));
        details.setStoredPaymentUpdatedAt(date("2023-12-01T08:00:00Z"));
        details.setAuthorizationError(authorizationError());
        details.setAuthenticationResult(authenticationResult());
        details.setCardholderName("Jane Doe");
        details.setId("payment-1");
        details.setGateway("stripe");
        details.setAcquirerBin("123456");
        details.setMid("mid-1");
        details.setPaymentPlan("monthly");
        details.setInstallmentMonths(3);
        details.setInstallments(3);
        details.setAuthorizationType(AuthorizationType.authorization);
        details.setCreditCardCountry("US");
        details.setAcquirerRegion("EU");
        details.setExpiryMonth(12);
        details.setExpiryYear(2030);
        return details;
    }

    static PaypalPaymentDetails paypalPaymentDetails() {
        PaypalPaymentDetails details = new PaypalPaymentDetails("payer@example.com", "verified", "confirmed", "Eligible");
        details.setPaymentStatus("Completed");
        details.setPendingReason("none");
        details.setAuthorizationId("auth-2");
        details.setAuthenticationResult(authenticationResult());
        details.setAuthorizationError(authorizationError());
        details.setId("payment-2");
        details.setGateway("paypal");
        details.setAcquirerBin("654321");
        details.setMid("mid-2");
        details.setAuthorizationType(AuthorizationType.authorization);
        return details;
    }

    static WalletPaymentDetails walletPaymentDetails() {
        WalletPaymentDetails details = new WalletPaymentDetails(PaymentType.APPLE_PAY, "auth-3", "Y");
        details.setCreditCardCompany("Visa");
        details.setCreditCardCountry("US");
        details.setCreditCardToken("wallet-token-1");
        details.setCardholderName("Jane Doe");
        details.setMid("mid-3");
        details.setId("payment-3");
        details.setStoredPaymentCreatedAt(date("2023-06-01T08:00:00Z"));
        details.setStoredPaymentUpdatedAt(date("2023-12-01T08:00:00Z"));
        details.setInstallments(1);
        details.setAcquirerBin("123456");
        details.setAcquirerRegion("NONEU");
        details.setAuthorizationType(AuthorizationType.authorization);
        details.setExpiryMonth(11);
        details.setExpiryYear(2029);
        details.setInitialPaymentAmount(50.0);
        details.setPaymentFrequency(30);
        details.setBillingAddressId("address-billing");
        details.setAuthenticationResult(authenticationResult());
        return details;
    }

    static BankWirePaymentDetails bankWirePaymentDetails() {
        BankWirePaymentDetails details = new BankWirePaymentDetails("000123456789", "110000000");
        details.setToken("bank-token-1");
        details.setStoredPaymentCreatedAt(date("2023-06-01T08:00:00Z"));
        details.setStoredPaymentUpdatedAt(date("2023-12-01T08:00:00Z"));
        details.setDaysSinceAccountOpening(400);
        details.setDaysWithNegativeBalanceCount(2);
        details.setIsSavingsOrMoneyMarketAccount(false);
        details.setNsfOverdraftTransactionsCount(1);
        details.setUnauthorizedTransactionsCount(0);

        InitiatedReturnRisk customerRisk = new InitiatedReturnRisk();
        customerRisk.setScore(10);
        customerRisk.setRiskTier(1);
        InitiatedReturnRisk bankRisk = new InitiatedReturnRisk();
        bankRisk.setScore(20);
        bankRisk.setRiskTier(2);
        PlaidScores plaidScores = new PlaidScores();
        plaidScores.setCustomerInitiatedReturnRisk(customerRisk);
        plaidScores.setBankInitiatedReturnRisk(bankRisk);
        details.setPlaidScores(plaidScores);

        AccountIdentity identity = new AccountIdentity();
        identity.setNames(Arrays.asList("Jane Doe"));
        identity.setAddresses(Collections.singletonList(address("bank")));
        identity.setPhoneNumbers(Arrays.asList("+15555550100"));
        identity.setEmails(Arrays.asList("jane.doe@example.com"));
        details.setAccountIdentity(identity);

        details.setAccountBalance(new AccountBalance(2500.5, AccountBalanceServiceName.PLAID, date("2024-01-15T09:00:00Z"), "USD"));
        return details;
    }

    static AuthorizationError authorizationError() {
        AuthorizationError error = new AuthorizationError("card_declined", date("2024-01-15T10:31:00Z"));
        error.setMessage("Card was declined");
        error.setDropOff(false);
        return error;
    }

    static AuthenticationResult authenticationResult() {
        AuthenticationResult result = new AuthenticationResult("05");
        result.setTransStatus(TransStatus.Y);
        result.setTransStatusReason(TransStatusReason.Zero_One);
        result.setLiabilityShift(true);
        result.setCavv("cavv-1");
        result.setCreatedAt(date("2024-01-15T10:30:30Z"));
        result.set3DChallenge(true);
        result.setTRAExemption(false);
        return result;
    }

    // Line items

    static LineItem physicalLineItem() {
        LineItem item = new LineItem(119.99, 1, "Widget", "product-1");
        populateLineItem(item);
        item.setProductType("physical");
        return item;
    }

    static DigitalLineItem digitalLineItem() {
        DigitalLineItem item = new DigitalLineItem(50.0, 1, "Gift card");
        populateLineItem(item);
        item.setProductId("product-digital");
        item.setProductType("digital");
        item.setSenderName("John Doe");
        item.setSenderEmail("john.doe@example.com");
        item.setDisplayName("Happy birthday");
        item.setPhotoUploaded(true);
        item.setPhotoUrl("https://cdn.example.com/photo.jpg");
        item.setGreetingPhotoUrl("https://cdn.example.com/greeting.jpg");
        item.setMessage("Enjoy!");
        item.setGreetingMessage("Happy birthday, Jane");
        item.setCardType("gift");
        item.setCardSubType("birthday");
        return item;
    }

    static TravelLineItem travelLineItem() {
        TravelLineItem item = new TravelLineItem(350.0, 1, "Flight TLV-JFK", "product-travel", "leg-1", 1, 0);
        populateLineItem(item);
        item.setProductType("travel");
        item.setDepartureDate(date("2024-03-01T06:00:00Z"));
        item.setArrivalDate(date("2024-03-01T18:00:00Z"));
        item.setDepartureCountryCode("IL");
        item.setArrivalCountryCode("US");
        item.setDepartureCity("Tel Aviv");
        item.setArrivalCity("New York");
        item.setDeparturePortCode("TLV");
        item.setArrivalPortCode("JFK");
        item.setTicketClass("economy");
        item.setCarrierName("Example Air");
        item.setCarrierCode("EA");
        item.setTransportMethod("plane");
        return item;
    }

    static RideLineItem rideLineItem() {
        RideLineItem item = new RideLineItem(42.5, 1, "Airport ride", date("2024-03-02T07:00:00Z"), 0, 1);
        populateLineItem(item);
        item.setProductId("product-ride");
        item.setProductType("ride");
        item.setPickupLatitude(40.6413f);
        item.setPickupLongitude(-73.7781f);
        item.setPickupAddress(address("pickup"));
        item.setDropoffDate(date("2024-03-02T08:00:00Z"));
        item.setDropoffLatitude(40.7580f);
        item.setDropoffLongitude(-73.9855f);
        item.setDropoffAddress(address("dropoff"));
        item.setTransportMethod("taxi");
        item.setPriceBy("distance");
        item.setVehicleClass("sedan");
        item.setCarrierName("Example Rides");
        item.setDriverId("driver-1");
        item.setTariff("standard");
        item.setNoteToDriver("terminal 4");
        item.setMeetNGreet("yes");
        item.setCancellationPolicy("free until 1h before");
        item.setAuthorizedPayments(50.0f);
        return item;
    }

    static EventLineItem eventLineItem() {
        EventLineItem item = new EventLineItem(80.0, 2, "Concert ticket", "product-event");
        populateLineItem(item);
        item.setProductType("event");
        item.setSection("A12");
        item.setCity("New York");
        item.setCountryCode("US");
        item.setLatitude(40.7505f);
        item.setLongitude(-73.9934f);
        item.setEventDate(date("2024-04-10T19:30:00Z"));
        return item;
    }

    static AccommodationLineItem accommodationLineItem() {
        AccommodationLineItem item = new AccommodationLineItem(600.0, "Hotel stay", "product-hotel", "New York", "US",
                date("2024-03-01T15:00:00Z"), date("2024-03-04T11:00:00Z"));
        populateLineItem(item);
        item.setProductType("accommodation");
        item.setRoomType("double");
        item.setRating("4");
        item.setNumberOfGuests(2);
        item.setCancellationPolicy("non refundable");
        item.setAccommodationType("hotel");
        return item;
    }

    static void populateLineItem(LineItem item) {
        item.setSku("sku-1");
        item.setColor("blue");
        item.setCondition("new");
        item.setFulfillmentService("manual");
        item.setFulfillmentStatus("unfulfilled");
        item.setGrams(500.0f);
        item.setVariantId("variant-1");
        item.setVariantTitle("Blue / Large");
        item.setVariantInventoryManagement("shopify");
        item.setVendor(new Vendor("Example Vendor", "vendor-1"));
        item.setName("Item name");
        item.setRequiresShipping(true);
        item.setTaxable(true);
        item.setProductExists(true);
        item.setSeller(seller());
        // properties stays at its default []: LineItem.setProperties takes java.util.jar.Attributes, not models.Attributes.
        item.setTaxLines(Collections.singletonList(taxLine()));
        item.setBrand("Example Brand");
        item.setCategory("electronics");
        item.setSubCategory("accessories");
        item.setSize("L");
        item.setDeliveredAt(date("2024-01-20T12:00:00Z"));
        item.setDeliveredTo("shipping_address");
        item.setRegistryType(RegistryType.wedding);
        item.setPolicy(new Policy(true));
        item.setRecipient(recipient());
        item.setReasonToTransfer("gift");
    }

    // Shared building blocks

    static Customer customer() {
        Customer customer = new Customer("jane.doe@example.com", "Jane", "Doe", "customer-1",
                date("2022-05-01T12:00:00Z"), true, 7);
        customer.setUpdatedAt(date("2024-01-10T12:00:00Z"));
        customer.setGroupId("group-1");
        customer.setGroupName("vip");
        customer.setNote("loyal customer");
        customer.setAcceptsMarketing(true);
        customer.setLastOrderId("order-0");
        customer.setLastOrderName("#1000");
        customer.setState("enabled");
        customer.setTotalSpent(1500.5f);
        customer.setTags("vip");
        customer.setDefaultAddress(address("default"));
        customer.setAddress(address("customer"));
        customer.setSocial(Collections.singletonList(socialDetails()));
        customer.setAccountType("premium");
        customer.setLinkedAccounts(2);
        customer.setDocumentId("doc-1");
        customer.setDocumentType("passport");
        customer.setPhone("+15555550100");
        customer.setVerifiedPhone(true);
        customer.setVerifiedPhoneAt(date("2022-05-02T12:00:00Z"));
        customer.setUserName("janedoe");
        customer.setKycDetails(Collections.singletonList(kycDetails()));
        customer.setHasDefaulted(false);
        customer.setDateOfBirth(date("1990-07-04T00:00:00Z"));
        return customer;
    }

    static Address address(String label) {
        Address address = new Address("Jane", "Doe", "1 " + label + " St", "New York", "+15555550100", "United States");
        address.setName("Jane Doe");
        address.setCompany("Example Inc");
        address.setAddress2("Apt 4");
        address.setCountryCode("US");
        address.setProvince("New York");
        address.setProvinceCode("NY");
        address.setZip("10001");
        address.setLatitude(40.7128f);
        address.setLongitude(-74.006f);
        address.setVerifiedPhone(true);
        return address;
    }

    static ClientDetails clientDetails() {
        ClientDetails details = new ClientDetails();
        details.setAcceptLanguage("en-US");
        details.setBrowserIp("203.0.113.10");
        details.setUserAgent("Mozilla/5.0");
        details.setSessionHash("session-hash-1");
        return details;
    }

    static DiscountCode discountCode() {
        DiscountCode code = new DiscountCode(10.0, "SAVE10");
        code.setPolicy(new Policy(false));
        return code;
    }

    static ShippingLine shippingLine() {
        ShippingLine line = new ShippingLine(5.0, "Standard");
        line.setCode("std");
        line.setSource("ups");
        line.setTaxLines(Collections.singletonList(taxLine()));
        return line;
    }

    static TaxLine taxLine() {
        TaxLine line = new TaxLine();
        line.setTitle("VAT");
        line.setPrice(10.0f);
        line.setRate(0.17f);
        return line;
    }

    static Attributes attribute(String name, String value) {
        Attributes attribute = new Attributes();
        attribute.setName(name);
        attribute.setValue(value);
        return attribute;
    }

    static Passenger passenger() {
        Passenger passenger = new Passenger("Jane", "Doe");
        passenger.setDateOfBirth(date("1990-07-04T00:00:00Z"));
        passenger.setNationalityCode("US");
        passenger.setInsuranceType("full");
        passenger.setInsurancePrice(25.0f);
        passenger.setDocumentIssueDate(date("2020-01-01T00:00:00Z"));
        passenger.setDocumentExpirationDate(date("2030-01-01T00:00:00Z"));
        passenger.setPassengerType("adult");
        passenger.setDocumentNumber("P1234567");
        passenger.setDocumentType("passport");
        return passenger;
    }

    static Map<String, Object> additionalData() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("loyalty_tier", "gold");
        data.put("visits", 12);
        return data;
    }

    static CommunicationDetails communicationDetails() {
        CommunicationDetails details = new CommunicationDetails();
        details.setContactType(ContactMethodType.email);
        details.setEmail("support@example.com");
        details.setPhone("+15555550199");
        details.setFacebookAccountUrl("https://facebook.com/example");
        details.setAgentName("Agent Smith");
        details.setAgentId("agent-1");
        details.setConversationDuration(300);
        details.setWaitingDuration(30);
        details.setConversationLanguage("en");
        details.setAgentImpression(AgentImpression.positive);
        details.setOutboundCommunication(false);
        details.setCustomerVerifiedBy("otp");
        details.setAgentCountryCode("US");
        return details;
    }

    static Custom custom() {
        Custom custom = new Custom("app-dom-1");
        custom.setCustomObjTwo("two");
        custom.setCustomObjThree("three");
        custom.setCustomObjFour("four");
        custom.setCustomObjFive("five");
        custom.setCustomObjSix("six");
        custom.setCustomObjSeven("seven");
        return custom;
    }

    static Seller seller() {
        Customer customer = new Customer("seller@example.com", "Sam", "Seller");
        customer.setCreatedAt(date("2021-03-01T00:00:00Z"));
        Seller seller = new Seller(customer);
        seller.setCorrespondence(3);
        seller.setPriceNegotiated(true);
        seller.setStartingPrice(99.5f);
        return seller;
    }

    static Recipient recipient() {
        Recipient recipient = new Recipient();
        recipient.setEmail("recipient@example.com");
        recipient.setPhone("+15555550111");
        recipient.setSocial(socialDetails());
        recipient.setAccountNumber("000987654321");
        recipient.setRoutingNumber("110000000");
        recipient.setCreatedAt(date("2023-01-01T00:00:00Z"));
        recipient.setUpdatedAt(date("2023-06-01T00:00:00Z"));
        recipient.setSelfRecipient(false);
        Wallet wallet = new Wallet();
        wallet.setId("wallet-1");
        recipient.setWallet(wallet);
        return recipient;
    }

    static SocialDetails socialDetails() {
        SocialDetails social = new SocialDetails("facebook", "janedoe", "https://facebook.com/janedoe");
        social.setCommunityScore(80);
        social.setProfilePicture("https://cdn.example.com/jane.jpg");
        social.setEmail("jane.doe@example.com");
        social.setBio("Hello");
        social.setFollowing(100);
        social.setFollowed(200);
        social.setPosts(50);
        return social;
    }

    static KycDetails kycDetails() {
        KycDetails kyc = new KycDetails();
        kyc.setVendorName("Example KYC");
        kyc.setUpdateAt(date("2023-02-01T00:00:00Z"));
        kyc.setKycVerified(true);
        kyc.setKycType("strong");
        return kyc;
    }

    static Date date(String isoInstant) {
        return Date.from(Instant.parse(isoInstant));
    }
}
