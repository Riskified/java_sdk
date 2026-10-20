package com.riskified.models;

import com.google.gson.annotations.SerializedName;
import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validation;

import java.util.Date;

public class KycDetails implements IValidated {

    private String vendorName;
    // The Java field name is updateAt, which LOWER_CASE_WITH_UNDERSCORES derives as "update_at".
    // The contract key is "updated_at" (KycDetails.cs:18), so the derived name was a key the API
    // ignores: KYC update timestamps were silently not arriving. Pinned explicitly rather than
    // renaming the field, which would break every caller of getUpdateAt()/setUpdateAt().
    @SerializedName("updated_at")
    private Date updateAt;
    private Boolean kyc_verified;
    private String kycType;


    public KycDetails() {
    }

    public void validate(Validation validationType)
            throws FieldBadFormatException {
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getKycVerified() {
        return kyc_verified;
    }

    public void setKycVerified(Boolean kyc_verified) {
        this.kyc_verified = kyc_verified;
    }

    public String getKycType() {
        return kycType;
    }

    public void setKycType(String kycType) {
        this.kycType = kycType;
    }


}
