package com.example.ivan.weathersampleapp.hourly.entity;

import java.util.List;

/**
 * Created by I.Laukhin on 19.12.2016.
 */

public class HourlyForecast {

    private List<ForecastTime> FCTTIME;

    public HourlyForecast() {
    }

    public HourlyForecast(List<ForecastTime> FCTTIME) {
        this.FCTTIME = FCTTIME;
    }

    public List<ForecastTime> getFCTTIME() {
        return FCTTIME;
    }

    public void setFCTTIME(List<ForecastTime> FCTTIME) {
        this.FCTTIME = FCTTIME;
    }
}
