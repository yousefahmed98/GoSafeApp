package com.example.gosafe;

import com.google.firebase.firestore.GeoPoint;

public class Defect {
    String id,type;
    GeoPoint loc;
    String imageUrl , city , governorate;


    public Defect(String id, String type, GeoPoint loc,String imageUrl,String governorate, String city) {
        this.id = id;
        this.type = type;
        this.loc = loc;
        this.imageUrl = imageUrl;
        this.governorate = governorate;
        this.city = city;

    }

    public Defect() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeoPoint getLoc() {
        return loc;
    }

    public void setLoc(GeoPoint loc) {
        this.loc = loc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
