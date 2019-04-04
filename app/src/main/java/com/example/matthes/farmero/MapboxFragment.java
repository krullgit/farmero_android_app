package com.example.matthes.farmero;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

/**
 * The class:
 * - loads a mapbox map
 * - shows diseases in my fields and the fields of friends.
 */

public class MapboxFragment extends Fragment {


    // properties
    FloatingActionButton settings;
    ImageView field;
    LinearLayout buttonYield;
    LinearLayout buttonLoss;
    LinearLayout buttonMildrew;
    ImageView buttonnvdi;
    ImageView buttonrgb;
    private static final List<List<Point>> POINTS = new ArrayList<>();
    private static final List<Point> OUTER_POINTS = new ArrayList<>();
    FloatingActionButton polygone;
    private static String pointsFile = "/storage/emulated/0/Download/points.txt";
    private SeekBar seekBar;
    ImageView imagesliderfirst;
    ImageView imageslidersecond;
    private DatabaseReference databaseReference;
    List<StoredDisease> StoredDiseaseList;
    private FrameLayout pnlFlash;
    FloatingActionButton info;



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

        // initialize parameters
        pnlFlash = (FrameLayout) view.findViewById(R.id.pnlFlash);
        info = view.findViewById(R.id.info);
        databaseReference = FirebaseDatabase.getInstance().getReference("diseases");
        StoredDiseaseList = new ArrayList<>();

        // Listener
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show welcome message
                Snackbar snackbar = Snackbar.make(view.findViewById(R.id.myCoordinatorLayout),
                        "All diseases are shown here; Your own and the ones from your friends.", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                snackbar.show();
            }
        });








        /// Create Mapbox
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // Create fragment
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Build mapboxMap
            MapboxMapOptions options = new MapboxMapOptions();
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(51.575037, 12.946546))
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

                // Create List with coordinates for polygone
                POINTS.clear();
                OUTER_POINTS.clear();
                String coordString = readSavedData();
                Log.d("coordString: ", "" + coordString);
                // Open File
                String coordStringList[] = coordString.split("#");
                Log.d("coordString.length: ", "" + coordString.length());
                Log.d("coordString.length: ", "" + coordStringList.toString());
                int i;
                for (i = 0; i < coordStringList.length; i++) {
                    double lon;
                    double lat;
                    try {
                        lon = Double.parseDouble(coordStringList[i].split(",")[0]);
                        lat = Double.parseDouble(coordStringList[i].split(",")[1]);
                    } catch (NumberFormatException e) {
                        lon = 0;
                        lat = 0;
                    }
                    //mapboxMap.addMarker(new MarkerOptions()
                    //        .position(new LatLng(lat, lon))
                    //        .title("Eiffel Tower"));
                    OUTER_POINTS.add(Point.fromLngLat(lon, lat));
                }
                POINTS.add(OUTER_POINTS);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot diseaseSnapshot : dataSnapshot.getChildren()) {
                            StoredDisease disease = diseaseSnapshot.getValue(StoredDisease.class);
                            StoredDiseaseList.add(disease);
                        }

                        for (StoredDisease disease : StoredDiseaseList) {

                            // the next 2 line avoid an exception (Fragment not attached to Activity)
                            Activity activity = getActivity();
                            if (isAdded() && activity != null) {
                                // get the name of the plant to select the correct icon
                                String diseaseIcon = disease.diseaseName.split(" ")[0].toLowerCase();
                                int drawableId = getResources().getIdentifier(diseaseIcon, "drawable", "com.example.matthes.farmero");

                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(disease.diseaselat), Double.parseDouble(disease.diseaselon)))
                                        .icon(IconFactory.getInstance(getActivity()).fromResource(drawableId))
                                        .title(disease.diseaseName + "\n" + disease.diseasDate));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


//                mapboxMap.addSource(source);
//                mapboxMap.addLayer(new LineLayer("geojson", "geojson"));

                //MarkerOptions options = new MarkerOptions();
                //options.title("cwefewf");
                //options.position(new LatLng(51.171775896696396, 14.570703506469727));


                //mapboxMap.addMarker(options);


//                mapboxMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(51.574903, 12.942841))
//                        .icon(IconFactory.getInstance(getActivity()).fromResource(R.drawable.squash))
//                        .title("Squash Powdery Mildrew"));
//
//                mapboxMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(51.579692, 12.954649))
//                        .icon(IconFactory.getInstance(getActivity()).fromResource(R.drawable.maize))
//                        .title("Maize Gray Spot"));
//
//                mapboxMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(51.561064, 12.916913))
//                        .icon(IconFactory.getInstance(getActivity()).fromResource(R.drawable.bell))
//                        .title("Bell Pepper Bacterial Spot"));

                mapboxMap.setStyle(Style.SATELLITE, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {


//
//                        // ADD the Polygon
//                        style.addSource(new GeoJsonSource("source-id", Polygon.fromLngLats(POINTS)));
//                        style.addLayer(new FillLayer("layer-id", "source-id").withProperties(
//                                fillColor(Color.parseColor("#3CFFEA00")))
//                        );

                        style.addSource(new GeoJsonSource("source-id", Polygon.fromLngLats(POINTS)));

                        Log.d("OUTER_POINTS: ", "" + OUTER_POINTS);
                        style.addSource(new GeoJsonSource("line-source",
                                FeatureCollection.fromFeatures(new Feature[]{Feature.fromGeometry(
                                        LineString.fromLngLats(OUTER_POINTS)
                                )})));

                        style.addLayer(new FillLayer("layer-id", "source-id").withProperties(
                                fillColor(Color.parseColor("#22ffc20c")))
                        );

                        style.addLayer(new LineLayer("linelayer", "line-source").withProperties(
                                PropertyFactory.lineDasharray(new Float[]{0.01f, 2f}),
                                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                                PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                                PropertyFactory.lineWidth(2.3f),
                                PropertyFactory.lineColor(Color.parseColor("#ffc20c"))
                        ));

                    }
                });
            }


        });
        return view;
    }

    private String readFromFile(String fileName) {
        InputStream in = null;
        try {
            in = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return String.valueOf(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readSavedData() {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = getActivity().openFileInput("fields_field_1");
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader buffreader = new BufferedReader(isr);

            String readString = buffreader.readLine();
            while (readString != null) {
                datax.append(readString);
                readString = buffreader.readLine();
            }

            isr.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return datax.toString();

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
