package com.example.matthes.farmero;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    // properties
    FloatingActionButton settings;
    private static final List<List<Point>> POINTS = new ArrayList<>();
    private static final List<Point> OUTER_POINTS = new ArrayList<>();
    // polygone for map
    static {
        OUTER_POINTS.add(Point.fromLngLat(14.566519260406494, 51.16942143993214));
        OUTER_POINTS.add(Point.fromLngLat( 14.571776390075684, 51.16933398636553));
        OUTER_POINTS.add(Point.fromLngLat(14.57181930541992, 51.170383418219465));
        OUTER_POINTS.add(Point.fromLngLat(14.57480192184448, 51.17024887700865));
        OUTER_POINTS.add(Point.fromLngLat(14.57510232925415,51.17326923268294));
        OUTER_POINTS.add(Point.fromLngLat(14.572194814682007,51.17347775773388));
        OUTER_POINTS.add(Point.fromLngLat(14.571744203567503,51.17388135192782));
        OUTER_POINTS.add(Point.fromLngLat(14.571443796157837,51.17345085132869));
        OUTER_POINTS.add(Point.fromLngLat(14.568450450897215,51.17406296816258));
        OUTER_POINTS.add(Point.fromLngLat(14.567999839782715,51.17329613919413));
        OUTER_POINTS.add(Point.fromLngLat(14.56661581993103,51.17307416000739));
        OUTER_POINTS.add(Point.fromLngLat(14.566519260406494,51.16942143993214));
        POINTS.add(OUTER_POINTS);
    }

    // required constructor
    public MainFragment() {
        // Required empty public constructor
    }

    // every fragment has this
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // initialize parameters
        settings = view.findViewById(R.id.nav_settings2);

        // Listener
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });




        // Create Mapbox
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // Create fragment
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Build mapboxMap
            MapboxMapOptions options = new MapboxMapOptions();
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(51.171775896696396, 14.570703506469727))
                    .zoom(14)
                    .build());

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);

            // Add map fragment to parent container
            transaction.add(R.id.sub_frame, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getFragmentManager().findFragmentByTag("com.mapbox.map");
        }



        // Create Mapbox Callback
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.SATELLITE, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments

                        // ADD the Polygon
                        style.addSource(new GeoJsonSource("source-id", Polygon.fromLngLats(POINTS)));
                        style.addLayer(new FillLayer("layer-id", "source-id").withProperties(
                                fillColor(Color.parseColor("#3CFFEA00")))
                        );
                    }
                });
            }
        });

        return view;
    }
}
