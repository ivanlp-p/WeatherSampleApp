package com.example.ivan.weathersampleapp.forecast.entity.forecast;

public class ForecastDay {

    private Date date;
    private HighTemperature high;
    private LowTemperature low;
    private String conditions;
    private String icon_url;

    public ForecastDay() {
    }

    public ForecastDay(Date date, HighTemperature high, LowTemperature low, String conditions, String icon_url) {
        this.date = date;
        this.high = high;
        this.low = low;
        this.conditions = conditions;
        this.icon_url = icon_url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HighTemperature getHigh() {
        return high;
    }

    public void setHigh(HighTemperature high) {
        this.high = high;
    }

    public LowTemperature getLow() {
        return low;
    }

    public void setLow(LowTemperature low) {
        this.low = low;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
