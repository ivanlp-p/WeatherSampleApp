package com.example.ivan.weathersampleapp.forecast.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;

import com.example.ivan.weathersampleapp.forecast.entity.conditions.ConditionsEntity;
import com.example.ivan.weathersampleapp.forecast.entity.forecast.ForecastEntity;
import com.example.ivan.weathersampleapp.forecast.view.WeatherView;
import com.example.ivan.weathersampleapp.location.LocationApi;
import com.example.ivan.weathersampleapp.net.WundergroundApi;
import com.example.ivan.weathersampleapp.utils.SharedPreferencesHelper;
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

public class WeatherPresenterImpl
        extends MvpBasePresenter<WeatherView>
        implements WeatherPresenter
{

    private static final String CONDITIONS = "conditions";
    private static final String FORECAST = "forecast";

    private Context context;
    private WundergroundApi wundergroundApi;
    private LocationApi locationApi;
    private SharedPreferencesHelper prefsHelper;

    private volatile Address address;
    private volatile String area = "";
    private volatile String cityName = "";
    private volatile String lastUpdateTime;

    private LocationManager lm;

    @Inject
    public WeatherPresenterImpl(Context context,
                                WundergroundApi wundergroundApi,
                                LocationApi locationApi,
                                SharedPreferencesHelper prefsHelper) {
        this.context = context;
        this.wundergroundApi = wundergroundApi;
        this.locationApi = locationApi;
        this.prefsHelper = prefsHelper;

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void loadWeatherInfo() {
        if (isViewAttached()) {
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && isOnline()) {
                if (!locationApi.isGoogleApiConnected()) {

                    currentWeatherThread.start();
                } else {
                    getWeatherFromApi(locationApi.getLatitude(), locationApi.getLongitude(), true);
                }
            }
        }
    }

    @Override
    public void updateWeatherInfo() {

        if (isViewAttached()) {
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && isOnline() && prefsHelper.getConditions() != null) {
                if (locationApi.isGoogleApiConnected()) {
                    locationApi.startLocationUpdate();
                    getWeatherFromApi(locationApi.getLatitude(), locationApi.getLongitude(), true);
                }
            } else if (prefsHelper.getConditions() != null && isOnline()) {
                getWeatherFromApi(prefsHelper.getLastLatitude(), prefsHelper.getLastLongitude(), false);
            } else if (prefsHelper.getConditions() != null){
                getView().showCurrentWeather(prefsHelper.getConditions(),
                        prefsHelper.getLastCity(), prefsHelper.getLastAddress(),
                        prefsHelper.getLastDate());
                getView().showForecastWeather(prefsHelper.getForecast().getForecast().getSimpleforecast().getForecastday());
                getView().showWarningToast();
            }
        }
    }

    private void getWeatherFromApi(double latitude, double longitube, boolean isLocationConnected) {

        area = "";

        if (isLocationConnected) {
            prefsHelper.setLastLatitude(latitude);
            prefsHelper.setLastLongitude(longitube);
        }

        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocation(
                    latitude,
                    longitube,
                    1);
        } catch (IOException e) {

        }

        if (addressList != null && addressList.size() > 0) {
            address = addressList.get(0);
            if (address.getCountryName() != null) {
                area = area + address.getCountryName();
            }

            if (address.getAdminArea() != null) {
                area = area + ", " + address.getAdminArea();
            }

            if (address.getLocality() != null) {
                area = area + ", " + address.getLocality();
                cityName = address.getLocality();
            }

            if (address.getAddressLine(0) != null) {
                area = area + ", " + address.getAddressLine(0);
            }
        }

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            lastUpdateTime = locationApi.getDate();
        } else {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("d MMM HH:mm");
            lastUpdateTime = new DateTime().toString(formatter);
        }

        prefsHelper.setLastDate(lastUpdateTime);

        Call<ConditionsEntity> callConditions = wundergroundApi.getConditionsData(
                CONDITIONS,
                "lang:RU",
                String.valueOf(latitude) + "," + String.valueOf(longitube));
        callConditions.enqueue(new Callback<ConditionsEntity>() {

            @Override
            public void onResponse(Call<ConditionsEntity> call, Response<ConditionsEntity> response) {
                if (response.isSuccessful()) {
                    ConditionsEntity entity = response.body();
                    prefsHelper.setConditions(entity);

                    if (address == null) {
                        area = entity.getObservation().getDisplay_location().getFull();
                        cityName = entity.getObservation().getDisplay_location().getCity();
                    }

                    prefsHelper.setLastAddress(area);
                    prefsHelper.setLastCity(cityName);

                    if (isViewAttached()) {
                        getView().showCurrentWeather(entity, cityName, area, lastUpdateTime);
                    }
                } else {
                    getView().showWarningToast();
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
                String.valueOf(latitude) + "," + String.valueOf(longitube));
        callForecast.enqueue(new Callback<ForecastEntity>() {
            @Override
            public void onResponse(Call<ForecastEntity> call, Response<ForecastEntity> response) {
                if (response.isSuccessful()) {
                    ForecastEntity entity = response.body();
                    prefsHelper.setForecast(entity);

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

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Boolean isGetGoogleApi = (Boolean) msg.obj;
            if (isGetGoogleApi) {
                currentWeatherThread.interrupt();
                getWeatherFromApi(locationApi.getLatitude(), locationApi.getLongitude(), true);
            }
        }
    };

    private Thread currentWeatherThread = new Thread(new Runnable() {
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
        }
    });
}
