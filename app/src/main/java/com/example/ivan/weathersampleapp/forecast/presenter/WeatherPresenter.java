package com.example.ivan.weathersampleapp.forecast.presenter;

import com.example.ivan.weathersampleapp.forecast.view.WeatherView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by I.Laukhin on 15.12.2016.
 */

public interface WeatherPresenter extends MvpPresenter<WeatherView> {

    void loadWeatherInfo();

    void connectGoogleApi();

    void stopGoogleApi();

    void updateWeatherInfo();
}
