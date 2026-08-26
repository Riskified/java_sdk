package com.riskified.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * {@link Response} regression tests.
 *
 * <p>
 * {@code getReceived()} returns a primitive {@code int} from a boxed {@link Integer} field, so
 * unboxing raised a {@link NullPointerException} on every response that omits {@code received} —
 * which is every error response. The primitive return type is kept, so existing callers still
 * compile; absence is now reachable through {@code getReceivedOrNull()}.
 */
public class ResponseTest {

    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Test
    public void testGetReceivedDoesNotThrowWhenTheFieldIsAbsent() {
        Response inputResponse = gson.fromJson("{\"decision\":\"approve\"}", Response.class);

        int actualReceived = inputResponse.getReceived();

        assertEquals(0, actualReceived);
    }

    @Test
    public void testGetReceivedOrNullDistinguishesAbsentFromZero() {
        Response inputAbsent = gson.fromJson("{\"decision\":\"approve\"}", Response.class);
        Response inputZero = gson.fromJson("{\"received\":0}", Response.class);

        assertNull(inputAbsent.getReceivedOrNull());
        assertEquals(Integer.valueOf(0), inputZero.getReceivedOrNull());
    }

    @Test
    public void testGetReceivedReturnsThePresentValue() {
        Response inputResponse = gson.fromJson("{\"received\":3}", Response.class);

        assertEquals(3, inputResponse.getReceived());
        assertEquals(Integer.valueOf(3), inputResponse.getReceivedOrNull());
    }

    /** An error-shaped body has no {@code received}; reading it must not throw. */
    @Test
    public void testErrorShapedBodyIsReadableWithoutThrowing() {
        Response inputResponse =
                gson.fromJson("{\"error\":{\"message\":\"order id is missing\"}}", Response.class);

        assertEquals(0, inputResponse.getReceived());
        assertNull(inputResponse.getReceivedOrNull());
        assertEquals("order id is missing", inputResponse.getError().getMessage());
    }

    /** The copy constructor preserves absence rather than turning a missing field into 0. */
    @Test
    public void testCheckoutCopyConstructorPreservesAbsentReceived() {
        CheckoutResponse inputCheckout =
                gson.fromJson("{\"checkout\":{\"id\":\"C-1\"}}", CheckoutResponse.class);

        Response actualResponse = new Response(inputCheckout);

        assertNull(actualResponse.getReceivedOrNull());
        assertEquals(0, actualResponse.getReceived());
    }
}
