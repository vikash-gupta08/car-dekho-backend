package com.car.dekho.dto;

import java.util.List;
import java.util.Map;

public class CarMatchResponse {
    private boolean success;
    private String message;
    private List<Map<String, Object>> aiSuggestions;
    private List<CarRecommendation> recommendations;

    public CarMatchResponse(boolean success, String message, List<CarRecommendation> recommendations) {
        this.success = success;
        this.message = message;
        this.recommendations = recommendations;
        this.aiSuggestions = null;
    }

    public CarMatchResponse(boolean success, String message, List<Map<String, Object>> aiSuggestions, List<CarRecommendation> recommendations) {
        this.success = success;
        this.message = message;
        this.aiSuggestions = aiSuggestions;
        this.recommendations = recommendations;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, Object>> getAiSuggestions() {
        return aiSuggestions;
    }

    public void setAiSuggestions(List<Map<String, Object>> aiSuggestions) {
        this.aiSuggestions = aiSuggestions;
    }

    public List<CarRecommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<CarRecommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
