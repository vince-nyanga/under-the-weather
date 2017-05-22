package com.greenmoon.undertheweather.utils;

import android.arch.lifecycle.LiveData;

import com.greenmoon.undertheweather.api.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by Vincent.
 */

public class LiveDataAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(getRawType(returnType) != LiveData.class)
            return null;
        Type observableType = getParameterUpperBound(0,(ParameterizedType)returnType);
        Type type = getParameterUpperBound(0, (ParameterizedType)observableType);
        return new LiveDataAdapter<>(type);
    }
}
