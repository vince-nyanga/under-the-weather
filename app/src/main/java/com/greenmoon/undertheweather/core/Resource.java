package com.greenmoon.undertheweather.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Vincent.
 *
 * This is a generic resource that has a status.
 */

public class Resource<D> {

    public final D data;
    public final Status status;
    public final String message;


    public Resource(@NonNull Status status, @Nullable D data, @Nullable String message){
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <D> Resource<D> success (D data){
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <D> Resource<D> loading(){
        return new Resource<>(Status.LOADING, null, null);
    }

    public static <D> Resource<D> error(@Nullable String message){
        return new Resource<>(Status.ERROR, null, message);
    }

}
