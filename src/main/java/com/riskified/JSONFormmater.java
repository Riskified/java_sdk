package main.java.com.riskified;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JSONFormmater {

  public static String toJson(Object obj) {
    Gson gson = new GsonBuilder().setPrettyPrinting()
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
