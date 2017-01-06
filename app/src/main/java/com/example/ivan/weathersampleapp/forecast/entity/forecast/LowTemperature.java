package com.example.ivan.weathersampleapp.forecast.entity.forecast;

public class LowTemperature {

    private int celsius;

    public LowTemperature() {
    }

    public LowTemperature(int celsius) {
        this.celsius = celsius;
    }

    public int getCelsius() {
        return celsius;
    }

    public void setCelsius(int celsius) {
        this.celsius = celsius;
    }
}
