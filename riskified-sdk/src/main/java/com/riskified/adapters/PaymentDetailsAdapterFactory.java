package com.riskified.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.riskified.models.IPaymentDetails;

import java.io.IOException;

/**
 * Serializes each element of a {@code payment_details} array as its own runtime type, and emits
 * <b>no</b> type discriminator.
 *
 * <p>
 * {@code payment_details} carries no discriminator on the wire: the variant is expressed by which
 * keys are present, with the constant {@code payment_type} acting as the de-facto discriminator
 * ({@code docs/flows/01-model-catalog.md} section 6). This SDK previously used a
 * {@code RuntimeTypeAdapterFactory} for the dispatch, which injected a {@code "method"} key with
 * values that disagreed with the {@code payment_type} emitted alongside it.
 *
 * <p>
 * <b>Why a factory is still needed at all.</b> Gson normally dispatches on an element's runtime type
 * by itself, via {@code TypeAdapterRuntimeTypeWrapper}. It does not here: {@code BaseOrder} and
 * {@code DecisionOrder} declare the field as {@code List<? extends IPaymentDetails>}, and Gson's
 * runtime-type promotion fires only when the declared element type is a {@code Class} — a
 * {@code WildcardType} is left alone. Without this factory the reflective adapter for the bare
 * interface runs instead and every element serializes as {@code {}}: total, silent loss of the
 * payment details. Dropping the old factory without replacing it is therefore not a safe no-op.
 *
 * <p>
 * Only the interface itself is matched, so the delegate lookup for the concrete class cannot re-enter
 * this factory.
 *
 * @since 6.4.1
 */
public class PaymentDetailsAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        // Matched by raw type on purpose: the declared element type is a WildcardType
        // (? extends IPaymentDetails), not IPaymentDetails.class, so TypeToken equality misses it.
        if (type.getRawType() != IPaymentDetails.class) {
            return null;
        }
        return (TypeAdapter<T>) new PaymentDetailsTypeAdapter(gson);
    }

    private static class PaymentDetailsTypeAdapter extends TypeAdapter<IPaymentDetails> {

        private final Gson gson;

        PaymentDetailsTypeAdapter(Gson gson) {
            this.gson = gson;
        }

        @Override
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public void write(JsonWriter out, IPaymentDetails value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            TypeAdapter runtimeAdapter = gson.getAdapter(TypeToken.get(value.getClass()));
            runtimeAdapter.write(out, value);
        }

        @Override
        public IPaymentDetails read(JsonReader in) throws IOException {
            // No response in the contract carries payment_details, and with no discriminator on the
            // wire the variant is not recoverable from the keys alone. Deserialize the concrete
            // class instead of the interface.
            throw new JsonParseException("payment_details cannot be deserialized through the "
                    + "IPaymentDetails interface: it carries no type discriminator. Deserialize a "
                    + "concrete payment-details class instead.");
        }
    }
}
