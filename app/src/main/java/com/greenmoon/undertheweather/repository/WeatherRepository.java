package com.greenmoon.undertheweather.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.greenmoon.undertheweather.BuildConfig;
import com.greenmoon.undertheweather.api.ApiResponse;
import com.greenmoon.undertheweather.api.WeatherApi;
import com.greenmoon.undertheweather.core.Resource;
import com.greenmoon.undertheweather.model.WeatherData;

import javax.inject.Inject;

/**
 * Created by Vincent.
 */

public class WeatherRepository {

    @Inject
    public  WeatherApi weatherApi;
    public WeatherRepository(WeatherApi weatherApi){
        this.weatherApi = weatherApi;
    }

    public LiveData<Resource<WeatherData>> getWeather(double lat, double lon){
        final MediatorLiveData<Resource<WeatherData>> result = new MediatorLiveData<>();
        result.setValue(Resource.<WeatherData>loading());
        final LiveData<ApiResponse<WeatherData>> response = weatherApi.getWeather(lat, lon, "metric",
                BuildConfig.OPEN_WEATHER_API_KEY);
        result.addSource(response, new Observer<ApiResponse<WeatherData>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<WeatherData> res) {
                result.removeSource(response);
                if(res.isSuccessful()){
                    result.setValue(Resource.success(res.body));
                }else {
                    result.setValue(Resource.<WeatherData>error(res.message));
                }
            }
        });
        return result;
    }

}
