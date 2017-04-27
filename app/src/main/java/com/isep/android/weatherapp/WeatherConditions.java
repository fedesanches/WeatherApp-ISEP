package com.isep.android.weatherapp;

public class WeatherConditions {
    private String lat;
    private String lng;
    private String temperature;
    private String max;
    private String min;
    private String wind;
    private String humidity;
    private String rain;

    public WeatherConditions() {}

    public WeatherConditions(String lat, String lng, String temperature, String max, String min, String wind, String humidity, String rain) {
        this.lat = lat;
        this.lng = lng;
        this.temperature = temperature;
        this.max = max;
        this.min = min;
        this.wind = wind;
        this.humidity = humidity;
        this.rain = rain;
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

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }
}
