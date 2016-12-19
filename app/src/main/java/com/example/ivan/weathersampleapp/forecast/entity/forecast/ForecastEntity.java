package com.example.ivan.weathersampleapp.forecast.entity.forecast;

/**
 * Created by I.Laukhin on 16.12.2016.
 */

public class ForecastEntity {

    private Forecast forecast;

    public ForecastEntity() {
    }

    public ForecastEntity(Forecast forecast) {
        this.forecast = forecast;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
