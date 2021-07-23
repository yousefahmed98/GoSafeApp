package com.example.gosafe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DefectAdapter extends RecyclerView.Adapter<DefectAdapter.MyViewHolder> {

    private Defects defects;
    private List<Defect> defectsList;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    public DefectAdapter(Defects defects, List<Defect> defectsList) {
        this.defects = defects;
        this.defectsList = defectsList;
    }
    public void deleteDefect(int position){
        Defect defect = defectsList.get(position);
        fStore.collection("areas").document(defect.getGovernorate()).collection("cities").document(defect.getCity()).collection("defects")
                .document(defect.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull  Task<Void> task) {
            if(task.isSuccessful()){
                notifyRemoved(position);
                Toast.makeText(defects,"defect deleted" ,Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(defects,"error "+ task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
            }
           }
        });
    }

    public void defectLoc(int position){
        Defect defect = defectsList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id" , defect.getId());
        bundle.putString("type" , defect.getType());
        bundle.putString("imageUrl" , defect.getImageUrl());
        bundle.putString("governorate" , defect.getGovernorate());
        bundle.putString("city" , defect.getCity());
        double lat = defect.getLoc().getLatitude();
        double lng = defect.getLoc().getLongitude();
        bundle.putString("lat" , String.valueOf(lat));
        bundle.putString("lng" , String.valueOf(lng));
        Intent intent = new Intent(defects,DefectMapsActivity.class);
        intent.putExtras(bundle);
        defects.startActivity(intent);
        defects.showData(defect.getCity());
    }

    public void fixDefect(int position){
        Defect defect = defectsList.get(position);
        fStore.collection("fixed").document(defect.getId()).set(defect).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if(task.isSuccessful()){
                    deleteDefect(position);
                    notifyRemoved(position);
                    Toast.makeText(defects,"defect fixed" ,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(defects,"error "+ task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void notifyRemoved(int position){
        String city = defectsList.get(position).getCity();
        defectsList.remove(position);
        notifyItemRemoved(position);
        defects.showData(city);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(defects).inflate(R.layout.defect,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.type.setText(defectsList.get(position).getType());
        GeoPoint locGeo = defectsList.get(position).getLoc();
        double lat = locGeo.getLatitude();
        double lng = locGeo.getLongitude();
        String location = String.valueOf(lat) + "," + String.valueOf(lng);
        holder.loc.setText(location);
        String imgURL = defectsList.get(position).getImageUrl();
        Picasso.get() .load(imgURL).into(holder.defectImg);

    }

    @Override
    public int getItemCount() {
        return defectsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView type , loc;
        ImageView defectImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.typeText);
            loc = itemView.findViewById(R.id.locationText);
            defectImg = itemView.findViewById(R.id.imageView);

        }
    }


}
