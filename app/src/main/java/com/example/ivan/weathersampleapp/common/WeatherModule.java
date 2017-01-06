package com.example.ivan.weathersampleapp.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ivan.weathersampleapp.location.LocationApi;
import com.example.ivan.weathersampleapp.utils.SharedPreferencesHelper;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    @Singleton
    public LocationApi provideLocationApi(Context context) {
        return new LocationApi(context);
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public SharedPreferencesHelper provideSharedPreferencesHelper(SharedPreferences preferences, Gson gson) {
        return new SharedPreferencesHelper(preferences, gson);
    }
}
