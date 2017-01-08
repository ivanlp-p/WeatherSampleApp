package com.example.ivan.weathersampleapp.main.presenter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;

import com.example.ivan.weathersampleapp.location.LocationApi;
import com.example.ivan.weathersampleapp.main.view.MainView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;


public class MainPresenterImpl
        extends MvpBasePresenter<MainView>
        implements MainPresenter
{

    private Context context;
    private LocationApi locationApi;

    @Inject
    public MainPresenterImpl(Context context, LocationApi locationApi) {
        this.context = context;
        this.locationApi = locationApi;
    }

    @Override
    public void checkLocationPermission() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (isViewAttached()) {
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        getView().showRequestPermissions();
                    }
                } else {
                    startUpdateAfterPermGranted();
                }
            } else {
                getView().showNoGpsDialog();
            }
        }
    }

    @Override
    public void startUpdateAfterPermGranted() {
        if (locationApi.isGoogleApiConnected())
            locationApi.startLocationUpdate();

        locationThread.start();
    }

    @Override
    public void startLocationUpdate() {
        if (locationApi.isGoogleApiConnected())
            locationApi.startLocationUpdate();
    }

    @Override
    public void stopLocationUpdate() {
        if (locationApi.isGoogleApiConnected())
            locationApi.stopLocationUpdate();
    }

    @Override
    public void disconnectGoogleApi() {
        locationApi.disconnectGoogleApi();
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Boolean isLocationAvailable = (Boolean) msg.obj;

            if (isLocationAvailable) {
                if (isViewAttached()) {
                    getView().showWeatherFirstTime();
                }

            }
        }
    };

    private Thread locationThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Message msg = Message.obtain();
            Boolean isLocationAvailable = false;

            while (!isLocationAvailable) {
                if (locationApi.getLatitude() != null) {

                    isLocationAvailable = true;
                    msg.obj = isLocationAvailable;
                    handler.sendMessage(msg);
                }
            }
        }
    });
}
