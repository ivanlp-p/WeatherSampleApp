package com.example.ivan.weathersampleapp.hourly.entity;

/**
 * Created by I.Laukhin on 19.12.2016.
 */

public class ForecastTime {

    private String hour_padded;
    private String min;
    private String mon;
    private String mday_padded;
    private String month_name_abbrev;
    private String weekday_name_abbrev;
    private Temperature temp;
    private String condition;
    private String icon_url;

    public ForecastTime() {
    }

    public ForecastTime(String hour_padded,
                        String min,
                        String mon,
                        String mday_padded,
                        String month_name_abbrev,
                        String weekday_name_abbrev,
                        Temperature temp,
                        String condition,
                        String icon_url)
    {
        this.hour_padded = hour_padded;
        this.min = min;
        this.mon = mon;
        this.mday_padded = mday_padded;
        this.month_name_abbrev = month_name_abbrev;
        this.weekday_name_abbrev = weekday_name_abbrev;
        this.temp = temp;
        this.condition = condition;
        this.icon_url = icon_url;
    }

    public String getHour_padded() {
        return hour_padded;
    }

    public void setHour_padded(String hour_padded) {
        this.hour_padded = hour_padded;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getMday_padded() {
        return mday_padded;
    }

    public void setMday_padded(String mday_padded) {
        this.mday_padded = mday_padded;
    }

    public String getMonth_name_abbrev() {
        return month_name_abbrev;
    }

    public void setMonth_name_abbrev(String month_name_abbrev) {
        this.month_name_abbrev = month_name_abbrev;
    }

    public String getWeekday_name_abbrev() {
        return weekday_name_abbrev;
    }

    public void setWeekday_name_abbrev(String weekday_name_abbrev) {
        this.weekday_name_abbrev = weekday_name_abbrev;
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
