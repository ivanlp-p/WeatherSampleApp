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


public class LocationApi implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {

    private static final String TAG = "location-updates-sample";

    private static final int UPDATE_INTERVAL_IN_SECONDS = 5000;
    private static final int FAST_CEILING_IN_SECONDS = UPDATE_INTERVAL_IN_SECONDS / 2;

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

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        createLocationRequest();
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(UPDATE_INTERVAL_IN_SECONDS);
        locationRequest.setFastestInterval(FAST_CEILING_IN_SECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public void startLocationUpdate() {

            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

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
        isGoogleApiConnected = false;
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
        }

        isGoogleApiConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastUpdateTime = new DateTime();
        date = lastUpdateTime.toString(formatter);
    }

    public boolean isGoogleApiConnected() {
        return isGoogleApiConnected;
    }
}
