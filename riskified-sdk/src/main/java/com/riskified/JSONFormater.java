package com.riskified;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

import com.google.gson.*;
import com.riskified.adapters.NaiveDateTypeAdapter;
import com.riskified.adapters.PaymentDetailsAdapterFactory;
import com.riskified.models.BankWirePaymentDetails;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.IPaymentDetails;
import com.riskified.models.PaypalPaymentDetails;
import com.riskified.models.StripePaymentDetails;
import com.riskified.models.WalletPaymentDetails;

public class JSONFormater {

	public static String toJson(Object obj) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTimeSerializer())
                .registerTypeAdapterFactory(new PaymentDetailsAdapterFactory())
                .create();
        return gson.toJson(obj);
    }

    /**
     * Serializer for the 25 <em>offset-bearing</em> date fields of the wire contract — every
     * {@link Date} that is not explicitly annotated with
     * {@code @JsonAdapter(NaiveDateTypeAdapter.class)}.
     *
     * <p>
     * Two properties matter here:
     * <ul>
     * <li><b>UTC, not the JVM default timezone.</b> The previous implementation used the default
     * timezone, so the same {@link Date} serialized differently on two machines — and since the
     * HMAC is computed over the serialized bytes, so did the signature.</li>
     * <li><b>The offset is written as {@code +00:00}, not {@code Z}.</b> Both are valid ISO 8601 and
     * denote the same instant, but the reference implementation renders a zero-offset
     * {@code DateTimeOffset} as {@code +00:00}, and byte parity with the reference is the point.
     * The literal is safe because the formatter is pinned to UTC one line below.</li>
     * </ul>
     *
     * @see NaiveDateTypeAdapter for the 13 fields that must carry no offset at all
     */
    public static class DateTimeSerializer implements JsonSerializer<Date> {
        /** Zero offset spelled out, to match how .NET renders a UTC {@code DateTimeOffset}. */
        public static final String OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'+00:00'";

        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            DateFormat df = NaiveDateTypeAdapter.utcFormat(OFFSET_PATTERN);
            return new JsonPrimitive(df.format(src));
        }
    }

    /**
     * The polymorphic {@code payment_details} adapter that this SDK used to register.
     *
     * <p>
     * <b>No longer used, and deliberately so.</b> It injected a {@code "method"} key into every
     * {@code payment_details} object with values ({@code credit_card}, {@code bank_wire},
     * {@code digital_wallet}) that disagreed with the {@code payment_type} value emitted alongside
     * it. The wire contract carries <b>no</b> type discriminator on {@code payment_details}: the
     * variant is expressed by which keys are present, with the constant {@code payment_type} acting
     * as the de-facto discriminator. No other SDK in the fleet emitted {@code method}.
     *
     * <p>
     * Gson dispatches on the runtime type of each element of a {@code List<IPaymentDetails>} on its
     * own, so nothing is lost by dropping the factory. It is retained here only so that callers who
     * built their own {@code Gson} against it still compile; do not register it.
     *
     * @return the legacy discriminator-injecting factory
     * @deprecated the {@code method} key it emits is not part of the Riskified wire contract.
     */
    @Deprecated
    public static RuntimeTypeAdapterFactory paymentDetailsSerializer() {
        return RuntimeTypeAdapterFactory
                .of(IPaymentDetails.class, "method")
                .registerSubtype(PaypalPaymentDetails.class, "paypal")
                .registerSubtype(StripePaymentDetails.class, "stripe")
                .registerSubtype(CreditCardPaymentDetails.class, "credit_card")
                .registerSubtype(BankWirePaymentDetails.class, "bank_wire")
                .registerSubtype(WalletPaymentDetails.class, "digital_wallet");
    }

}
