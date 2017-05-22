package com.greenmoon.undertheweather;

import android.app.Activity;
import android.app.Application;

import com.greenmoon.undertheweather.di.DaggerWeatherComponent;
import com.greenmoon.undertheweather.di.WeatherComponent;
import com.greenmoon.undertheweather.di.WeatherModule;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

/**
 * Created by Vincent.
 */

public class WeatherApp extends Application {

    private static WeatherComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerWeatherComponent.builder()
                .weatherModule(new WeatherModule())
                .build();

    }


    public synchronized static WeatherComponent getWeatherComponent(){
        return component;
    }

}
