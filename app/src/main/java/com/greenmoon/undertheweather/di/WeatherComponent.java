package com.greenmoon.undertheweather.di;

import com.greenmoon.undertheweather.ui.WeatherViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Vincent.
 */

@Singleton
@Component(modules = {WeatherModule.class})
public interface WeatherComponent {
    void inject(WeatherViewModel weatherViewModel);

    interface Injectable{
        void inject(WeatherComponent weatherComponent);
    }
}
