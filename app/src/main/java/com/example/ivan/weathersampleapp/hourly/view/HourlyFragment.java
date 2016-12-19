package com.example.ivan.weathersampleapp.hourly.view;

import com.example.ivan.weathersampleapp.hourly.presenter.HourlyPresenter;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

/**
 * Created by I.Laukhin on 17.12.2016.
 */

public class HourlyFragment
    extends MvpFragment<HourlyView, HourlyPresenter>
    implements HourlyView
{
    @Override
    public HourlyPresenter createPresenter() {
        return null;
    }
}
