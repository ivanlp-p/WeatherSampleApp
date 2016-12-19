package com.example.ivan.weathersampleapp.main.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by I.Laukhin on 15.12.2016.
 */

public interface MainView extends MvpView {

    void showRequestPermissions();
    void showNoGpsToast();
}
