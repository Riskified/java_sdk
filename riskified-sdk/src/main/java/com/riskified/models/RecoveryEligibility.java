package com.riskified.models;
import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RecoveryEligibility {
    private String id;
    private String status;
    private String verifiedAt;
    private VerificationMethod verificationMethod;

    private String challengeType;

    private VerificationSessionDetails verificationSessionDetails;

    public RecoveryEligibility(String id, String status, String verifiedAt, VerificationMethod verificationMethod){
        this.id = id;
        this.status = status;
        this.verifiedAt = verifiedAt;
        this.verificationMethod = verificationMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getVerifiedAt(){
        return verifiedAt;
    }

    public void setVerifiedAt(ZonedDateTime verifiedAt){
        this.verifiedAt = verifiedAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public VerificationMethod getVerificationMethod(){
        return verificationMethod;
    }

    public void setVerificationMethod(VerificationMethod verificationMethod){
        this.verificationMethod = verificationMethod;
    }

    public VerificationSessionDetails getVerificationSessionDetails() {
        return verificationSessionDetails;
    }

    public void setVerificationSessionDetails(VerificationSessionDetails verificationSessionDetails){
        this.verificationSessionDetails = verificationSessionDetails;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(String challengeType){
        this.challengeType = challengeType;
    }
}


