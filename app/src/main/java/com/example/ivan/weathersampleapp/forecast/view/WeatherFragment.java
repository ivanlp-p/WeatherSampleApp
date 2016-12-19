package com.example.ivan.weathersampleapp.forecast.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.weathersampleapp.R;
import com.example.ivan.weathersampleapp.common.WeatherSampleApplication;
import com.example.ivan.weathersampleapp.databinding.FragmentWeatherBinding;
import com.example.ivan.weathersampleapp.forecast.entity.conditions.ConditionsEntity;
import com.example.ivan.weathersampleapp.forecast.entity.forecast.ForecastDay;
import com.example.ivan.weathersampleapp.forecast.presenter.WeatherPresenter;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by I.Laukhin on 16.12.2016.
 */

public class WeatherFragment
        extends MvpFragment<WeatherView, WeatherPresenter>
        implements WeatherView {
    private static final String PERMISSION_TAG = "permission_tag";

    private FragmentWeatherBinding binding;
    private TextView cityName;

    @Override
    public WeatherPresenter createPresenter() {
        return ((WeatherSampleApplication) getActivity().getApplication()).component().weatherPresenter();
    }

    public static WeatherFragment newInstance() {

        WeatherFragment fragment = new WeatherFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false);

        cityName = (TextView) getActivity().findViewById(R.id.city_name);

        getActivity().setTitle(R.string.forecast_three_days);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.supportsPredictiveItemAnimations();
        binding.rvForecastWeather.setLayoutManager(layoutManager);

        binding.forecastRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        binding.forecastRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().updateWeatherInfo();
                Log.d("update", "Update is done");
                binding.forecastRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getActivity().checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED&&
                getActivity().checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getPresenter().connectGoogleApi();
            getPresenter().loadWeatherInfo();
        }


    }

    @Override
    public void onStop() {
        super.onStop();

        getPresenter().stopGoogleApi();
    }

    @Override
    public void showCurrentWeather(ConditionsEntity entity, Address address, String lastUpdateTime) {
        if (address != null) {
            cityName.setText(address.getLocality());
            binding.currentPosAddress.setText(address.getCountryName() + ", " +
                    address.getAdminArea() + ", " +
                    address.getLocality() + ", " +
                    address.getAddressLine(0));
        } else {
            cityName.setText(entity.getObservation().getDisplay_location().getCity());
            binding.currentPosAddress.setText(entity.getObservation().getDisplay_location().getFull());
        }

        binding.dateLastUpdate.setText(getResources().getString(R.string.last_update_time) + lastUpdateTime);
        binding.currentTemp.setText(entity.getObservation().getTemp_c() + " °C");
        binding.feelslikeTemp.setText("Ощущается: " + entity.getObservation().getFeelslike_c() + " °C");
        binding.weather.setText(entity.getObservation().getWeather());
        Picasso.with(getContext())
                .load(entity.getObservation().getIcon_url())
                .into(binding.weatherIcon);
    }

    @Override
    public void showForecastWeather(List<ForecastDay> forecastList) {
        WeatherAdapter adapter = new WeatherAdapter(getContext(), forecastList);
        binding.rvForecastWeather.setAdapter(adapter);
    }

    @Override
    public void showWarningToast() {
        Toast.makeText(getActivity(), getResources().getString(R.string.warning_toast),
                Toast.LENGTH_SHORT).show();
    }

    public void showWeatherFirstTime() {
        getPresenter().connectGoogleApi();
        getPresenter().loadWeatherInfo();
    }

}
