package com.greenmoon.undertheweather.api;

import android.arch.lifecycle.LiveData;

import com.greenmoon.undertheweather.model.WeatherData;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vincent.
 */

public interface WeatherApi {
    @GET("/data/2.5/weather")
    LiveData<ApiResponse<WeatherData>> getWeather(@Query("lat")double latitude, @Query("lon")
                                                  double longitude, @Query("units")String units,
                                                  @Query("appid")String appId);
}
