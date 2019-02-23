package com.example.matthes.farmero;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapboxFragment extends Fragment {


    // required constructor
    public MapboxFragment() {
        // Required empty public constructor
    }

    // every fragment has this
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mapbox, container, false);

        /// Create Mapbox
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
            transaction.add(R.id.mapView, mapFragment, "com.mapbox.map");
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

                        // for current location
                        /*enableLocationComponent(style);*/


                        /*MarkerOptions options = new MarkerOptions();
                        options.title("cwefewf");
                        options.position(new LatLng(51.171775896696396, 14.570703506469727));
                        mapboxMap.addMarker(options);


                        // Add the marker image to map
                        style.addImage("marker-icon-id",
                                BitmapFactory.decodeResource(
                                        MainActivity.this.getResources(), R.drawable.camera));

                        GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                                Point.fromLngLat(51.171775896696396, 14.570703506469727)));
                        style.addSource(geoJsonSource);

                        SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                        symbolLayer.withProperties(
                                PropertyFactory.iconImage("marker-icon-id")
                        );
                        style.addLayer(symbolLayer);
                        */

                    }
                });
            }
        });
        return view;
    }
}
