package com.example.matthes.farmero;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.light.Position;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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

//                mapboxMap.addSource(source);
//                mapboxMap.addLayer(new LineLayer("geojson", "geojson"));

                //MarkerOptions options = new MarkerOptions();
                //options.title("cwefewf");
                //options.position(new LatLng(51.171775896696396, 14.570703506469727));


                //mapboxMap.addMarker(options);


                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(51.171775896696396, 14.570703506469727))
                        .title("Eiffel Tower"));

                mapboxMap.setStyle(Style.SATELLITE, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {


                        // for current location
                        /*enableLocationComponent(style);*/


/*
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

    private static String convertStreamToString(InputStream is) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(Uri fileUri, Context context) throws Exception {
        InputStream fin = context.getContentResolver().openInputStream(fileUri);

        String ret = convertStreamToString(fin);

        fin.close();
        return ret;
    }
}
