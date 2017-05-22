package com.greenmoon.undertheweather.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.greenmoon.undertheweather.WeatherApp;
import com.greenmoon.undertheweather.core.Resource;
import com.greenmoon.undertheweather.di.WeatherComponent;
import com.greenmoon.undertheweather.model.WeatherData;
import com.greenmoon.undertheweather.repository.WeatherRepository;

import javax.inject.Inject;

/**
 * Created by Vincent.
 */

public class WeatherViewModel extends ViewModel implements WeatherComponent.Injectable {
    @Inject
    public  WeatherRepository repository;

    public WeatherViewModel(){
        WeatherApp.getWeatherComponent().inject(this);
    }

    public LiveData<Resource<WeatherData>>getWeather(double lat, double lon){
        return repository.getWeather(lat, lon);
    }

    @Override
    public void inject(WeatherComponent weatherComponent) {
        weatherComponent.inject(this);
    }
}
