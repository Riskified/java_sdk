package com.riskified.adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.riskified.models.AuthenticationResult;

import java.io.IOException;
import java.util.Map;

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
 * <li><b>Legacy:</b> tran_status, tran_status_reason, tra_exemption (or their camelCase equivalents)</li>
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

            for (Map.Entry<String, JsonElement> entry : original.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();

                if (key.equals("tranStatus") || key.equals("tran_status")) {
                    transformed.add("trans_status", value);
                }
                else if (key.equals("tranStatusReason") || key.equals("tran_status_reason")) {
                    transformed.add("trans_status_reason", value);
                }
                else if (key.equals("threeDChallenge")) {
                    transformed.add("three_d_challenge", value);
                }
                else if (key.equals("traExemption") || key.equals("tra_exemption")) {
                    transformed.add("TRA_exemption", value);
                }
                else if (key.equals("liabilityShift")) {
                    transformed.add("liability_shift", value);
                }
                else if (key.equals("createdAt")) {
                    transformed.add("created_at", value);
                }
                else {
                    transformed.add(key, value);
                }
            }

            return delegateAdapter.fromJsonTree(transformed);
        }
    }
}
