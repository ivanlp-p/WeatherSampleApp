package com.example.ivan.weathersampleapp.common;

import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.example.ivan.weathersampleapp.BuildConfig;
import com.example.ivan.weathersampleapp.forecast.presenter.WeatherPresenterImpl;
import com.example.ivan.weathersampleapp.hourly.presenter.HourlyPresenterImpl;
import com.example.ivan.weathersampleapp.main.presenter.MainPresenterImpl;
import com.example.ivan.weathersampleapp.main.view.MainActivity;
import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Singleton;

import dagger.Component;
import io.fabric.sdk.android.Fabric;

public class WeatherSampleApplication extends Application {

    protected ApplicationComponent component;

    @Singleton
    @Component(modules = {
            AndroidModule.class,
            NetModule.class,
            WeatherModule.class})
    public interface ApplicationComponent {
        void inject(MainActivity activity);

        MainPresenterImpl mainPresenter();
        WeatherPresenterImpl weatherPresenter();
        HourlyPresenterImpl hourlyPresenter();
    }

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .permitDiskReads()
                            .penaltyLog()
                            .penaltyDialog()
                            .penaltyDeathOnNetwork()
                            .build()
            );
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog()
                            .penaltyDeath()
                            .build()
            );
        }
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        LeakCanary.install(this);
        JodaTimeAndroid.init(this);

        component = DaggerWeatherSampleApplication_ApplicationComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public ApplicationComponent component() {
        if (component == null) {
            synchronized (this) {
                if (component == null) {
                    component = createComponent();
                }
            }
        }

        return component;
    }

    private ApplicationComponent createComponent() {
        return DaggerWeatherSampleApplication_ApplicationComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .build();
    }
}
