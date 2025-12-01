package com.riskified.models;

public class UseCase {
    private String useCase;
    private String decision;
    private Double score;

    public String getUseCase() {
        return useCase;
    }
    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }
    public String getDecision() {
        return decision;
    }
    public void setDecision(String decision) {
        this.decision = decision;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
}
