package com.example.ivan.weathersampleapp.main.view;

import com.hannesdorfmann.mosby.mvp.MvpView;


public interface MainView extends MvpView {

    void showRequestPermissions();
    void showNoGpsToast();
    void showWeatherFirstTime();
}
