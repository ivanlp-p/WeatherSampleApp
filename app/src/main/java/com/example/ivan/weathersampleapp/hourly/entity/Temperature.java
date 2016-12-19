package com.example.ivan.weathersampleapp.hourly.entity;

/**
 * Created by I.Laukhin on 19.12.2016.
 */

public class Temperature {

    private int metric;

    public Temperature() {
    }

    public Temperature(int metric) {
        this.metric = metric;
    }

    public int getMetric() {
        return metric;
    }

    public void setMetric(int metric) {
        this.metric = metric;
    }
}
