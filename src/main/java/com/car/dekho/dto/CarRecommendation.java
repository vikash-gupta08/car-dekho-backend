package com.car.dekho.dto;

public class CarRecommendation {
    private String name;
    private String type;
    private int matchScore;
    private String matchReason;
    private boolean topMatch;
    private String aiInsight;

    public CarRecommendation(String name, String type, int matchScore, String matchReason, boolean topMatch) {
        this.name = name;
        this.type = type;
        this.matchScore = matchScore;
        this.matchReason = matchReason;
        this.topMatch = topMatch;
        this.aiInsight = null;
    }

    public CarRecommendation(String name, String type, int matchScore, String matchReason, boolean topMatch, String aiInsight) {
        this.name = name;
        this.type = type;
        this.matchScore = matchScore;
        this.matchReason = matchReason;
        this.topMatch = topMatch;
        this.aiInsight = aiInsight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public String getMatchReason() {
        return matchReason;
    }

    public void setMatchReason(String matchReason) {
        this.matchReason = matchReason;
    }

    public boolean isTopMatch() {
        return topMatch;
    }

    public void setTopMatch(boolean topMatch) {
        this.topMatch = topMatch;
    }

    public String getAiInsight() {
        return aiInsight;
    }

    public void setAiInsight(String aiInsight) {
        this.aiInsight = aiInsight;
    }
}
