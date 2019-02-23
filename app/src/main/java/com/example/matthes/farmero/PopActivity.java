package com.example.matthes.farmero;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // constructor
        super.onCreate(savedInstanceState);

        // set xml
        setContentView(R.layout.activity_popwindow);

        // settings for the popup effect
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width-100),(int) (height-300));
    }
}
