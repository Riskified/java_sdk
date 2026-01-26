package com.riskified.adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.riskified.models.Address;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Gson TypeAdapter for List&lt;Address&gt; that handles backward compatibility.
 *
 * <p>
 * Supports two JSON formats:
 * <ul>
 * <li><b>Current:</b> Array of addresses:
 * {@code [{"address1":"..."}, ...]}</li>
 * <li><b>Legacy:</b> Single address object: {@code {"address1":"..."}}</li>
 * </ul>
 *
 * <p>
 * During deserialization, single address objects are automatically wrapped in a
 * list.
 * Serialization always outputs the current array format.
 */
public class AddressListAdapter extends TypeAdapter<List<Address>> {
    private static final Type ADDRESS_LIST_TYPE = new TypeToken<List<Address>>() {
    }.getType();
    private final Gson gson = new GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create();

    @Override
    public void write(JsonWriter out, List<Address> value) throws IOException {
        gson.toJson(value, ADDRESS_LIST_TYPE, out);
    }

    @Override
    public List<Address> read(JsonReader reader) throws IOException, JsonParseException {
        JsonElement element = JsonParser.parseReader(reader);

        if (element.isJsonNull()) {
            return null;
        }

        if (element.isJsonArray()) {
            return gson.fromJson(element, ADDRESS_LIST_TYPE);
        } else if (element.isJsonObject()) {
            return Collections.singletonList(gson.fromJson(element, Address.class));
        }

        throw new JsonParseException(
                "Expected array or object for address field, got: " + element.getClass().getSimpleName());

    }
}
