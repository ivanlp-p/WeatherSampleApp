package com.example.ivan.weathersampleapp.common;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


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
