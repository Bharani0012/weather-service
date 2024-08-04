package com.example.weatherService.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class WeatherService {

    private static final String TOMORROW_IO_API_URL = "https://api.tomorrow.io/v4/weather/forecast?location=%s&apikey=WpMVxUHIYj4zOmUiGQfNOdTXBnFKGc1o";
    private static final String OPENCAGE_API_URL = "https://api.opencagedata.com/geocode/v1/json?q=%s&key=6639785137fa4c16adc0975c9ac2ea31";

    public String getWeatherDataByCoordinates(String coordinates) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(TOMORROW_IO_API_URL, coordinates);
        String response = restTemplate.getForObject(url, String.class);
        return parseWeatherData(response);
    }

    public String getWeatherDataByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();

        // Get coordinates from OpenCage
        String geoCodingUrl = String.format(OPENCAGE_API_URL, city);
        String geoCodingResponse = restTemplate.getForObject(geoCodingUrl, String.class);
        JSONObject geoData = new JSONObject(geoCodingResponse);

        // Extract coordinates
        double lat = geoData.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lat");
        double lon = geoData.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getDouble("lng");

        // Get weather data from Tomorrow.io
        String location = lat + "," + lon;
        String weatherUrl = String.format(TOMORROW_IO_API_URL, location);
        String weatherResponse = restTemplate.getForObject(weatherUrl, String.class);

        return parseWeatherData(weatherResponse);
    }



    private @NotNull String parseWeatherData(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray hourly = jsonResponse.getJSONObject("timelines").getJSONArray("hourly");

        StringBuilder weatherSummary = new StringBuilder();
        int forecastPeriod = 24;
        int intervalHours = 1;
        int hoursCollected = 0;

        ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime currentTime = ZonedDateTime.now(istZoneId);
        long currentTimeMillis = currentTime.toEpochSecond();

        // Define the date-time formatter for parsing the ISO 8601 time strings
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC"));

        // Loop through hourly data
        for (int i = 0; i < hourly.length(); i++) {
            JSONObject hourData = hourly.getJSONObject(i);
            String time = hourData.getString("time");
            ZonedDateTime forecastTime = ZonedDateTime.parse(time, formatter.withZone(istZoneId));
            long forecastTimeMillis = forecastTime.toEpochSecond();

            // Check if the forecast time is after the current time
            if (forecastTimeMillis >= currentTimeMillis && hoursCollected < forecastPeriod) {
                JSONObject values = hourData.getJSONObject("values");

                double temperature = values.getDouble("temperature");
                double humidity = values.getDouble("humidity");
                double windSpeed = values.getDouble("windSpeed");
                double cloudCover = values.getDouble("cloudCover");
                windSpeed=windSpeed*3.6;

                // Append the data for each hour
                weatherSummary.append(String.format(
                        "Time: %s IST\nTemperature: %.2f°C\nHumidity: %.2f%%\nWind Speed: %.2f Km/hr\nCloud Cover: %.2f%%\n\n",
                        forecastTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), temperature, humidity, windSpeed, cloudCover
                ));

                hoursCollected++;
            }

            if (hoursCollected >= forecastPeriod) {
                break;
            }
        }

        return weatherSummary.toString();
    }

}
