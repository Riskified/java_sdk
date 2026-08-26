package com.riskified.models;

import com.riskified.JSONFormater;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Wire-name regression tests for {@link KycDetails}.
 *
 * <p>
 * The Java field is named {@code updateAt}, which {@code LOWER_CASE_WITH_UNDERSCORES} derives as
 * {@code update_at}. The contract key is {@code updated_at} ({@code KycDetails.cs:18}), so the
 * derived name was a key the API ignores and KYC update timestamps were silently not arriving. Same
 * class of failure as {@code dropoff_latitiude}, in the opposite direction: there the SDK
 * "corrected" a wire typo, here it inherited a Java-side one.
 */
public class KycDetailsTest {

    @Test
    public void testUpdatedAtUsesTheContractWireName() {
        KycDetails inputDetails = new KycDetails();
        inputDetails.setUpdateAt(new Date(36000000L));

        String actualJson = JSONFormater.toJson(inputDetails);

        assertTrue("expected the contract key updated_at, got: " + actualJson,
                actualJson.contains("\"updated_at\":\"1970-01-01T10:00:00+00:00\""));
        assertFalse("update_at is the derived name, not the contract key: " + actualJson,
                actualJson.contains("\"update_at\""));
    }

    /** {@code updated_at} is one of the 25 offset-bearing fields, not one of the 13 naive ones. */
    @Test
    public void testUpdatedAtIsOffsetBearing() {
        KycDetails inputDetails = new KycDetails();
        inputDetails.setUpdateAt(new Date(36000000L));

        String actualJson = JSONFormater.toJson(inputDetails);

        assertFalse(actualJson, actualJson.contains("\"updated_at\":\"1970-01-01T10:00:00\""));
    }

    /** The remaining three keys derive correctly; locked so they cannot drift. */
    @Test
    public void testRemainingFieldsUseTheirContractWireNames() {
        KycDetails inputDetails = new KycDetails();
        inputDetails.setVendorName("Acme KYC");
        inputDetails.setKycVerified(true);
        inputDetails.setKycType("full");

        String actualJson = JSONFormater.toJson(inputDetails);

        for (String expectedWireName : Arrays.asList("vendor_name", "kyc_verified", "kyc_type")) {
            assertTrue("missing wire key " + expectedWireName + " in " + actualJson,
                    actualJson.contains("\"" + expectedWireName + "\""));
        }
    }

    /** A customer carrying KYC details serializes the corrected key through the nested path too. */
    @Test
    public void testUpdatedAtSurvivesNestingUnderCustomer() {
        KycDetails inputDetails = new KycDetails();
        inputDetails.setUpdateAt(new Date(36000000L));
        Customer inputCustomer = new Customer("a@b.com", "Ada", "Lovelace");
        inputCustomer.setKycDetails(Arrays.asList(inputDetails));

        String actualJson = JSONFormater.toJson(inputCustomer);

        assertTrue(actualJson, actualJson.contains("\"kyc_details\""));
        assertTrue(actualJson, actualJson.contains("\"updated_at\""));
        assertFalse(actualJson, actualJson.contains("\"update_at\""));
    }

    /** Unset fields stay absent — the always-emitted-defaults boundary is unchanged. */
    @Test
    public void testDefaultKycDetailsSerializesEmpty() {
        String actualJson = JSONFormater.toJson(new KycDetails());

        assertEquals("{}", actualJson);
    }
}
