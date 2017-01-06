package com.example.ivan.weathersampleapp.forecast.entity.forecast;

public class Forecast {

    private SimpleForecast simpleforecast;

    public Forecast() {
    }

    public Forecast(SimpleForecast simpleforecast) {
        this.simpleforecast = simpleforecast;
    }

    public SimpleForecast getSimpleforecast() {
        return simpleforecast;
    }

    public void setSimpleforecast(SimpleForecast simpleforecast) {
        this.simpleforecast = simpleforecast;
    }
}
