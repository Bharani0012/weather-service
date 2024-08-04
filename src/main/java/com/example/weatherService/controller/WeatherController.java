package com.example.weatherService.controller;

import com.example.weatherService.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    //Tomorrow.io api is used to get the weather

    @GetMapping("/weather/by-coordinates")
    public String getWeatherByCoordinates(@RequestParam String coordinates) {
        return weatherService.getWeatherDataByCoordinates(coordinates);
    }

    @GetMapping("/weather/by-city")
    public String getWeatherByCity(@RequestParam String city) {
        return weatherService.getWeatherDataByCity(city);
    }
}
