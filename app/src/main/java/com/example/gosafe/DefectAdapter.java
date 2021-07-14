package com.example.gosafe;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
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
       fStore.collection("issues").document(defect.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
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
    private void notifyRemoved(int postiton){
        defectsList.remove(postiton);
        notifyItemRemoved(postiton);
        defects.showData();

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
        holder.loc.setText(defectsList.get(position).getLoc());
        String imgURL = defectsList.get(position).getImageUrl();
        Picasso.get() .load(imgURL).into(holder.defectImg);

    }

    @Override
    public int getItemCount() {
        return defectsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView type , loc, imageUrl;
        ImageView defectImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.typeText);
            loc = itemView.findViewById(R.id.locationText);
            //imageUrl = itemView.findViewById(R.id.imageUrlText);
            defectImg = itemView.findViewById(R.id.imageView);

        }
    }


}
