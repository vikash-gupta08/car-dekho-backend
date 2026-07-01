package com.car.dekho.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LLMSuggestionService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;
    private final String llmProvider;

    public LLMSuggestionService(
            ChatClient.Builder chatClientBuilder,
            @Value("${app.llm.provider}") String llmProvider) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = new ObjectMapper();
        this.llmProvider = llmProvider;
    }

    public String generateCarSuggestions(double maxBudget, String primaryUse, String topPriority) {
        String prompt = String.format(
            "You are a car recommendation expert. Based on the following criteria, provide 2-3 brief, practical suggestions " +
            "for car features or characteristics to look for:\n" +
            "- Budget: $%.0f\n" +
            "- Primary Use: %s\n" +
            "- Top Priority: %s\n\n" +
            "Format your response as JSON with a 'suggestions' array containing objects with 'title' and 'description' fields. " +
            "Keep each suggestion concise (1 sentence). Example format:\n" +
            "{\"suggestions\": [{\"title\": \"Safety\", \"description\": \"Look for vehicles with advanced collision avoidance systems.\"}]}",
            maxBudget, primaryUse, topPriority
        );

        String response = chatClient.prompt()
            .user(prompt)
            .call()
            .content();

        return extractJson(response);
    }

    private String extractJson(String response) {
        try {
            if (response.startsWith("{")) {
                return response;
            }

            int start = response.indexOf("{");
            int end = response.lastIndexOf("}");
            if (start != -1 && end != -1) {
                return response.substring(start, end + 1);
            }
        } catch (Exception e) {
            // Return default if parsing fails
        }
        return "{\"suggestions\": [{\"title\": \"Standard\", \"description\": \"Review car specifications for best fit.\"}]}";
    }
}
