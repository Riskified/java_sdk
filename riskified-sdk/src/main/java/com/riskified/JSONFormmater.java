package com.riskified;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.*;

public class JSONFormmater {

    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                     .registerTypeAdapter(Date.class, new DateTimeSerializer()).create();
        return gson.toJson(obj);
    }

    public static class DateTimeSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
            return new JsonPrimitive(df.format(src));
        }
    }
}
