package com.isep.android.weatherapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class WeatherMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng coordinate) {
        removeMarker();
        marker = mMap.addMarker(new MarkerOptions().position(coordinate).title("The coordinates of the point are:"));

        Bundle args = new Bundle();
        args.putParcelable("mapCoordinate", coordinate);
        Intent intent = new Intent(WeatherMapActivity.this,WeatherInfoActivity.class);
        intent.putExtras(args);
        startActivity(intent);
    }

    public void removeMarker() {
        if (marker != null) {
            marker.remove();
            marker = null;
        }
    }
}
