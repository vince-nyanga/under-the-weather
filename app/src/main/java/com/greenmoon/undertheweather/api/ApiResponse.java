package com.greenmoon.undertheweather.api;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Vincent.
 */

public class ApiResponse<T> {

    public final T body;
    public final String message;
    public final int responseCode;

    public ApiResponse(Response<T> response){
        responseCode = response.code();
        if(response.isSuccessful()){
            body = response.body();
            message = null;
        }else{
            body = null;
            String msg = null;
            try {
                msg = response.errorBody().string();
            } catch (Exception e) {
                msg = response.message();
            }
            message = msg;
        }
    }

    public ApiResponse(Throwable error){
        body = null;
        responseCode = 500;
        message = error.getMessage();
    }

    public boolean isSuccessful(){
        return  responseCode > 199 && responseCode < 300;
    }
}
