package com.example.ivan.weathersampleapp.forecast.entity.forecast;

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
