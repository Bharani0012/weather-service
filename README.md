
# Weather Service

This project is a simple weather service application built with Spring Boot. It provides weather information based on geographical coordinates using the WeatherStack API.

## Features

- Fetch current weather data by location.
- Handles errors and exceptions gracefully.
- Formats JSON responses for readability.

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Web (RestTemplate)
- JSON (org.json)
- WeatherStack API

### Setup

1. **Clone the repository:** 
    git clone https://github.com/yourusername/weather-service.git
    cd weather-service
    Update API Key:
Replace the access_key in WeatherService with your WeatherStack API key.

   private static final String WEATHER_STACK_API_URL = "http://api.weatherstack.com/current?access_key=your_api_key_here&query=%s";

Build the project:

bash

mvn clean install

Run the application:

bash

    mvn spring-boot:run

API Endpoints
Get Weather by Coordinates

    URL: /weather/get-weather

    Method: GET

    Query Parameters:
        location (String) - The location name or coordinates (e.g., "Lat 11.39 and Lon 77.67").

    ###Example Request:
        GET http://localhost:8080/weather/get-weather?location=Lat 11.39 and Lon 77.67

Response:

    Returns formatted JSON containing current weather details.
