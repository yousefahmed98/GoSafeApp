package com.example.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Defects extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private DefectAdapter adapter;
    private List<Defect> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defects);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new DefectAdapter(this,list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);
        showData();
    }

    public void showData(){

        fStore.collection("areas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                for(DocumentSnapshot snapshot :task.getResult()){
                    String id = snapshot.getString("id");
                    String type = snapshot.getString("type");
                    GeoPoint locGeo = snapshot.getGeoPoint("geoPoint");
                    String imgUrl =  snapshot.getString("imgUrl");
                    String governorate =  snapshot.getString("imgUrl");
                    String city =  snapshot.getString("imgUrl");
                    Defect defect = new Defect( id , type , locGeo , imgUrl , governorate , city );
                    list.add(defect);
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Defects.this,"something went wrong" ,Toast.LENGTH_SHORT).show();

            }
        });
    }

}