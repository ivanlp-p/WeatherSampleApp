package com.example.ivan.weathersampleapp.forecast.view;

import com.example.ivan.weathersampleapp.forecast.entity.conditions.ConditionsEntity;
import com.example.ivan.weathersampleapp.forecast.entity.forecast.ForecastDay;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;


public interface WeatherView extends MvpView {

    void showCurrentWeather(ConditionsEntity entity, String city, String area, String lastUpdateTime);
    void showForecastWeather(List<ForecastDay> forecastList);
    void showWarningToast();
}
