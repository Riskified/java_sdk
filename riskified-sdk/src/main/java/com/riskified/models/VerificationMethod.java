package com.riskified.models;

public class VerificationMethod {
        private VerificationMethodTypeEnum type;
        private String value;


        public VerificationMethod(VerificationMethodTypeEnum type, String value){
                this.type = type;
                this.value = value;
        }

        public VerificationMethodTypeEnum getVerificationMethodType(){
                return this.type;
        }

        public void setVerificationMethodType(VerificationMethodTypeEnum verificationMethodType){
                this.type = verificationMethodType;
        }

        public String getValue(){
                return this.value;
        }

        public void setValue(String value){
                this.value = value;
        }

}

