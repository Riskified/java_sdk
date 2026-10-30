package com.riskified;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.*;
import com.riskified.models.BankWirePaymentDetails;
import com.riskified.models.BuyNowPayLaterPaymentDetails;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.IPaymentDetails;
import com.riskified.models.NonChbPaymentDetails;
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
                .create();
        return gson.toJson(obj);
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
