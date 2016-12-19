package com.example.ivan.weathersampleapp.location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by I.Laukhin on 15.12.2016.
 */

public class LocationApi implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {

    protected static final String TAG = "location-updates-sample";

    private final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    private final static String LOCATION_KEY = "location-key";
    private final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    public static final int UPDATE_INTERVAL_IN_SECONDS = 10000;
    public static final int FAST_CEILING_IN_SECONDS = UPDATE_INTERVAL_IN_SECONDS / 2;

    private GoogleApiClient googleApiClient;

    private Location lastLocation;
    private LocationRequest locationRequest;
    private LocationManager lm;

    private Context context;
    private Double latitude;
    private Double longitude;
    private DateTime lastUpdateTime;
    private DateTimeFormatter formatter;
    private String date;

    private Boolean requestingLocationUpdates;
    private boolean isGoogleApiConnected = false;

    public LocationApi(Context context) {
        this.context = context;

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        buildGoogleApiClient(context);
        googleApiClient.connect();

        formatter = DateTimeFormat.forPattern("d MMM HH:mm");
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getDate() {
        return date;
    }

    private synchronized void buildGoogleApiClient(Context context) {
        Log.i(TAG, "Building GoogleApiClient");

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        createLocationRequest();

        Log.i(TAG, "Building GoogleApiClient is end");

    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(UPDATE_INTERVAL_IN_SECONDS);
        locationRequest.setFastestInterval(FAST_CEILING_IN_SECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public void updateLocationFromBundle(Bundle savedInstanceState) {

        if (savedInstanceState != null) {

            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                requestingLocationUpdates = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY);
            }

            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                lastLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
              //  lastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
        }
    }

    public void startLocationUpdate() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Log.d("happy", "startLocationUpdates");
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        googleApiClient, locationRequest, this);
            }
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        }

    }

    public void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    public void disconnectGoogleApi() {
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Connected to GoogleApiClient");
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    lastUpdateTime = new DateTime();

                }
            } else {
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                lastUpdateTime = new DateTime();
            }
        }

        if (lastLocation != null){
            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
            date = lastUpdateTime.toString(formatter);

            isGoogleApiConnected = true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        lastUpdateTime = new DateTime();
        date = lastUpdateTime.toString(formatter);
        Log.d("onLocationChanged", "onLocationChanged");
    //    updateUI();

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates);

    }

    public boolean isGoogleApiConnected() {
        return isGoogleApiConnected;
    }
}
