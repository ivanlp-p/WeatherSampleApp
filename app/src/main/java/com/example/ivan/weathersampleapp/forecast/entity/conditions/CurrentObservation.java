package com.example.ivan.weathersampleapp.forecast.entity.conditions;

public class CurrentObservation {

    private DisplayLocation display_location;
    private String weather;
    private String temp_c;
    private String feelslike_c;
    private String icon_url;

    public CurrentObservation() {
    }

    public CurrentObservation(DisplayLocation display_location, String weather, String temp_c, String feelslike_c, String icon_url) {
        this.display_location = display_location;
        this.weather = weather;
        this.temp_c = temp_c;
        this.feelslike_c = feelslike_c;
        this.icon_url = icon_url;
    }

    public DisplayLocation getDisplay_location() {
        return display_location;
    }

    public void setDisplay_location(DisplayLocation display_location) {
        this.display_location = display_location;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(String temp_c) {
        this.temp_c = temp_c;
    }

    public String getFeelslike_c() {
        return feelslike_c;
    }

    public void setFeelslike_c(String feelslike_c) {
        this.feelslike_c = feelslike_c;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
