package com.example.matthes.farmero;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // parameter
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private MainFragment mainFragment;
    private FriendsFragment friendsFragment;
    private PhotoFragment photoFragment;
    private MapboxFragment mapboxFragment;
    private SettingsActivity settingsFragment;
    private List<Point> routeCoordinates;

    // Create Buttom Navigation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menue_upper_right, menu);
        return true;
    }

    // like a constructor
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        // run super constructor
        super.onCreate(savedInstanceState);

        // get mapbox instance with key
        Mapbox.getInstance(this, "pk.eyJ1IjoiZmFybWVybyIsImEiOiJjanJ6Zmd5cGcxODFzNDNsdnB0dW16bXh6In0.F6w_diZUKuqmcHGc1QNOGw");

        // render main layout
        setContentView(R.layout.activity_main);

        // initialize parameters
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mainFragment = new MainFragment();
        photoFragment = new PhotoFragment();
        settingsFragment = new SettingsActivity();
        friendsFragment = new FriendsFragment();
        mapboxFragment = new MapboxFragment();

        // set default view to main page
        setFragment(mainFragment);

        // Event handler for bottom navigation bar
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        setFragment(mainFragment);
                        return true;
                    case R.id.nav_photo:
                        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                        startActivity(intent);
                        //setFragment(photoFragment);
                        return true;
                    case R.id.nav_map:
                        setFragment(mapboxFragment);
                        return true;
                    case R.id.nav_friends:
                        setFragment(friendsFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

        // require permission to save pictures on phone
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("fragment_camera2_basic", "fragment_camera2_basic");
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
    }

    // FUNCTION to replace the fragment according to the nav bar
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}


//package com.example.matthes.farmero;
//
//import android.Manifest;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.net.wifi.hotspot2.pps.HomeSp;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static android.os.Environment.getExternalStoragePublicDirectory;
//
//public class MainActivity extends AppCompatActivity {
//
//    Button popUp;
//    Button btnSoil;
//    Button btnTakePic;
//    Button btnYield;
//    Button btnproducts;
//    ImageView btnTakePicImage1;
//    String pathToFile;
//
//    private BottomNavigationView mMainNav;
//    private FrameLayout mMainFrame;
//
//    private MainFragment mainFragment;
//    private SettingsActivity settingsFragment;
//    private MapFragment mapFragment;
//    private PhotoFragment photoFragment;
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menue_upper_right, menu);
//
//
//        return true;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        //constructor
//        super.onCreate(savedInstanceState);
//
//        // render main layout
//        setContentView(R.layout.activity_main);
//
//        // set variables
//        popUp = findViewById(R.id.btnSugar);
//        btnSoil = findViewById(R.id.btnSoil);
//        btnTakePic = findViewById(R.id.btnTakePic);
//        btnproducts = findViewById(R.id.btnProducts);
//        btnTakePicImage1 = findViewById(R.id.btnTakePicImage1);
//        btnYield = findViewById(R.id.btnYield);
//
//        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
//        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
//        mainFragment = new MainFragment();
//        mapFragment = new MapFragment();
//        photoFragment = new PhotoFragment();
//        settingsFragment = new SettingsActivity();
//
//
//        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.nav_home :
//                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
//                        setFragment(mainFragment);
//                        return true;
//                    case R.id.nav_photo :
//                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
//                        setFragment(photoFragment);
//                        return true;
//                    case R.id.nav_map :
//                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
//                        setFragment(mapFragment);
//                        return true;
//                    case R.id.nav_settings:
//                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
//                        setFragment(settingsFragment);
//                        return true;
//                        default:
//                            return false;
//                }
//            }
//        });
//
//        // require permission to save pictures on phone
//        if(Build.VERSION.SDK_INT >= 23){
//            Log.d("fragment_camera2_basic","fragment_camera2_basic");
//            requestPermissions(new  String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
//        }
//
//        //#################### LISTENERS #######################
//
//        // Listener for taking picture
//        btnTakePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dispatchPictureTakerAction();
//            }
//        });
//
//        // Listener for popUp
//        popUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, PopActivity.class));
//            }
//        });
//
//        // Listener for popUp
//        btnSoil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(MainActivity.this, DELETEWebViewMap.class));
//            }
//        });
//
//        btnproducts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
//            }
//        });
//        btnYield.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
//
//    private void setFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.main_frame, fragment);
//        fragmentTransaction.commit();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK){
//            if(requestCode == 1){
//                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
//                btnTakePicImage1.setImageBitmap(bitmap);
//            }
//        }
//    }
//
//
//    // take picture function 1
//    private void dispatchPictureTakerAction() {
//        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(takePic.resolveActivity(getPackageManager()) != null){
//            File photoFile = null;
//            photoFile = createPhotoFile();
//            if(photoFile != null){
//                pathToFile = photoFile.getAbsolutePath();
//                Uri photoURI = FileProvider.getUriForFile(MainActivity.this,"com.example.matthes.farmero.fileprovider",photoFile);
//                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
//                startActivityForResult(takePic,1);
//            }
//        }
//    }
//
//    // take picture function 1
//    private File createPhotoFile() {
//        String name = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
//        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File image = null;
//        try {
//            image = File.createTempFile(name, ".jpg", storageDir);
//        } catch (IOException e) {
//            Log.d("myLog","Excep: " + e.toString());
//        }
//        return image;
//    }
//
//}
