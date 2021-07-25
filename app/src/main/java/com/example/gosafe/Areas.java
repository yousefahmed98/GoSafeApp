package com.example.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Areas extends AppCompatActivity {

    private List<String> AreaList;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private int numOfClick = 0 ;
    private String governorate;
    private String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);
        ListView listView = (ListView)  findViewById(R.id.ListView);
        AreaList = new ArrayList<>();
        fStore.collection("areas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Areas.this, android.R.layout.select_dialog_singlechoice, ids);

                listView.setAdapter(arrayAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                numOfClick += 1;
                if(numOfClick == 1){
                    governorate = parent.getItemAtPosition(position).toString();
                    fStore.collection("areas").document(governorate).collection("cities")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            List<String> ids = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String id = document.getId();
                                    ids.add(id);
                                }
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Areas.this, android.R.layout.select_dialog_singlechoice, ids);

                            listView.setAdapter(arrayAdapter);
                        }
                    });
                }else if(numOfClick == 2){
                    city = parent.getItemAtPosition(position).toString();
                    numOfClick = 0;
                    Bundle bundle = new Bundle();
                    bundle.putString("city" , city);
                    bundle.putString("governorate" , governorate);
                    if(getIntent().getStringExtra("from").equals("defects")){
                        Intent intent = new Intent(Areas.this,Defects.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Areas.this,Analysis.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }


                }

            }


        });

    }
}