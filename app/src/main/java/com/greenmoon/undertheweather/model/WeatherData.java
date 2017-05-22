package com.greenmoon.undertheweather.model;

import com.google.gson.annotations.SerializedName;
import com.greenmoon.undertheweather.BuildConfig;

import java.util.List;

/**
 * Created by Vincent.
 */

public class WeatherData {

    public final String name;

    public final Main main;

    public final List<Weather> weather;


    public WeatherData(String name, Main main, List<Weather> weather){
        this.name = name;
        this.main = main;
        this.weather =  weather;
    }



    public static class Main {
        @SerializedName("temp_min")
        public final double minTemperature;

        @SerializedName("temp_max")
        public final double maxTemperature;


        public Main(double minTemperature, double maxTemperature){
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
        }
    }

    public static class Weather {

        public final String icon;

        public Weather(String icon){
            this.icon = icon;
        }
    }
}
