package com.riskified.models;

import java.util.List;

public class PolicyProtect {
    private List<UseCase> useCases;
    private String business_decision;



    public List<UseCase> getUseCases() {
        return useCases;
    }
    public void setUseCases(List<UseCase> useCases) {
        this.useCases = useCases;
    }
    public String getBusiness_decision(){
        return business_decision;
    }
    public void setBusiness_decision(String business_decision){
        this.business_decision = business_decision;
    }
}
