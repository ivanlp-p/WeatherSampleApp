package com.example.ivan.weathersampleapp.main.view;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ivan.weathersampleapp.R;
import com.example.ivan.weathersampleapp.common.WeatherSampleApplication;
import com.example.ivan.weathersampleapp.forecast.view.WeatherFragment;
import com.example.ivan.weathersampleapp.main.presenter.MainPresenter;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

public class MainActivity
        extends MvpActivity<MainView, MainPresenter>
        implements MainView, NavigationView.OnNavigationItemSelectedListener
{
    WeatherFragment fragment;

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return ((WeatherSampleApplication) getApplication()).component().mainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((WeatherSampleApplication) getApplication()).component().inject(this);
        setContentView(R.layout.activity_main);

        fragment = WeatherFragment.newInstance();

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.content_container, fragment)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.forecast);

        getPresenter().checkLocationPermission();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getPresenter().startLocationUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().stopLocationUpdate();
    }

    @Override
    protected void onStop() {
        getPresenter().disconnectGoogleApi();
        super.onStop();
    }

    @Override
    public void showRequestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    @Override
    public void showNoGpsToast() {
        Toast.makeText(this, getResources().getString(R.string.gps_not_activited),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.forecast) {

        } else if (id == R.id.hourly) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("perm", "perm granded");

                    fragment.showWeatherFirstTime();

                } else {
                    Toast.makeText(this, "Права доступа к определению местоположения не получены",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }
}
