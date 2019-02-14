package com.example.matthes.farmero;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity {

    Button popUp;
    Button btnSoil;
    Button btnTakePic;
    Button btnproducts;
    ImageView btnTakePicImage1;
    String pathToFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //constructor
        super.onCreate(savedInstanceState);

        // render main layout
        setContentView(R.layout.activity_main);

        // set variables
        popUp = findViewById(R.id.btnSugar);
        btnSoil = findViewById(R.id.btnSoil);
        btnTakePic = findViewById(R.id.btnTakePic);
        btnproducts = findViewById(R.id.btnProducts);
        btnTakePicImage1 = findViewById(R.id.btnTakePicImage1);

        // require permission to save pictures on phone
        if(Build.VERSION.SDK_INT >= 23){
            Log.d("test","test");
            requestPermissions(new  String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }

        //#################### LISTENERS #######################

        // Listener for taking picture
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPictureTakerAction();
            }
        });

        // Listener for popUp
        popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Pop.class));
            }
        });

        // Listener for popUp
        btnSoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, WebViewMap.class));
            }
        });

        btnproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                btnTakePicImage1.setImageBitmap(bitmap);
            }
        }
    }


    // take picture function 1
    private void dispatchPictureTakerAction() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            photoFile = createPhotoFile();
            if(photoFile != null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this,"com.example.matthes.farmero.fileprovider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePic,1);
            }
        }
    }

    // take picture function 1
    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("myLog","Excep: " + e.toString());
        }
        return image;
    }

}
