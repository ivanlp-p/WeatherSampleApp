package com.example.ivan.weathersampleapp.forecast.entity.forecast;

import java.util.List;

/**
 * Created by I.Laukhin on 16.12.2016.
 */

public class SimpleForecast {

    private List<ForecastDay> forecastday;

    public SimpleForecast() {
    }

    public SimpleForecast(List<ForecastDay> forecastday) {
        this.forecastday = forecastday;
    }

    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<ForecastDay> forecastday) {
        this.forecastday = forecastday;
    }
}
