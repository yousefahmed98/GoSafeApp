package com.example.gosafe;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.gosafe.databinding.ActivityAreaDefectsMapBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AreaDefectsMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityAreaDefectsMapBinding binding;
    private String city , governorate ;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private List<Defect> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAreaDefectsMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            city = bundle.getString("city");
            governorate = bundle.getString("governorate");
        }
        list = new ArrayList<>();
        list.clear();
        fStore.collection("areas").document(governorate).collection("cities").document(city).collection("defects").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull  Task<QuerySnapshot> task) {
                        for(DocumentSnapshot snapshot :task.getResult()){
                            String id = snapshot.getString("id");
                            String type = snapshot.getString("type");
                            GeoPoint locGeo = snapshot.getGeoPoint("geoPoint");
                            String imgUrl =  snapshot.getString("imgUrl");
                            String governorate =  snapshot.getString("governorate");
                            String city =  snapshot.getString("city");
                            Defect defect = new Defect( id , type , locGeo , imgUrl , governorate , city );
                            list.add(defect);
                        }
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(AreaDefectsMap.this);
                    }
                });



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println(list.size());
        // Add a marker in Sydney and move the camera
        for (int i = 0 ; i <list.size(); i ++){
            GeoPoint loc = list.get(i).getLoc();
            LatLng defectLoc = new LatLng(loc.getLatitude(), loc.getLongitude());
            mMap.addMarker(new MarkerOptions().position(defectLoc).title(list.get(i).getType()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(defectLoc));
        }

    }
}