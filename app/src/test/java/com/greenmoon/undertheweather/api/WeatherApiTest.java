package com.greenmoon.undertheweather.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.icu.util.TimeUnit;
import android.support.annotation.Nullable;

import com.greenmoon.undertheweather.model.WeatherData;
import com.greenmoon.undertheweather.utils.LiveDataAdapterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * Created by Vincent.
 */
@RunWith(JUnit4.class)
public class WeatherApiTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private WeatherApi weatherApi;
    private MockWebServer mockWebServer;


    @Before
    public void init(){
        mockWebServer = new MockWebServer();
        weatherApi = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataAdapterFactory())
                .build()
                .create(WeatherApi.class);
    }


    @After
    public void cleanup() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getWeather() throws IOException, InterruptedException {
        enqueueResponse("weather.json");
        WeatherData weatherData = getValue(weatherApi.getWeather(0,0,"metric","appid")).body;
        RecordedRequest request = mockWebServer.takeRequest();
        assertNotNull(weatherData);
        assertEquals("Name should be 'Pretoria'", weatherData.name, "Pretoria");
        assertEquals(1, weatherData.weather.size());
        assertEquals("01n", weatherData.weather.get(0).icon);
        assertEquals(9, weatherData.main.minTemperature,0);
        assertEquals(25, weatherData.main.maxTemperature,0);
    }

    private void enqueueResponse(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse response = new MockResponse();
        mockWebServer.enqueue(response.setBody(source.readString(StandardCharsets.UTF_8)));

    }


    private  <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                data[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, java.util.concurrent.TimeUnit.SECONDS);
        return (T) data[0];
    }
}