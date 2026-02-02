package com.riskified.adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.riskified.models.AuthenticationResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Gson TypeAdapterFactory for AuthenticationResult that handles backward
 * compatibility.
 *
 * <p>
 * This factory creates TypeAdapters that inherit the parent Gson's
 * configuration,
 * including field naming policies and custom type adapters. This ensures
 * compatibility
 * with user-configured Gson instances.
 *
 * <p>
 * Handles field name changes between legacy and current formats:
 * <ul>
 * <li><b>Legacy:</b> tran_status, tran_status_reason, tra_exemption</li>
 * <li><b>Current:</b> trans_status, trans_status_reason, TRA_exemption</li>
 * </ul>
 *
 * <p>
 * During deserialization, legacy field names are automatically mapped to
 * current field names.
 * Serialization always outputs the current format.
 *
 * @since 5.0.1
 */
public class AuthenticationResultAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!type.equals(TypeToken.get(AuthenticationResult.class))) {
            return null;
        }

        return (TypeAdapter<T>) new AuthenticationResultTypeAdapter(gson, this);
    }

    /**
     * TypeAdapter for AuthenticationResult that uses parent Gson configuration.
     */
    private static class AuthenticationResultTypeAdapter extends TypeAdapter<AuthenticationResult> {
        private final TypeAdapter<AuthenticationResult> delegateAdapter;
        private final Set<String> manuallyHandledKeys = new HashSet<String>(Arrays.asList(new String[] { "tran_status",
                "trans_status", "tran_status_reason", "trans_status_reason", "tra_exemption", "TRA_exemption" }));

        AuthenticationResultTypeAdapter(Gson gson, TypeAdapterFactory skipPast) {
            // Get delegate adapter to avoid infinite recursion
            this.delegateAdapter = gson.getDelegateAdapter(skipPast,
                    TypeToken.get(AuthenticationResult.class));
        }

        @Override
        public void write(JsonWriter out, AuthenticationResult value) throws IOException {
            delegateAdapter.write(out, value);
        }

        @Override
        public AuthenticationResult read(JsonReader in) throws IOException {
            JsonElement element = JsonParser.parseReader(in);

            if (element.isJsonNull()) {
                return null;
            }

            if (!element.isJsonObject()) {
                throw new JsonParseException(
                        "Expected object for AuthenticationResult, got: " + element.getClass().getSimpleName());
            }

            JsonObject original = element.getAsJsonObject();
            JsonObject transformed = new JsonObject();

            if (original.has("tran_status")) {
                transformed.add("trans_status", original.get("tran_status"));
            } else if (original.has("trans_status")) {
                transformed.add("trans_status", original.get("trans_status"));
            }

            if (original.has("tran_status_reason")) {
                transformed.add("trans_status_reason", original.get("tran_status_reason"));
            } else if (original.has("trans_status_reason")) {
                transformed.add("trans_status_reason", original.get("trans_status_reason"));
            }

            if (original.has("tra_exemption")) {
                transformed.add("TRA_exemption", original.get("tra_exemption"));
            } else if (original.has("TRA_exemption")) {
                transformed.add("TRA_exemption", original.get("TRA_exemption"));
            }

            for (Map.Entry<String, JsonElement> entry : original.entrySet()) {
                String key = entry.getKey();
                if (!manuallyHandledKeys.contains(key)) {
                    transformed.add(key, entry.getValue());
                }
            }

            return delegateAdapter.fromJsonTree(transformed);
        }
    }
}
