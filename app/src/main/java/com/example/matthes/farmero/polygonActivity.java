package com.example.matthes.farmero;





import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class polygonActivity extends AppCompatActivity implements OnMapReadyCallback{

    private FrameLayout frameLayout;

    private GoogleMap googleMap;
    private Polyline polyline = null;
    private Polygon polygon = null;


    private Button buttonPolygon;
    private Button buttonClear;
    private Button save;

    private static String pointsFile = "/storage/emulated/0/Download/points.txt";





    //private final LatLng beirutLatLng = new LatLng(33.893865, 35.501175);
    private final LatLng beirutLatLng = new LatLng(51.17163463268039, 14.57087516784668);
    //private final MarkerOptions beirutMarker = new MarkerOptions().position(beirutLatLng);

    private List<LatLng> listLatLngs = new ArrayList<>();
    private List<Marker> listMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon);
        // Construct a PlaceDetectionClient.

        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.googleMap);
        supportMapFragment.getMapAsync(polygonActivity.this);
        setupUI();
        setButtonPolylineListener();
        setButtonPolygonListener();
        setButtonClearListener();

    }

    private void setupUI(){
        frameLayout = findViewById(R.id.frameLayout);

        buttonPolygon = findViewById(R.id.buttonPolygon);
        buttonClear = findViewById(R.id.buttonReset);
        save = findViewById(R.id.buttonLocation);
    }

    private void setButtonPolylineListener() {

        buttonPolygon.setOnClickListener(e -> connectPolygon());
        buttonClear.setOnClickListener(e -> resetMap());
        save.setOnClickListener(e -> savePolygon());
    }

    private void savePolygon() {
        Iterator<LatLng> crunchifyIterator = listLatLngs.iterator();
        String filename = "fields_field_1";

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            while (crunchifyIterator.hasNext()) {
                outputStream.write((crunchifyIterator.next().latitude + "," + crunchifyIterator.next().longitude + "\n").getBytes());
            }
            outputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setButtonPolygonListener() {
        buttonPolygon.setOnClickListener(e -> connectPolygon());
    }

    private void setButtonClearListener() {
        buttonClear.setOnClickListener(e -> resetMap());
    }








    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;



        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(beirutLatLng, 15.0f));
        //map view
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        setGoogleMapClickListener();
        setCircleClickListener();
        setPolylineClickListener();
        setPolygonClickListener();
    }



    private void setGoogleMapClickListener(){
        googleMap.setOnMapClickListener(e -> {
            MarkerOptions markerOptions = new MarkerOptions().position(e);
            //markerOptions.draggable(true);
            //markerOptions.alpha(50);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.polygone_marker));
            Marker marker = googleMap.addMarker(markerOptions);
            System.out.println("FA");
            System.out.println(marker.getPosition());
            listMarkers.add(marker);
            listLatLngs.add(e);

        });
    }


    private void setCircleClickListener(){
        googleMap.setOnCircleClickListener(e ->{
            Snackbar.make(frameLayout, "Circle clicked!", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void setPolylineClickListener(){
        googleMap.setOnPolylineClickListener(e -> {
            Snackbar.make(frameLayout, "Polyline clicked!", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void setPolygonClickListener(){
        googleMap.setOnPolygonClickListener(e -> {
            Snackbar.make(frameLayout, "Polygon clicked!", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void connectPolyline(){
        if (polyline != null)
            polyline.remove();
        PolylineOptions polylineOptions = new PolylineOptions().addAll(listLatLngs).clickable(true).color(R.color.colorPrimary);

        polyline = googleMap.addPolyline(polylineOptions);

    }

    private void connectPolygon(){
        if (polygon != null)
            polygon.remove();
        PolygonOptions polygonOptions = new PolygonOptions().addAll(listLatLngs).clickable(true);
        polygon = googleMap.addPolygon(polygonOptions);
        polygon.setStrokeColor(R.color.colorWhite);





        ///data/user/0/com.example.matthes.farmero/files
        //File directory = getApplicationContext().getFilesDir();
        //Log.d("currentDir:", directory.toString());

    }

    private void resetMap(){
        if (polyline != null) polyline.remove();
        if (polygon != null) polygon.remove();

        for (Marker marker : listMarkers) marker.remove();
        listMarkers.clear();
        listLatLngs.clear();
    }








    private void unFillPolygon(){
        polygon.setFillColor(Color.parseColor("#00000000"));
    }


    /**
     * Gets the current location of the device, and positions the map's camera.
     */



}