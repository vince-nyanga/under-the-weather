package com.greenmoon.undertheweather.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.greenmoon.undertheweather.WeatherApp;

/**
 * Created by Vincent.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private WeatherApp app;

    public ViewModelFactory(WeatherApp app){
        this.app = app;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if(t instanceof WeatherComponent.Injectable){
           // ((WeatherComponent.Injectable)t).inject(app.getWeatherComponent());
        }
        return t;
    }
}
