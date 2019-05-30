package com.riskified;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.*;
import com.riskified.models.BankWirePaymentDetails;
import com.riskified.models.CreditCardPaymentDetails;
import com.riskified.models.IPaymentDetails;
import com.riskified.models.PaypalPaymentDetails;
import sun.security.x509.IPAddressName;

public class JSONFormater {

	public static String toJson(Object obj) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTimeSerializer())
                .registerTypeAdapterFactory(paymentDetailsSerializer())
                .create();
        return gson.toJson(obj);
    }

    public static class DateTimeSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX");
            return new JsonPrimitive(df.format(src));
        }
    }
    public static RuntimeTypeAdapterFactory paymentDetailsSerializer() {
        return RuntimeTypeAdapterFactory
                .of(IPaymentDetails.class, "method")
                .registerSubtype(PaypalPaymentDetails.class, "paypal")
                .registerSubtype(CreditCardPaymentDetails.class, "credit_card")
                .registerSubtype(BankWirePaymentDetails.class, "bank_wire");
    }
    
    
}
