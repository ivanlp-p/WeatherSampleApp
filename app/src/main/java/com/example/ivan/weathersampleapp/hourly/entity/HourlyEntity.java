package com.example.ivan.weathersampleapp.hourly.entity;

/**
 * Created by I.Laukhin on 19.12.2016.
 */

public class HourlyEntity {

    private HourlyForecast hourly_forecast;

    public HourlyEntity() {
    }

    public HourlyEntity(HourlyForecast hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public HourlyForecast getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(HourlyForecast hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }
}
