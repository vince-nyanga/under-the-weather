package com.greenmoon.undertheweather;

import android.Manifest;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.greenmoon.undertheweather.binding.ActivityDataBindingComponent;
import com.greenmoon.undertheweather.core.Resource;
import com.greenmoon.undertheweather.core.Status;
import com.greenmoon.undertheweather.databinding.ActivityMainBinding;
import com.greenmoon.undertheweather.di.ViewModelFactory;
import com.greenmoon.undertheweather.model.WeatherData;
import com.greenmoon.undertheweather.ui.WeatherViewModel;
import com.greenmoon.undertheweather.utils.ActivityUtils;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends LifecycleActivity implements GoogleApiClient
        .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private WeatherViewModel viewModel;

    private ActivityMainBinding binding;

    private GoogleApiClient googleApiClient;

    private boolean hasPlayServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main, new
                ActivityDataBindingComponent(this));
        viewModel = ViewModelProviders.of(this, new ViewModelFactory((WeatherApp)
                getApplicationContext())).get(WeatherViewModel.class);

        initPlayServices();
        setDate();

    }

    private void initPlayServices() {
        hasPlayServices = ActivityUtils.isGoogleServicesAvailable(MainActivity.this);
        if (hasPlayServices) {
            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            }
        }
    }

    private void setDate() {
        TextView dateTextView = (TextView) findViewById(R.id.date_tv);
        String dateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
        dateTextView.setText(String.format(getString(R.string.date), dateString));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null) {
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);
            googleApiClient.disconnect();
        }

    }

    protected void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        if (hasPlayServices) {
            if(googleApiClient.isConnected()) {
                Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                if (location != null) {
                   requestWeather(location.getLatitude(), location.getLongitude());
                }
            }
        }else{
            // In a production app we 'silently fail' and fall back to using LocationManager.
            binding.setWeatherResource(Resource.error(getResources().getString(R.string
                    .no_play_services)));
        }

    }

    @VisibleForTesting
    protected void requestWeather(double lat, double lon){
        viewModel.getWeather(lat, lon).observe
                (this,
                        new Observer<Resource<WeatherData>>() {
                            @Override
                            public void onChanged(@Nullable Resource<WeatherData>
                                                          weatherDataResource) {
                                bindData(weatherDataResource);
                            }
                        });
    }

    @VisibleForTesting
    protected void bindData(Resource<WeatherData> weatherDataResource){
        binding.setWeatherResource(weatherDataResource);
        binding.setWeather(weatherDataResource.data);
        if (weatherDataResource.status == Status.SUCCESS) {
            binding.setWeatherIcon(weatherDataResource.data.weather.get(0));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
