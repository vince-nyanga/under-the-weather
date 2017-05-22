package com.greenmoon.undertheweather.utils;

import android.arch.lifecycle.LiveData;

import com.greenmoon.undertheweather.api.ApiResponse;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vincent.
 *
 * Retrofit adapter to convert Call into LiveData
 */

public class LiveDataAdapter<T> implements CallAdapter<T,LiveData<ApiResponse<T>>>{

    private Type type;

    public LiveDataAdapter(Type type){
        this.type = type;
    }
    @Override
    public Type responseType() {
        return type;
    }

    @Override
    public LiveData<ApiResponse<T>> adapt(final Call<T> call) {
        return new LiveData<ApiResponse<T>>() {
            @Override
            protected void onActive() {
                super.onActive();
                call.enqueue(new Callback<T>() {
                    @Override
                    public void onResponse(Call<T> call, Response<T> response) {
                        postValue(new ApiResponse<T>(response));
                    }

                    @Override
                    public void onFailure(Call<T> call, Throwable t) {
                        postValue(new ApiResponse<T>(t));
                    }
                });
            }
        };
    }
}
