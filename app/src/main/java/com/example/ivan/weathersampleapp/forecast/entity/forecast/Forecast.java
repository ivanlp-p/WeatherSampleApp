package com.example.ivan.weathersampleapp.forecast.entity.forecast;

/**
 * Created by I.Laukhin on 16.12.2016.
 */

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
