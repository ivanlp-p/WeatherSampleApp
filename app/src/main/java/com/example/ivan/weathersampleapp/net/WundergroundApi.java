package com.example.ivan.weathersampleapp.net;

import com.example.ivan.weathersampleapp.forecast.entity.conditions.ConditionsEntity;
import com.example.ivan.weathersampleapp.forecast.entity.forecast.ForecastEntity;
import com.example.ivan.weathersampleapp.hourly.entity.HourlyEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface WundergroundApi {

    String API_KEY = "1088c60ee4c1b4c6/";
    String BASE_URL = "http://api.wunderground.com/api/" + API_KEY;


    @GET("{features}/{settings}/q/{query}.json")
    Call<ConditionsEntity> getConditionsData(@Path("features") String features,
                                             @Path("settings") String settings,
                                             @Path("query") String query);

    @GET("{features}/{settings}/q/{query}.json")
    Call<ForecastEntity> getForecastData(@Path("features") String features,
                                        @Path("settings") String settings,
                                        @Path("query") String query);

    @GET("{features}/{settings}/q/{query}.json")
    Call<HourlyEntity> getHourlyForecastData(@Path("features") String features,
                                       @Path("settings") String settings,
                                       @Path("query") String query);
}
