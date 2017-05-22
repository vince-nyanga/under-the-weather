package com.greenmoon.undertheweather.binding;

import android.app.Activity;
import android.databinding.DataBindingComponent;

/**
 * Created by Vincent.
 */

public class ActivityDataBindingComponent implements android.databinding.DataBindingComponent {

    private final ActivityBindingAdapters adapters;

    public ActivityDataBindingComponent(Activity activity){
        adapters = new ActivityBindingAdapters(activity);
    }
    @Override
    public ActivityBindingAdapters getActivityBindingAdapters() {
        return adapters;
    }
}
