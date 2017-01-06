package com.example.ivan.weathersampleapp.hourly.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivan.weathersampleapp.R;
import com.example.ivan.weathersampleapp.common.WeatherSampleApplication;
import com.example.ivan.weathersampleapp.databinding.FragmentHourlyBinding;
import com.example.ivan.weathersampleapp.hourly.entity.HourlyEntity;
import com.example.ivan.weathersampleapp.hourly.presenter.HourlyPresenter;
import com.hannesdorfmann.mosby.mvp.MvpFragment;


public class HourlyFragment
    extends MvpFragment<HourlyView, HourlyPresenter>
    implements HourlyView
{
    private FragmentHourlyBinding binding;

    @Override
    public HourlyPresenter createPresenter() {
        return ((WeatherSampleApplication) getActivity().getApplication()).component().hourlyPresenter();
    }

    public static HourlyFragment newInstance() {
        return new HourlyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hourly, container, false);

        getActivity().setTitle(R.string.hourly_forecast);

        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearManager.supportsPredictiveItemAnimations();
        binding.rvHourlyWeather.setLayoutManager(linearManager);

        binding.hourlyRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPresenter().loadHourlyForecastInfo();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.hourlyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().loadHourlyForecastInfo();
                binding.hourlyRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void showHourlyForecast(HourlyEntity entity) {

        HourlyAdapter adapter = new HourlyAdapter(getContext(), entity.getHourly_forecast());
        binding.rvHourlyWeather.setAdapter(adapter);
    }
}
