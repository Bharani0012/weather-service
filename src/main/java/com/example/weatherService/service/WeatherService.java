package com.example.weatherService.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class WeatherService {

    private static final String WEATHER_STACK_API_URL = "http://api.weatherstack.com/current?access_key=8fda004df3ef118b8bc5577b961cb439&query=%s";

    public String getWeatherData(String location) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(WEATHER_STACK_API_URL, location);

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            HttpStatusCode statusCode = responseEntity.getStatusCode();
            
            HttpStatus httpStatus = HttpStatus.resolve(statusCode.value());
            
            if (httpStatus == HttpStatus.OK) {
                String responseBody = responseEntity.getBody();
                
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse.toString(4); // Indent with 4 spaces

            } else {
                return String.format("Error: Received HTTP %d %s from WeatherStack API", httpStatus.value(), httpStatus.getReasonPhrase());
            }

        } catch (HttpClientErrorException e) {
            return String.format("HTTP Error: %s - %s", e.getStatusCode(), e.getStatusText());

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
