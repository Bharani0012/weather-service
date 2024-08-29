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

    @GetMapping("/weather/get-weather")
    public String getWeatherByCoordinates(@RequestParam String location) {
        return weatherService.getWeatherData(location);
    }

}
