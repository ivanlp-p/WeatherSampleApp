package com.example.ivan.weathersampleapp.main.presenter;

import com.example.ivan.weathersampleapp.main.view.MainView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;


public interface MainPresenter extends MvpPresenter<MainView> {

    void checkLocationPermission();
    void startUpdateAfterPermGranted();
    void startLocationUpdate();
    void stopLocationUpdate();
    void disconnectGoogleApi();
}
