package com.example.ivan.weathersampleapp.hourly.presenter;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.ivan.weathersampleapp.hourly.entity.HourlyEntity;
import com.example.ivan.weathersampleapp.hourly.view.HourlyView;
import com.example.ivan.weathersampleapp.net.WundergroundApi;
import com.example.ivan.weathersampleapp.utils.SharedPreferencesHelper;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HourlyPresenterImpl
    extends MvpBasePresenter<HourlyView>
    implements HourlyPresenter
{

    private static final String HOURLY = "hourly";

    private Context context;
    private WundergroundApi wundergroundApi;
    private SharedPreferencesHelper prefsHelper;

    @Inject
    public HourlyPresenterImpl(Context context,
                               WundergroundApi wundergroundApi,
                               SharedPreferencesHelper prefsHelper) {
        this.context = context;
        this.wundergroundApi = wundergroundApi;
        this.prefsHelper = prefsHelper;

    }

    @Override
    public void loadHourlyForecastInfo() {

        if (isViewAttached()) {
            if (isOnline() && prefsHelper.getLastLatitude() != 0) {
                getWeatherFromApi(prefsHelper.getLastLatitude(), prefsHelper.getLastLongitude());
            } else if (prefsHelper.getHourly() != null) {
                getView().showHourlyForecast(prefsHelper.getHourly());
            }
        }
    }

    private void getWeatherFromApi(double latitude, double longitube) {

        Call<HourlyEntity> callConditions = wundergroundApi.getHourlyForecastData(
                HOURLY,
                "lang:RU",
                String.valueOf(latitude) + "," + String.valueOf(longitube));
        callConditions.enqueue(new Callback<HourlyEntity>() {

            @Override
            public void onResponse(Call<HourlyEntity> call, Response<HourlyEntity> response) {
                if (response.isSuccessful()) {
                    HourlyEntity entity = response.body();
                    prefsHelper.setHourly(entity);

                    if (isViewAttached()) {
                        getView().showHourlyForecast(entity);
                    }
                }
            }

            @Override
            public void onFailure(Call<HourlyEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() == null) {
            return false;
        }

        return cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
