package com.example.ivan.weathersampleapp.hourly.presenter;

import com.example.ivan.weathersampleapp.hourly.view.HourlyView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;


public interface HourlyPresenter extends MvpPresenter<HourlyView> {

    void loadHourlyForecastInfo();
}
