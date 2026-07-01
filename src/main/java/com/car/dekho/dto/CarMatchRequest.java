package com.car.dekho.dto;

public class CarMatchRequest {
    private double maxBudget;
    private String primaryUse;
    private String topPriority;

    public CarMatchRequest() {}

    public CarMatchRequest(double maxBudget, String primaryUse, String topPriority) {
        this.maxBudget = maxBudget;
        this.primaryUse = primaryUse;
        this.topPriority = topPriority;
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public String getPrimaryUse() {
        return primaryUse;
    }

    public void setPrimaryUse(String primaryUse) {
        this.primaryUse = primaryUse;
    }

    public String getTopPriority() {
        return topPriority;
    }

    public void setTopPriority(String topPriority) {
        this.topPriority = topPriority;
    }
}
