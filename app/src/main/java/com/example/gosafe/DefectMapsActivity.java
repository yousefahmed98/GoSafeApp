package com.example.gosafe;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.gosafe.databinding.ActivityDefectMapsBinding;
import com.google.firebase.firestore.GeoPoint;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DefectMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityDefectMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDefectMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        String id,type,imageUrl,lat,lng,city,governorate;
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
             id = bundle.getString("id");
             type = bundle.getString("type");
             imageUrl = bundle.getString("imageUrl");
             lat = bundle.getString("lat");
             lng = bundle.getString("lng");
             city = bundle.getString("city");
             governorate = bundle.getString("governorate");
        }
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
        //TextView typeText = findViewById(R.id.typeText);
       // TextView locationText = findViewById(R.id.locationText);
        ImageView defectImg = findViewById(R.id.imageView);
        String id,type,imageUrl,lat,lng;
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        type = bundle.getString("type");
        imageUrl = bundle.getString("imageUrl");
        lat = bundle.getString("lat");
        lng = bundle.getString("lng");
        //Picasso.get() .load(imageUrl).into((Target) DefectMapsActivity.this);
        ///typeText.setText(type);
        //String location = lat + "," + lng;
        //locationText.setText(location);a
        Picasso.get() .load(imageUrl).into(defectImg);
        // Add a marker in Sydney and move the camera

        LatLng sydney = new LatLng(Float.parseFloat(lat),Float.parseFloat(lng));
        mMap.addMarker(new MarkerOptions().position(sydney).title(type));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}