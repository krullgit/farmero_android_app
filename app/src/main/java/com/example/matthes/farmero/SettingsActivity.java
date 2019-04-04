package com.example.matthes.farmero;


import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * The class:
 * - shows the view "settings" to the farmer
 * - gives logout functionality
 */

public class SettingsActivity extends AppCompatActivity {

    //parameter
    Button btnLogOut;
    private EditText v;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
//    private FusedLocationProviderClient mFusedLocationProviderClient;
//    private Boolean mLocationPermissionsGranted = true;
//    Double lon;
//    Double lat;
//    DatabaseReference databaseDiseases;

    // constructor
    public SettingsActivity() {
        // Required empty public constructor
    }


//    // from here https://www.youtube.com/watch?v=fPFr0So1LmI
//    private void getDeviceLocation(){
//        Log.d("Location:", "getDeviceLocation: getting the devices current location");
//
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        try{
//            if(mLocationPermissionsGranted){
//
//                final Task location = mFusedLocationProviderClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if(task.isSuccessful()){
//                            Log.d("Location:", "onComplete: found location!");
//                            Location currentLocation = (Location) task.getResult();
//
//
//                            //Log.d("lat",""+currentLocation.getLatitude());
//                            //Log.d("lon",""+currentLocation.getLongitude());
//                            lon = currentLocation.getLongitude();
//                            lat = currentLocation.getLatitude();
//
//                        }else{
//                            Log.d("Location:", "onComplete: current location is null");
//                            Toast.makeText(SettingsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        }catch (SecurityException e){
//            Log.e("Location:", "getDeviceLocation: SecurityException: " + e.getMessage() );
//        }
//    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //constructor
        super.onCreate(savedInstanceState);

        // set xml
        setContentView(R.layout.activity_settings);

        // set variables
        btnLogOut = findViewById(R.id.btnLogOut);
//        Double lon = 0.0;
//        Double lat = 0.0;

        // Listener
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(I);
            }
        });

        // get Location
        //getDeviceLocation();

        // safe disease in Firebase
//        databaseDiseases = FirebaseDatabase.getInstance().getReference("diseases");
//        String id = databaseDiseases.push().getKey();
//        StoredDisease disease = new StoredDisease("Squash Powdery Mildrew",""+12.942841,""+51.574903,"01.04.2019");
//        databaseDiseases.child(id).setValue(disease);
//        String id2 = databaseDiseases.push().getKey();
//        StoredDisease disease2 = new StoredDisease("Maize Gray Spot",""+12.954649,""+51.579692,"01.04.2019");
//        databaseDiseases.child(id2).setValue(disease2);
//        String id3 = databaseDiseases.push().getKey();
//        StoredDisease disease3 = new StoredDisease("Bell Pepper Bacterial Spot",""+12.916913,""+51.561064,"01.04.2019");
//        databaseDiseases.child(id3).setValue(disease3);

        // get user email and place it inside the xml
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getEmail();
        v = (EditText) findViewById(R.id.email);
        SharedPreferences settings = this.getSharedPreferences("PREFS", 0);
        v.setText(settings.getString("value", userid));
    }
}















///**
// * A simple {@link Fragment} subclass.
// */
//public class SettingsActivity extends Fragment {
//
//    Button btnLogOut;
//    private EditText v;
//    FirebaseAuth firebaseAuth;
//    private FirebaseAuth.AuthStateListener authStateListener;
//
//    public SettingsActivity() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.activity_settings, container, false);
//
//        // set variables
//        btnLogOut = view.findViewById(R.id.btnLogOut);
//
//        btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent I = new Intent(getActivity(), LoginActivity.class);
//                startActivity(I);
//            }
//        });
//
//        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        String userid=user.getEmail();
//
//        v = (EditText) view.findViewById(R.id.email);
//        SharedPreferences settings = this.getActivity().getSharedPreferences("PREFS", 0);
//        v.setText(settings.getString("value", userid));
//
//
//        return view;
//
//    }
//
//}
