package com.riskified;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.riskified.models.BankWirePaymentDetails;
import com.riskified.models.BuyNowPayLaterPaymentDetails;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.IPaymentDetails;
import com.riskified.models.NonChbPaymentDetails;
import com.riskified.models.PaymentType;
import com.riskified.models.RideLineItem;
import com.riskified.models.PaypalPaymentDetails;
import com.riskified.models.StripePaymentDetails;
import com.riskified.models.WalletPaymentDetails;

public class JSONFormater {

	public static String toJson(Object obj) {
        return toJson(obj, false);
    }

    // legacyWireFormat restores the 6.6.x request format for the changes 6.7.0 makes toward the documented API
    // (RiskifiedClientBuilder.setLegacyWireFormat). Each of those changes registers its serializer here based on
    // the flag; until then both formats are the same.
    static String toJson(Object obj, boolean legacyWireFormat) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTimeSerializer())
                .registerTypeAdapterFactory(paymentDetailsSerializer())
                .registerTypeAdapterFactory(rewardPointsSerializer(legacyWireFormat))
                .registerTypeAdapterFactory(rideLineItemSerializer(legacyWireFormat))
                .create();
        return gson.toJson(obj);
    }

    // PaymentType.REWARD_POINTS (deprecated) and REWARDS_POINTS both send the documented "rewards_points". The legacy
    // wire format sends "reward_points", as 6.6.x did. Reading is unchanged: each spelling maps to its own constant.
    // RideLineItem, until 7.0.0:
    // - the amount goes under both the documented authorized_payment and the 6.6.x authorized_payments;
    // - pickup_address and dropoff_address are lists, as documented.
    // The legacy wire format sends what 6.6.x did: only authorized_payments, and each address as a single object
    // (the first one in the list).
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
                        if (legacyWireFormat) {
                            firstAddressOnly(json, "pickup_address");
                            firstAddressOnly(json, "dropoff_address");
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

    private static void firstAddressOnly(JsonObject json, String key) {
        JsonElement addresses = json.remove(key);
        if (addresses != null && addresses.isJsonArray() && addresses.getAsJsonArray().size() > 0) {
            json.add(key, addresses.getAsJsonArray().get(0));
        }
    }

    @SuppressWarnings("deprecation")
    static TypeAdapterFactory rewardPointsSerializer(final boolean legacyWireFormat) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked")
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                if (type.getRawType() != PaymentType.class) {
                    return null;
                }
                final TypeAdapter<PaymentType> delegate = (TypeAdapter<PaymentType>) gson.getDelegateAdapter(this, type);
                return (TypeAdapter<T>) new TypeAdapter<PaymentType>() {
                    @Override
                    public void write(JsonWriter out, PaymentType value) throws IOException {
                        if (value == PaymentType.REWARD_POINTS || value == PaymentType.REWARDS_POINTS) {
                            out.value(legacyWireFormat ? "reward_points" : "rewards_points");
                        } else {
                            delegate.write(out, value);
                        }
                    }

                    @Override
                    public PaymentType read(JsonReader in) throws IOException {
                        return delegate.read(in);
                    }
                };
            }
        };
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
