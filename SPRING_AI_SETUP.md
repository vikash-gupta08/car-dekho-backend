# Spring AI Integration for Car Matcher API

This guide explains how to set up and use the Spring AI integration for the `/api/car-matchmaker/match` endpoint.

## Setup Instructions

### 1. Install OpenAI API Key
First, get your OpenAI API key from [https://platform.openai.com/api-keys](https://platform.openai.com/api-keys).

### 2. Configure Environment Variable
Set your OpenAI API key as an environment variable:

**On macOS/Linux:**
```bash
export OPENAI_API_KEY=sk-your-api-key-here
```

**On Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY = "sk-your-api-key-here"
```

Alternatively, add it to your `.env` file or directly in `application.properties`:
```properties
spring.ai.openai.api-key=sk-your-api-key-here
```

### 3. Run the Application
```bash
cd /Users/vikashgupta/Downloads/dekho
./gradlew bootRun
```

## API Usage

### Endpoint: POST `/api/car-matchmaker/match`

The endpoint now includes AI-generated suggestions along with company car recommendations.

**Request Body:**
```json
{
  "maxBudget": 45000,
  "primaryUse": "family",
  "topPriority": "safety"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Matches generated successfully with AI insights",
  "aiSuggestions": [
    {
      "title": "Safety Features",
      "description": "Look for vehicles with advanced collision avoidance and emergency braking systems."
    },
    {
      "title": "Seating Capacity",
      "description": "Consider SUVs or crossovers with spacious seating for family travel."
    }
  ],
  "recommendations": [
    {
      "name": "Apex Family Cruiser",
      "type": "Hybrid Mid-Size SUV",
      "matchScore": 98,
      "matchReason": "Excellent safety features and ratings perfect for family.",
      "topMatch": true,
      "aiInsight": null
    },
    {
      "name": "Family Guardian SUV",
      "type": "Mid-Size SUV",
      "matchScore": 85,
      "matchReason": "Excellent safety features and ratings perfect for family.",
      "topMatch": false,
      "aiInsight": null
    },
    {
      "name": "Terra Ridge 4WD",
      "type": "Full-Size Adventure SUV",
      "matchScore": 78,
      "matchReason": "Excellent safety features and ratings perfect for family.",
      "topMatch": false,
      "aiInsight": null
    }
  ]
}
```

## Features

- **AI-Generated Suggestions**: LLM provides contextual recommendations based on user criteria
- **Company Car Data**: Matches against 10 pre-configured company car models
- **Score-Based Matching**: Intelligent matching algorithm considers:
  - Budget compatibility
  - Primary use case alignment
  - Safety ratings
  - Fuel efficiency
  - Comfort and luxury features
  - Value for money

## Configuration

You can customize the LLM model in `application.properties`:

```properties
# Use GPT-4 for better quality (costs more)
spring.ai.openai.chat.options.model=gpt-4

# Adjust temperature for creativity (0.0 = deterministic, 1.0 = creative)
spring.ai.openai.chat.options.temperature=0.7
```

## Testing the Integration

### Using cURL:
```bash
curl -X POST http://localhost:8081/api/car-matchmaker/match \
  -H "Content-Type: application/json" \
  -d '{
    "maxBudget": 45000,
    "primaryUse": "family",
    "topPriority": "safety"
  }'
```

### Health Check:
```bash
curl http://localhost:8081/api/car-matchmaker/health
```

## Environment Variables

- `OPENAI_API_KEY`: Your OpenAI API key (required)

## Troubleshooting

**Error: "Authentication failed"**
- Verify your `OPENAI_API_KEY` is correct
- Check that the API key has sufficient credits

**Error: "Model not found"**
- Ensure the model specified in `application.properties` is valid (e.g., `gpt-3.5-turbo`, `gpt-4`)

**No AI suggestions in response**
- Check application logs for AI service errors
- Verify OpenAI API is accessible
- The service gracefully falls back to empty suggestions if LLM fails

## Architecture

The integration includes:

1. **LLMSuggestionService** - Handles communication with OpenAI API
2. **CarMatchmakerService** - Orchestrates car matching + AI suggestions
3. **Updated DTOs** - Enhanced response structures with AI insights
4. **Spring AI Configuration** - OpenAI client setup

The `/match` endpoint combines:
- Company car database matching (company data)
- LLM-generated suggestions (AI insights)
