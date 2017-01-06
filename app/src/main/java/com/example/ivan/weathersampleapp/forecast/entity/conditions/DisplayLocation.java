package com.example.ivan.weathersampleapp.forecast.entity.conditions;

public class DisplayLocation {

    private String full;
    private String city;

    public DisplayLocation() {
    }

    public DisplayLocation(String full, String city) {
        this.full = full;
        this.city = city;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
