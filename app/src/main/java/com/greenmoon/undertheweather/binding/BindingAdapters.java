package com.greenmoon.undertheweather.binding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by Vincent.
 */

public class BindingAdapters {

    /**
     * Sets the Visibility of a View
     * @param view
     * @param show
     */
    @BindingAdapter("show")
    public static void show(View view, boolean show){
        view.setVisibility(show? View.VISIBLE: View.GONE);
    }
}
