package com.isep.android.weatherapp;

public class WeatherConditions {
    private String lat;
    private String lng;
    private String temperature;
    private String wind;
    private String humidity;

    public WeatherConditions() {}

    public WeatherConditions(String lat, String lng, String temperature, String wind, String humidity) {
        this.lat = lat;
        this.lng = lng;
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

}
