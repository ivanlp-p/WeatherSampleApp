package com.example.ivan.weathersampleapp.hourly.entity;

import java.util.List;


public class HourlyEntity {

    private List<HourlyForecast> hourly_forecast;

    public HourlyEntity() {
    }

    public HourlyEntity(List<HourlyForecast> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public List<HourlyForecast> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecast> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }
}
