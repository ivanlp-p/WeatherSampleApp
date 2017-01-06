package com.example.ivan.weathersampleapp.forecast.entity.forecast;

public class HighTemperature {

    private int celsius;

    public HighTemperature() {
    }

    public HighTemperature(int celsius) {
        this.celsius = celsius;
    }

    public int getCelsius() {
        return celsius;
    }

    public void setCelsius(int celsius) {
        this.celsius = celsius;
    }
}
