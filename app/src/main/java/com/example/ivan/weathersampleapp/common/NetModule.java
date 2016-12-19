package com.example.ivan.weathersampleapp.common;

import com.example.ivan.weathersampleapp.net.UnsafeOkHttpClient;
import com.example.ivan.weathersampleapp.net.WundergroundApi;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by I.Laukhin on 15.12.2016.
 */

@Module
public class NetModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttp() {
        OkHttpClient.Builder clientBuilder =
                UnsafeOkHttpClient
                        .getUnsafeOkHttpClient()
                        .followRedirects(false);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return clientBuilder
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public GsonBuilder provideGsonBuilder() {
        return new GsonBuilder();
    }

    @Provides
    @Singleton
    public WundergroundApi provideWundergroundApi(OkHttpClient client, GsonBuilder gsonBuilder) {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .baseUrl(WundergroundApi.BASE_URL)
                .build()
                .create(WundergroundApi.class);
    }

}
