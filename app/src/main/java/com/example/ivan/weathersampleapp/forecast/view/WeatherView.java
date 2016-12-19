package com.example.ivan.weathersampleapp.forecast.view;

import android.location.Address;

import com.example.ivan.weathersampleapp.forecast.entity.conditions.ConditionsEntity;
import com.example.ivan.weathersampleapp.forecast.entity.forecast.ForecastDay;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by I.Laukhin on 15.12.2016.
 */

public interface WeatherView extends MvpView {

    void showCurrentWeather(ConditionsEntity entity, Address address, String lastUpdateTime);
    void showForecastWeather(List<ForecastDay> forecastList);
    void showWarningToast();
}
