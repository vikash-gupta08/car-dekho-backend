package com.car.dekho.service;

import com.car.dekho.repo.CarRepository;
import com.car.dekho.dto.CarMatchRequest;
import com.car.dekho.dto.CarMatchResponse;
import com.car.dekho.dto.CarRecommendation;
import com.car.dekho.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarMatchmakerService {

    @Autowired
    private LLMSuggestionService llmSuggestionService;

    @Autowired
    private CarRepository carRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Car> getInternalCarDatabase() {
        return carRepository.findAll();
    }

    public CarMatchResponse matchCars(CarMatchRequest request) {
        List<Car> carDatabase = getInternalCarDatabase();
        
        List<CarRecommendation> recommendations = carDatabase.stream()
            .map(car -> new CarRecommendation(
                car.getName(),
                car.getType(),
                calculateMatchScore(car, request),
                generateMatchReason(car, request),
                false
            ))
            .sorted((a, b) -> Integer.compare(b.getMatchScore(), a.getMatchScore()))
            .limit(5)
            .collect(Collectors.toList());

        if (!recommendations.isEmpty()) {
            recommendations.get(0).setTopMatch(true);
        }

        List<Map<String, Object>> aiSuggestions = null ; //getAISuggestions(request);

        return new CarMatchResponse(true, "Matches generated successfully with AI insights", aiSuggestions, recommendations);
    }

    private List<Map<String, Object>> getAISuggestions(CarMatchRequest request) {
        try {
            String suggestionsJson = null;
            suggestionsJson =    llmSuggestionService.generateCarSuggestions(
                request.getMaxBudget(),
                request.getPrimaryUse(),
                request.getTopPriority()
            );

            JsonNode rootNode = objectMapper.readTree(suggestionsJson);
            List<Map<String, Object>> suggestions = new ArrayList<>();

            if (rootNode.has("suggestions")) {
                rootNode.get("suggestions").forEach(suggestion -> {
                    Map<String, Object> suggestionMap = new LinkedHashMap<>();
                    if (suggestion.has("title")) {
                        suggestionMap.put("title", suggestion.get("title").asText());
                    }
                    if (suggestion.has("description")) {
                        suggestionMap.put("description", suggestion.get("description").asText());
                    }
                    if (!suggestionMap.isEmpty()) {
                        suggestions.add(suggestionMap);
                    }
                });
            }
            return suggestions;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private int calculateMatchScore(Car car, CarMatchRequest request) {
        int score = 100;

        if (car.getPrice() > request.getMaxBudget()) {
            score -= 15;
        }

        if (car.getPrice() > request.getMaxBudget() * 0.9) {
            score -= 5;
        }

        String primaryUse = request.getPrimaryUse().toLowerCase();
        String carUseCategory = car.getPrimaryUseCategory().toLowerCase();
        
        if (carUseCategory.contains(primaryUse) || primaryUse.contains(carUseCategory)) {
            score += 5;
        }

        String topPriority = request.getTopPriority().toLowerCase();
        
        if (topPriority.contains("safety")) {
            if (car.getSafetyRating().equals("5-star")) {
                score += 10;
            } else if (car.getSafetyRating().equals("4-star")) {
                score += 5;
            }
        }

        if (topPriority.contains("efficiency")) {
            if (car.getFuelType().equals("Electric")) {
                score += 8;
            } else if (car.getFuelType().equals("Hybrid")) {
                score += 5;
            }
            score += (int) (car.getFuelEfficiency() / 5);
        }

        if (topPriority.contains("comfort") || topPriority.contains("luxury")) {
            if (car.isLuxuryFeatures()) {
                score += 8;
            }
        }

        if (topPriority.contains("budget")) {
            score += (int) ((request.getMaxBudget() - car.getPrice()) / 1000);
        }

        return Math.max(0, Math.min(100, score));
    }

    private String generateMatchReason(Car car, CarMatchRequest request) {
        String primaryUse = request.getPrimaryUse();
        String topPriority = request.getTopPriority();

        if (topPriority.equalsIgnoreCase("safety")) {
            return String.format("Excellent safety features and ratings perfect for %s.", primaryUse);
        } else if (topPriority.equalsIgnoreCase("efficiency")) {
            return String.format("Great fuel efficiency and %s technology for cost savings.", car.getFuelType());
        } else if (topPriority.equalsIgnoreCase("comfort")) {
            return String.format("Premium comfort and luxury features ideal for %s use.", primaryUse);
        } else if (topPriority.equalsIgnoreCase("budget")) {
            return String.format("Best value option within your $%.0f budget for %s.", request.getMaxBudget(), primaryUse);
        }
        
        return String.format("Great fit for %s with %s priority consideration.", primaryUse, topPriority);
    }
}
