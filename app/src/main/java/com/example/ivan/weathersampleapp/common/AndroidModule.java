package com.example.ivan.weathersampleapp.common;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by I.Laukhin on 15.12.2016.
 */

@Module
public class AndroidModule {
    private final WeatherSampleApplication application;

    public AndroidModule(WeatherSampleApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context providesContext() {return application;}
}
