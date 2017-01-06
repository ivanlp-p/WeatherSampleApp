package com.example.ivan.weathersampleapp.utils;

import android.content.SharedPreferences;

import com.example.ivan.weathersampleapp.forecast.entity.conditions.ConditionsEntity;
import com.example.ivan.weathersampleapp.forecast.entity.forecast.ForecastEntity;
import com.example.ivan.weathersampleapp.hourly.entity.HourlyEntity;
import com.google.gson.Gson;


public class SharedPreferencesHelper {

    private static final String PREFERENCES_CONDITIONS = "PREFERENCES_CONDITIONS";
    private static final String PREFERENCES_FORECAST = "PREFERENCES_FORECAST";
    private static final String PREFERENCES_HOURLY = "PREFERENCES_HOURLY";
    private static final String PREFERENCES_LATITUDE = "PREFERENCES_LATITUDE";
    private static final String PREFERENCES_LONGITUDE = "PREFERENCES_LONGITUDE";
    private static final String PREFERENCES_AREA = "PREFERENCES_AREA";
    private static final String PREFERENCES_CITY = "PREFERENCES_CITY";
    private static final String PREFERENCES_DATE = "PREFERENCES_DATE";

    private SharedPreferences preferences;
    private Gson gson;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHelper(SharedPreferences preferences, Gson gson) {
        this.preferences = preferences;
        this.gson = gson;
        editor = preferences.edit();
    }

    public void setConditions(ConditionsEntity entity) {
        String serialaizedEntity = gson.toJson(entity);

        editor.putString(PREFERENCES_CONDITIONS, serialaizedEntity);
        editor.commit();
    }

    public ConditionsEntity getConditions() {

        return gson.fromJson(preferences.getString(PREFERENCES_CONDITIONS, ""), ConditionsEntity.class);
    }

    public void setForecast(ForecastEntity entity) {
        String serialaizedEntity = gson.toJson(entity);

        editor.putString(PREFERENCES_FORECAST, serialaizedEntity);
        editor.commit();
    }

    public ForecastEntity getForecast() {

        return gson.fromJson(preferences.getString(PREFERENCES_FORECAST, ""), ForecastEntity.class);
    }

    public void setHourly(HourlyEntity entity) {
        String serialaizedEntity = gson.toJson(entity);

        editor.putString(PREFERENCES_HOURLY, serialaizedEntity);
        editor.commit();
    }

    public HourlyEntity getHourly() {

        return gson.fromJson(preferences.getString(PREFERENCES_HOURLY, ""), HourlyEntity.class);
    }

    public void setLastLatitude(double latitude) {
        editor.putFloat(PREFERENCES_LATITUDE, (float) latitude);
        editor.commit();
    }

    public double getLastLatitude() {
        return preferences.getFloat(PREFERENCES_LATITUDE, 0);
    }

    public void setLastLongitude(double longitude) {
        editor.putFloat(PREFERENCES_LONGITUDE, (float) longitude);
        editor.commit();
    }

    public double getLastLongitude() {
        return preferences.getFloat(PREFERENCES_LONGITUDE, 0);
    }

    public void setLastAddress(String address) {

        editor.remove(PREFERENCES_AREA);
        editor.putString(PREFERENCES_AREA, address);
        editor.commit();
    }

    public String getLastAddress() {
        return preferences.getString(PREFERENCES_AREA, "");
    }

    public void setLastCity(String city) {

        editor.putString(PREFERENCES_CITY, city);
        editor.commit();
    }

    public String getLastCity() {
        return preferences.getString(PREFERENCES_CITY, "");
    }

    public void setLastDate(String lastDate) {
        editor.putString(PREFERENCES_DATE, lastDate);
        editor.commit();
    }

    public String getLastDate() {
        return preferences.getString(PREFERENCES_DATE, "");
    }
}
