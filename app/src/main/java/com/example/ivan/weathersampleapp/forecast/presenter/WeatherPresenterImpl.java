package com.example.ivan.weathersampleapp.forecast.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.ivan.weathersampleapp.forecast.entity.conditions.ConditionsEntity;
import com.example.ivan.weathersampleapp.forecast.entity.forecast.ForecastEntity;
import com.example.ivan.weathersampleapp.forecast.view.WeatherView;
import com.example.ivan.weathersampleapp.location.LocationApi;
import com.example.ivan.weathersampleapp.net.WundergroundApi;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by I.Laukhin on 16.12.2016.
 */

public class WeatherPresenterImpl
        extends MvpBasePresenter<WeatherView>
        implements WeatherPresenter {

    private static final String CONDITIONS = "conditions";
    private static final String FORECAST = "forecast";

    private Context context;
    private WundergroundApi wundergroundApi;
    private LocationApi locationApi;
    private volatile Address address;
    private volatile String lastUpdateTime;

    private LocationManager lm;


    @Inject
    public WeatherPresenterImpl(Context context, WundergroundApi wundergroundApi) {
        this.context = context;
        this.wundergroundApi = wundergroundApi;

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void loadWeatherInfo() {
        if (!locationApi.isGoogleApiConnected()) {

            locationThread.start();
        }


        Log.d("presenter", "Latitude = " + locationApi.getLatitude());
        Log.d("presenter", "Longitude = " + locationApi.getLongitude());
    }

    @Override
    public void connectGoogleApi() {
        locationApi = new LocationApi(context);
    }

    @Override
    public void stopGoogleApi() {

    }

    @Override
    public void updateWeatherInfo() {

        if (isViewAttached()) {
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && isOnline()) {
                locationApi.startLocationUpdate();
                getWeatherFromApi();
            } else if (isOnline()){
                getWeatherFromApi();
            } else {
                getView().showWarningToast();
            }
        }

        Log.d("update", "Update is done");
    }

    private void getWeatherFromApi() {

        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocation(
                    locationApi.getLatitude(),
                    locationApi.getLongitude(),
                    1);
        } catch (IOException e) {

        }

        if (addressList != null && addressList.size() > 0) {
            address = addressList.get(0);
        }

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lastUpdateTime = locationApi.getDate();
        } else {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("d MMM HH:mm");
            lastUpdateTime = new DateTime().toString(formatter);
        }



        Call<ConditionsEntity> callConditions = wundergroundApi.getConditionsrData(
                CONDITIONS,
                "lang:RU",
                String.valueOf(locationApi.getLatitude()) + "," + String.valueOf(locationApi.getLongitude()));
        callConditions.enqueue(new Callback<ConditionsEntity>() {

            @Override
            public void onResponse(Call<ConditionsEntity> call, Response<ConditionsEntity> response) {
                if (response.isSuccessful()) {
                    ConditionsEntity entity = response.body();

                    if (isViewAttached()) {
                        getView().showCurrentWeather(entity, address, lastUpdateTime);
                        Log.d("update", "Update time " + lastUpdateTime);
                    }
                } else {
                    // getView().showWarningToast();
                }

            }

            @Override
            public void onFailure(Call<ConditionsEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<ForecastEntity> callForecast = wundergroundApi.getForecastData(
                FORECAST,
                "lang:RU",
                String.valueOf(locationApi.getLatitude()) + "," + String.valueOf(locationApi.getLongitude()));
        callForecast.enqueue(new Callback<ForecastEntity>() {
            @Override
            public void onResponse(Call<ForecastEntity> call, Response<ForecastEntity> response) {
                if (response.isSuccessful()) {
                    ForecastEntity entity = response.body();

                    if (isViewAttached()) {
                        getView().showForecastWeather(entity.getForecast().getSimpleforecast().getForecastday());
                    }
                }
            }

            @Override
            public void onFailure(Call<ForecastEntity> call, Throwable t) {

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

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Boolean isGetGoogleApi = (Boolean) msg.obj;
            if (isGetGoogleApi) {
                getWeatherFromApi();
            }
        }
    };

    Thread locationThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Message msg = Message.obtain();
            Boolean isGoogleApiConnected = false;
            while (!isGoogleApiConnected) {
                if (locationApi.isGoogleApiConnected()) {
                    isGoogleApiConnected = true;
                    msg.obj = isGoogleApiConnected;
                    handler.sendMessage(msg);
                }
            }
            Log.d("locationApi", locationApi.isGoogleApiConnected() + "");
        }
    });
}
