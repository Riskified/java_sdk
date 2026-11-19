package com.riskified;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.riskified.models.BankWirePaymentDetails;
import com.riskified.models.BuyNowPayLaterPaymentDetails;
import com.riskified.models.CheckoutDeniedOrder;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.DigitalLineItem;
import com.riskified.models.IPaymentDetails;
import com.riskified.models.NonChbPaymentDetails;
import com.riskified.models.RideLineItem;
import com.riskified.models.PaypalPaymentDetails;
import com.riskified.models.StripePaymentDetails;
import com.riskified.models.WalletPaymentDetails;

public class JSONFormater {

	public static String toJson(Object obj) {
        return toJson(obj, false);
    }

    // legacyWireFormat restores the 6.5.x request format for the changes 6.6.0 makes toward the documented API
    // (RiskifiedClientBuilder.setLegacyWireFormat). Each of those changes registers its serializer here based on
    // the flag; until then both formats are the same.
    static String toJson(Object obj, boolean legacyWireFormat) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTimeSerializer())
                .registerTypeAdapterFactory(paymentDetailsSerializer())
                .registerTypeAdapterFactory(rideLineItemSerializer(legacyWireFormat))
                .registerTypeAdapterFactory(checkoutDeniedOrderSerializer(legacyWireFormat))
                .registerTypeAdapterFactory(digitalLineItemSerializer(legacyWireFormat))
                .create();
        return gson.toJson(obj);
    }

    // RideLineItem sends the amount under both the documented authorized_payment and the 6.5.x authorized_payments,
    // until 7.0.0. The legacy wire format sends only authorized_payments, as 6.5.x did.
    static TypeAdapterFactory rideLineItemSerializer(final boolean legacyWireFormat) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked")
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                if (type.getRawType() != RideLineItem.class) {
                    return null;
                }
                final TypeAdapter<RideLineItem> delegate = (TypeAdapter<RideLineItem>) gson.getDelegateAdapter(this, type);
                final TypeAdapter<JsonElement> elements = gson.getAdapter(JsonElement.class);
                return (TypeAdapter<T>) new TypeAdapter<RideLineItem>() {
                    @Override
                    public void write(JsonWriter out, RideLineItem value) throws IOException {
                        if (value == null) {
                            out.nullValue();
                            return;
                        }
                        JsonObject json = delegate.toJsonTree(value).getAsJsonObject();
                        JsonElement amount = json.remove("authorized_payment");
                        if (amount != null && !amount.isJsonNull()) {
                            if (!legacyWireFormat) {
                                json.add("authorized_payment", amount);
                            }
                            json.add("authorized_payments", amount);
                        }
                        elements.write(out, json);
                    }

                    @Override
                    public RideLineItem read(JsonReader in) throws IOException {
                        return delegate.read(in);
                    }
                };
            }
        };
    }

    // CheckoutDeniedOrder: the docs put authorization_error inside card, wallet and PayPal payment details, not at the
    // root. A root-level error is copied into each of those payment details that has none of its own, and stays at the
    // root too, until 7.0.0. Only the JSON changes, never the merchant's objects. The legacy wire format sends it at the
    // root only, as 6.5.x did.
    static TypeAdapterFactory checkoutDeniedOrderSerializer(final boolean legacyWireFormat) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked")
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                if (type.getRawType() != CheckoutDeniedOrder.class) {
                    return null;
                }
                final TypeAdapter<CheckoutDeniedOrder> delegate = (TypeAdapter<CheckoutDeniedOrder>) gson.getDelegateAdapter(this, type);
                final TypeAdapter<JsonElement> elements = gson.getAdapter(JsonElement.class);
                return (TypeAdapter<T>) new TypeAdapter<CheckoutDeniedOrder>() {
                    @Override
                    public void write(JsonWriter out, CheckoutDeniedOrder value) throws IOException {
                        if (value == null) {
                            out.nullValue();
                            return;
                        }
                        JsonObject json = delegate.toJsonTree(value).getAsJsonObject();
                        if (!legacyWireFormat) {
                            copyAuthorizationErrorIntoPaymentDetails(json, value.getPaymentDetails());
                        }
                        elements.write(out, json);
                    }

                    @Override
                    public CheckoutDeniedOrder read(JsonReader in) throws IOException {
                        return delegate.read(in);
                    }
                };
            }
        };
    }

    // The JSON array and the order's list have the same order, so element i is paymentDetails.get(i).
    private static void copyAuthorizationErrorIntoPaymentDetails(JsonObject order, List<? extends IPaymentDetails> paymentDetails) {
        JsonElement error = order.get("authorization_error");
        JsonElement details = order.get("payment_details");
        if (error == null || error.isJsonNull() || details == null || !details.isJsonArray() || paymentDetails == null) {
            return;
        }
        JsonArray array = details.getAsJsonArray();
        for (int i = 0; i < array.size() && i < paymentDetails.size(); i++) {
            IPaymentDetails paymentDetail = paymentDetails.get(i);
            boolean documented = paymentDetail instanceof CreditCardPaymentDetails
                    || paymentDetail instanceof WalletPaymentDetails
                    || paymentDetail instanceof PaypalPaymentDetails;
            JsonElement element = array.get(i);
            if (!documented || !element.isJsonObject()) {
                continue;
            }
            // toJsonTree keeps unset fields as JSON nulls (dropped only when written), so null means "none of its own".
            JsonElement own = element.getAsJsonObject().get("authorization_error");
            if (own == null || own.isJsonNull()) {
                element.getAsJsonObject().add("authorization_error", error.deepCopy());
            }
        }
    }

    // DigitalLineItem: the docs define only card_type. When only one of card_type and card_sub_type is set, its value is
    // sent under both keys; when both are set, each keeps its own. The legacy wire format sends them as set, as 6.5.x did.
    static TypeAdapterFactory digitalLineItemSerializer(final boolean legacyWireFormat) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked")
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                if (legacyWireFormat || type.getRawType() != DigitalLineItem.class) {
                    return null;
                }
                final TypeAdapter<DigitalLineItem> delegate = (TypeAdapter<DigitalLineItem>) gson.getDelegateAdapter(this, type);
                final TypeAdapter<JsonElement> elements = gson.getAdapter(JsonElement.class);
                return (TypeAdapter<T>) new TypeAdapter<DigitalLineItem>() {
                    @Override
                    public void write(JsonWriter out, DigitalLineItem value) throws IOException {
                        if (value == null) {
                            out.nullValue();
                            return;
                        }
                        JsonObject json = delegate.toJsonTree(value).getAsJsonObject();
                        mirror(json, "card_type", "card_sub_type");
                        mirror(json, "card_sub_type", "card_type");
                        elements.write(out, json);
                    }

                    @Override
                    public DigitalLineItem read(JsonReader in) throws IOException {
                        return delegate.read(in);
                    }
                };
            }
        };
    }

    // Copies from's value to to when to is unset. toJsonTree keeps unset fields as JSON nulls.
    private static void mirror(JsonObject json, String from, String to) {
        JsonElement source = json.get(from);
        JsonElement target = json.get(to);
        if (source != null && !source.isJsonNull() && (target == null || target.isJsonNull())) {
            json.add(to, source.deepCopy());
        }
    }

    public static class DateTimeSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            return new JsonPrimitive(df.format(src));
        }
    }
    public static RuntimeTypeAdapterFactory paymentDetailsSerializer() {
        return RuntimeTypeAdapterFactory
                .of(IPaymentDetails.class, "method")
                .registerSubtype(PaypalPaymentDetails.class, "paypal")
                .registerSubtype(StripePaymentDetails.class, "stripe")
                .registerSubtype(CreditCardPaymentDetails.class, "credit_card")
                .registerSubtype(BankWirePaymentDetails.class, "bank_wire")
                .registerSubtype(WalletPaymentDetails.class, "digital_wallet")
                .registerSubtype(BuyNowPayLaterPaymentDetails.class, "bnpl")
                .registerSubtype(NonChbPaymentDetails.class, "non_chb");
    }
   
}
