package com.greenmoon.undertheweather.api;

import org.junit.Test;

import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Created by Vincent.
 */
public class ApiResponseTest {


    @Test
    public void successfulResponse(){
        ApiResponse<String> apiResponse = new ApiResponse<String>(Response.success("successful"));
        assertEquals("Body should be 'successful'",apiResponse.body, "successful");
        assertNull("Message should be null", apiResponse.message);
        assertEquals("Response code should be 200", 200, apiResponse.responseCode);
    }

    @Test
    public void failedResponse(){
        Exception exception = new Exception("error");
        ApiResponse apiResponse = new ApiResponse(exception);
        assertNull("Body should be null", apiResponse.body);
        assertEquals("Response code should be 500", apiResponse.responseCode, 500);
        assertEquals("Message should be 'error'",apiResponse.message, "error");
    }
}