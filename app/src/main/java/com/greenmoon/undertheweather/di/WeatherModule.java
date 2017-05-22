package com.greenmoon.undertheweather.di;

import android.content.Context;

import com.greenmoon.undertheweather.BuildConfig;
import com.greenmoon.undertheweather.WeatherApp;
import com.greenmoon.undertheweather.api.WeatherApi;
import com.greenmoon.undertheweather.repository.WeatherRepository;
import com.greenmoon.undertheweather.utils.LiveDataAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vincent.
 */
@Module
public class WeatherModule {

    @Provides
    @Singleton
    WeatherApi providesWeatherApi(){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.OPEN_WEATHER_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataAdapterFactory())
                .build()
                .create(WeatherApi.class);
    }

    @Provides
    @Singleton
    WeatherRepository providesWeatherRepository(WeatherApi weatherApi){
        return  new WeatherRepository(weatherApi);
    }
}
