package com.example.gosafe;

import com.google.firebase.firestore.GeoPoint;

public class Defect {
    String id,type;
    String loc;
    String imageUrl;

    public Defect(String id, String type, String loc,String imageUrl) {
        this.id = id;
        this.type = type;
        this.loc = loc;
        this.imageUrl = imageUrl;

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

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
