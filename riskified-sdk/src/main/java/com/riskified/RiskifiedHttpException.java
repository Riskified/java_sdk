package com.riskified;

import com.riskified.models.Error;
import org.apache.http.client.HttpResponseException;

/**
 * A non-2xx response from the Riskified API, with the transport facts preserved as structured
 * fields rather than flattened into a message string.
 *
 * <p>
 * It extends {@link HttpResponseException}, so every existing {@code catch (HttpResponseException)}
 * keeps working and {@link #getStatusCode()} keeps returning what it always did. What is new is
 * that the real status now survives: the client used to rewrite <b>every</b> unmatched status to
 * {@code 500 "Contact Riskified support"} and discard the body, which made a 502, a 503 and a
 * genuine 500 indistinguishable to a caller trying to decide whether to retry.
 *
 * <p>
 * {@link #getResponseBody()} carries the raw body as a separate field so that logging can redact
 * it. Response bodies can contain customer PII, and a message string is the one place logging
 * cannot reach — a defect the reference implementation has
 * ({@code Riskified.SDK/Utils/HttpUtils.cs:224}) and that this SDK should not deepen.
 *
 * <p>
 * The API returns seven mutually incompatible error body shapes across its six specs
 * ({@code docs/flows/00-shared-contract.md} section 6). {@link #getError()} is populated only when
 * the body matched the common {@code {"error": {"message": ...}}} shape; for the other six it is
 * {@code null} and the body is still available verbatim. It is never a reason to fail.
 *
 * @since 6.4.1
 */
public class RiskifiedHttpException extends HttpResponseException {

    private static final long serialVersionUID = 1L;

    private final String statusText;
    private final String responseBody;
    private final Error error;

    /**
     * @param statusCode   the real HTTP status code, never a substituted one
     * @param statusText   the HTTP reason phrase, may be {@code null}
     * @param responseBody the raw response body, may be {@code null}
     * @param error        the parsed error object when the body matched the common shape, else
     *                     {@code null}
     * @param message      the exception message
     */
    public RiskifiedHttpException(int statusCode, String statusText, String responseBody, Error error,
            String message) {
        super(statusCode, message);
        this.statusText = statusText;
        this.responseBody = responseBody;
        this.error = error;
    }

    /**
     * @return the HTTP reason phrase, or {@code null} if the response carried none
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * @return the raw response body, or {@code null} if the response had none. Attach this to a
     *         structured log field, not to a message string — it can contain customer PII.
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * @return the parsed error, or {@code null} when the body did not match the common
     *         {@code {"error": {"message": ...}}} shape. A {@code null} here says nothing about
     *         whether the request failed.
     */
    public Error getError() {
        return error;
    }
}
