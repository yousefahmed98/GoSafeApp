package com.example.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

public class Analysis extends AppCompatActivity {

    private String city , governorate ;
    private TextView cityTxt , numDefectsTxt , numPotholeText , numAlligatorText , numTransverseText , numLongitudinalText;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private int numOfDefects, numOfPothole , numOfAlligator , numOfTransverse , numOfLongitudinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            city = bundle.getString("city");
            governorate = bundle.getString("governorate");
        }

        fStore.collection("areas").document(governorate).collection("cities").document(city).collection("defects")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                numOfDefects = numOfPothole = numOfAlligator = numOfTransverse = numOfLongitudinal =  0 ;
                for(DocumentSnapshot snapshot :task.getResult()){
                    String id = snapshot.getString("id");
                    String type = snapshot.getString("type");
                    GeoPoint locGeo = snapshot.getGeoPoint("geoPoint");
                    String imgUrl =  snapshot.getString("imgUrl");
                    String governorate =  snapshot.getString("governorate");
                    String city =  snapshot.getString("city");
                    Defect defect = new Defect( id , type , locGeo , imgUrl , governorate , city );
                    numOfDefects++;
                    if(type.contains("Pothole")){
                        numOfPothole++;
                    }
                    if(type.contains("Alligator")){
                        numOfAlligator++;
                    }
                    if(type.contains("Transverse")){
                        numOfTransverse++;
                    }
                    if(type.contains("Longitudinal")){
                        numOfLongitudinal++;
                    }
                }
                cityTxt = findViewById(R.id.cityText);
                numDefectsTxt = findViewById(R.id.numDefectsText);
                numPotholeText = findViewById(R.id.numPotholeText);
                numAlligatorText = findViewById(R.id.numAlligatorText);
                numTransverseText = findViewById(R.id.numTransverseText);
                numLongitudinalText = findViewById(R.id.numLongitudinalText);
                cityTxt.setText(governorate + " ( " + city + " )");
                numDefectsTxt.setText("Number Of Defects: " + String.valueOf(numOfDefects) );
                numPotholeText.setText("Number Of Potholes: " + String.valueOf(numOfPothole) );
                numAlligatorText.setText("Number Of Alligators: " + String.valueOf(numOfAlligator) );
                numTransverseText.setText("Number Of Transverses: " + String.valueOf(numOfTransverse) );
                numLongitudinalText.setText("Number Of Longitudinal: " + String.valueOf(numOfLongitudinal) );


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Analysis.this,"something went wrong" ,Toast.LENGTH_SHORT).show();

            }
        });
    }
}