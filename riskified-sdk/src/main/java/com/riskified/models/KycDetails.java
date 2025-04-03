package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validation;

import java.util.Date;

public class KycDetails implements IValidated {

    private String vendorName;
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
