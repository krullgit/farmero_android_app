package com.example.matthes.farmero;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    Button popUp;
    Button btnSoil;
    Button btnTakePic;
    Button btnYield;
    Button btnproducts;
    ImageView btnTakePicImage1;
    String pathToFile;


    public MainFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_main, container, false);



        // set variables
        popUp = view.findViewById(R.id.btnSugar);
        btnSoil = view.findViewById(R.id.btnSoil);
        btnTakePic = view.findViewById(R.id.btnTakePic);
        btnproducts = view.findViewById(R.id.btnProducts);
        btnTakePicImage1 = view.findViewById(R.id.btnTakePicImage1);
        btnYield = view.findViewById(R.id.btnYield);



        return view;

    }

}
