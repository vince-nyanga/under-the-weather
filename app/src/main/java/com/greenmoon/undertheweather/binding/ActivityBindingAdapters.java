package com.greenmoon.undertheweather.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greenmoon.undertheweather.BuildConfig;
import com.greenmoon.undertheweather.R;

import java.text.DecimalFormat;

/**
 * Created by Vincent.
 */

public class ActivityBindingAdapters {

    final Activity activity;

    public ActivityBindingAdapters(Activity activity){
        this.activity = activity;
    }

    /**
     * Uses Glide to load the weather icon
     * @param imageView
     * @param icon
     */
    @BindingAdapter("icon")
    public void bindIcon(ImageView imageView, String icon){
        Glide.with(activity).load(String.format(BuildConfig.OPEN_WEATHER_ICON_URL, icon)).into
                (imageView);
    }

    /**
     * Formats and sets the temperature to TextView
     * @param textView
     * @param temperature
     */
    @BindingAdapter("temperature")
    public void setTemperature(TextView textView, double temperature){
        DecimalFormat format = new DecimalFormat("###");
        textView.setText(String.format(activity.getResources().getString(R.string.temperature),
                format.format(temperature)));
    }
}
