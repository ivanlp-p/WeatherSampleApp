package com.example.ivan.weathersampleapp.forecast.presenter;

import com.example.ivan.weathersampleapp.forecast.view.WeatherView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;


public interface WeatherPresenter extends MvpPresenter<WeatherView> {

    void loadWeatherInfo();

    void updateWeatherInfo();
}
