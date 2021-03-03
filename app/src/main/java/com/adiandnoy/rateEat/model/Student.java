package com.adiandnoy.rateEat.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Student {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String phone;
    private String imageUrl;
    private Long lastUpdated;


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("phone", phone);
        result.put("imageUrl", imageUrl);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map){
        id = (String)map.get("id");
        name = (String)map.get("name");
        imageUrl = (String)map.get("imageUrl");
        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
        //long time = ts.toDate().getTime();
    }


    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
