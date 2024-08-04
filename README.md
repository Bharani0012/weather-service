Weather Service

A Spring Boot application that provides weather forecasts based on user input. You can fetch weather data using either coordinates or city names. The application uses Tomorrow.io for weather data and OpenCage for geocoding city names.
Features

    Weather by Coordinates: Fetch weather data using latitude and longitude.
    Weather by City: Fetch weather data by converting city names to coordinates and then using the Tomorrow.io API.

API Endpoints
1. Get Weather by Coordinates

    Endpoint: /weather/by-coordinates
    Method: GET
    Parameters:
        coordinates (required): Coordinates in the format latitude,longitude (e.g., 42.3478,-71.0466).
    Response: Weather data for the specified coordinates.

Example Request:

 ```

curl --request GET --url 'http://localhost:8080/weather/by-coordinates?coordinates=42.3478,-71.0466'

2. Get Weather by City

    Endpoint: /weather/by-city
    Method: GET
    Parameters:
        city (required): Name of the city (e.g., New York).
    Response: Weather data for the specified city.

Example Request:

 ```

curl --request GET --url 'http://localhost:8080/weather/by-city?city=Chennai'

Setup and Installation
Prerequisites

    Java 17 or later
    Maven for building the project

Steps

    Clone the Repository:

    git clone <repository-url>

Navigate to the Project Directory:

 ```

cd weather-service

Build the Project:

  mvn clean install

Run the Application:

   mvn spring-boot:run

    The application will start and be accessible at http://localhost:8080.

Configuration

    Tomorrow.io API Key: Replace WpMVxUHIYj4zOmUiGQfNOdTXBnFKGc1o in the WeatherService class with your Tomorrow.io API key.
    OpenCage API Key: Replace 6639785137fa4c16adc0975c9ac2ea31 in the WeatherService class with your OpenCage API key.
