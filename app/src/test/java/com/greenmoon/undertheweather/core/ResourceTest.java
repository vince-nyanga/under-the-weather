package com.greenmoon.undertheweather.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent.
 */
public class ResourceTest {
    private static final String RESOURCE_DATA = "Data";
    private static final String ERROR_MESSAGE = "Error!";

    @Test
    public void success() throws Exception {
        Resource<String> resource = Resource.success(RESOURCE_DATA);
        assertEquals("Status should be 'SUCCESS'",Status.SUCCESS, resource.status);
        assertNull("Message should be null",resource.message);
        assertEquals("Data should be 'Data'", RESOURCE_DATA, resource.data);
    }

    @Test
    public void loading() throws Exception {
        Resource<String> resource = Resource.loading();
        assertNull("Data should be null", resource.data);
        assertNull("Message should be null", resource.message);
        assertEquals("Status should be 'LOADING'", Status.LOADING, resource.status);
    }

    @Test
    public void error() throws Exception {
        Resource<String> resource = Resource.error(ERROR_MESSAGE);
        assertEquals("Status should be 'ERROR'", Status.ERROR, resource.status);
        assertEquals("Message should be 'Error!'", ERROR_MESSAGE, resource.message);
        assertNull("Data should be null", resource.data);
    }

}