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
 * Gson TypeAdapterFactory for List&lt;Address&gt; that handles backward compatibility.
 *
 * <p>
 * This factory creates TypeAdapters that inherit the parent Gson's configuration,
 * including field naming policies and custom type adapters. This ensures compatibility
 * with user-configured Gson instances.
 *
 * <p>
 * Supports two JSON formats:
 * <ul>
 * <li><b>Current:</b> Array of addresses: {@code [{"address1":"..."}, ...]}</li>
 * <li><b>Legacy:</b> Single address object: {@code {"address1":"..."}}</li>
 * </ul>
 *
 * <p>
 * During deserialization, single address objects are automatically wrapped in a list.
 * Serialization always outputs the current array format.
 *
 * @since 5.1.0
 */
public class AddressListAdapterFactory implements TypeAdapterFactory {

    private static final Type ADDRESS_LIST_TYPE = new TypeToken<List<Address>>() {
    }.getType();

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        // Only handle List<Address> type
        if (!type.equals(TypeToken.get(ADDRESS_LIST_TYPE))) {
            return null;
        }

        // Return adapter that uses parent Gson configuration
        return (TypeAdapter<T>) new AddressListTypeAdapter(gson);
    }

    /**
     * TypeAdapter for List&lt;Address&gt; that uses parent Gson configuration.
     */
    private static class AddressListTypeAdapter extends TypeAdapter<List<Address>> {
        private final Gson gson;

        AddressListTypeAdapter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public void write(JsonWriter out, List<Address> value) throws IOException {
            gson.toJson(value, ADDRESS_LIST_TYPE, out);
        }

        @Override
        public List<Address> read(JsonReader in) throws IOException {
            JsonElement element = JsonParser.parseReader(in);

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
}
