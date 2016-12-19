package com.example.ivan.weathersampleapp.forecast.entity.forecast;

/**
 * Created by I.Laukhin on 16.12.2016.
 */

public class Date {

    private String weekday_short;

    public Date() {
    }

    public Date(String weekday_short) {
        this.weekday_short = weekday_short;
    }

    public String getWeekday_short() {
        return weekday_short;
    }

    public void setWeekday_short(String weekday_short) {
        this.weekday_short = weekday_short;
    }
}
