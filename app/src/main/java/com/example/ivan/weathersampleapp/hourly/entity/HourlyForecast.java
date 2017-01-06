package com.example.ivan.weathersampleapp.hourly.entity;


public class HourlyForecast {

    private ForecastTime FCTTIME;
    private Temperature temp;
    private String condition;
    private String icon_url;

    public HourlyForecast() {
    }

    public HourlyForecast(ForecastTime FCTTIME, Temperature temp, String condition, String icon_url) {
        this.FCTTIME = FCTTIME;
        this.temp = temp;
        this.condition = condition;
        this.icon_url = icon_url;
    }

    public ForecastTime getFCTTIME() {
        return FCTTIME;
    }

    public void setFCTTIME(ForecastTime FCTTIME) {
        this.FCTTIME = FCTTIME;
    }

    public Temperature getTemp() {
        return temp;
    }

    public void setTemp(Temperature temp) {
        this.temp = temp;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
