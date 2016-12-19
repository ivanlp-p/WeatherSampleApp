package com.example.ivan.weathersampleapp.main.presenter;

import com.example.ivan.weathersampleapp.main.view.MainView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by I.Laukhin on 15.12.2016.
 */

public interface MainPresenter extends MvpPresenter<MainView> {

    void checkLocationPermission();
    void startLocationUpdate();
    void stopLocationUpdate();
    void disconnectGoogleApi();
}
