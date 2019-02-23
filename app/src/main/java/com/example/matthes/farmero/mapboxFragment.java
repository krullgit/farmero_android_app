package com.example.matthes.farmero;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

/**
 * Include a map fragment within your app using Android support library.
 */
public class mapboxFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_support_map_frag);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, "pk.eyJ1IjoiZmFybWVybyIsImEiOiJjanJ6Zmd5cGcxODFzNDNsdnB0dW16bXh6In0.F6w_diZUKuqmcHGc1QNOGw");

        // Create supportMapFragment
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // Create fragment
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Build mapboxMap
            MapboxMapOptions options = new MapboxMapOptions();
            options.camera(new CameraPosition.Builder()
                .target(new LatLng(-52.6885, -70.1395))
                .zoom(9)
                .build());

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);

            // Add map fragment to parent container
            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.SATELLITE, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                    // Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }
                });
            }
        });
    }
}