package com.example.ivan.weathersampleapp.hourly.view;

import com.example.ivan.weathersampleapp.hourly.entity.HourlyEntity;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by I.Laukhin on 15.12.2016.
 */

public interface HourlyView extends MvpView {

    void showHourlyForecast(HourlyEntity entity);
}
