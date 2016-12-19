package com.example.ivan.weathersampleapp.common;

import android.content.Context;

import com.example.ivan.weathersampleapp.location.LocationApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by I.Laukhin on 19.12.2016.
 */

@Module
public class WeatherModule {

    @Provides
    @Singleton
    public LocationApi provideLocationApi(Context context) {
        return new LocationApi(context);
    }
}
