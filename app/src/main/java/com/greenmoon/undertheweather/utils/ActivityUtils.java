package com.greenmoon.undertheweather.utils;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.greenmoon.undertheweather.MainActivity;

/**
 * Created by Vincent.
 */

public class ActivityUtils {

    public static boolean isGoogleServicesAvailable(Activity activity){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404);
            }
            return false;
        }
        return true;
    }
}
