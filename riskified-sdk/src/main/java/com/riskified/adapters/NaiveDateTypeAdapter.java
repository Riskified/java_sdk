package com.riskified.adapters;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Gson {@link TypeAdapter} for the <em>naive</em> (offset-free) date fields of the Riskified wire
 * contract.
 *
 * <p>
 * The contract carries two date formats and the split between them is per field, not per type:
 * <ul>
 * <li><b>25 offset-bearing fields</b> — order-level and money-related timestamps — serialize as
 * {@code 2026-08-13T10:00:00+00:00}. These are handled by the {@code Date} adapter registered
 * globally in {@code JSONFormater}.</li>
 * <li><b>13 naive fields</b> — line-item, travel and passenger dates — serialize as
 * {@code 2026-08-13T10:00:00}, with <b>no</b> offset. Those thirteen fields carry
 * {@code @JsonAdapter(NaiveDateTypeAdapter.class)}, which takes precedence over the globally
 * registered adapter.</li>
 * </ul>
 *
 * <p>
 * The reference implementation reaches the same split by accident, through two CLR types
 * ({@code DateTimeOffset?} vs {@code DateTime?}) and no configured converter. Java has a single
 * {@link Date} type, so the split has to be declared field by field instead. The authoritative
 * lists live in the contract corpus at {@code docs/flows/01-model-catalog.md} section 3.
 *
 * <p>
 * Both formats are rendered in <b>UTC</b>. Rendering in the JVM default timezone would make the
 * same {@link Date} serialize differently on two machines, and the HMAC is computed over the
 * serialized bytes.
 *
 * <p>
 * On read the adapter is deliberately tolerant: a naive string, an offset-bearing string, a
 * date-only string, or epoch milliseconds are all accepted, because responses are not guaranteed to
 * echo the format the SDK sent.
 *
 * @since 6.4.1
 */
public class NaiveDateTypeAdapter extends TypeAdapter<Date> {

    /** The wire format for the 13 naive fields: no offset, no trailing {@code Z}. */
    public static final String NAIVE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    private static final String[] READ_PATTERNS = {
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            NAIVE_PATTERN,
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd",
    };

    /**
     * A {@link SimpleDateFormat} pinned to UTC. {@code SimpleDateFormat} is not thread safe, so a
     * fresh instance is created per call rather than cached in a field.
     *
     * <p>
     * {@link Locale#US} is passed explicitly: the default locale can select a non-Gregorian
     * calendar (Thai Buddhist, Japanese imperial), which would render a different year for the same
     * instant on a differently configured JVM — the same class of machine-dependence as the
     * timezone.
     *
     * @param pattern the {@link SimpleDateFormat} pattern
     * @return a non-lenient formatter fixed to UTC and to {@link Locale#US}
     */
    public static SimpleDateFormat utcFormat(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        format.setLenient(false);
        return format;
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            // Leaves the deferred field name unwritten while serializeNulls is off, so a null date
            // is omitted exactly as it was before this adapter existed.
            out.nullValue();
            return;
        }
        out.value(utcFormat(NAIVE_PATTERN).format(value));
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        if (token == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        if (token == JsonToken.NUMBER) {
            return new Date(in.nextLong());
        }
        String raw = in.nextString();
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        return parse(raw.trim());
    }

    /**
     * Parses a date written in any of the formats the Riskified API is known to return.
     *
     * @param raw the date string, already trimmed
     * @return the parsed date
     * @throws JsonSyntaxException if no known format matches
     */
    public static Date parse(String raw) {
        for (String pattern : READ_PATTERNS) {
            try {
                return utcFormat(pattern).parse(raw);
            } catch (ParseException ignored) {
                // try the next pattern
            }
        }
        throw new JsonSyntaxException("Unparseable date: \"" + raw + "\"");
    }
}
