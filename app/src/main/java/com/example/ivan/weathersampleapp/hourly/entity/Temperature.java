package com.example.ivan.weathersampleapp.hourly.entity;


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
