package com.example.ivan.weathersampleapp.hourly.presenter;

import android.content.Context;

import com.example.ivan.weathersampleapp.hourly.view.HourlyView;
import com.example.ivan.weathersampleapp.net.WundergroundApi;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

/**
 * Created by I.Laukhin on 17.12.2016.
 */

public class HourlyPresenterImpl
    extends MvpBasePresenter<HourlyView>
    implements HourlyPresenter
{

    private Context context;
    private WundergroundApi wundergroundApi;

    @Inject
    public HourlyPresenterImpl(Context context, WundergroundApi wundergroundApi) {
        this.context = context;
        this.wundergroundApi = wundergroundApi;
    }
}
