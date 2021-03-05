package com.adiandnoy.RatEat.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firestore.v1.DocumentTransform;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Dish {
    @PrimaryKey
    @NonNull
    private String id;
    private String dishName;
    private String resturantName;
    private String ingredients;
    private String dishDescription;
    private String imageUrl;
    private String userID;
    private Double stars;
    private Long lastUpdated;

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("id",id);
        result.put("dishName",dishName);
        result.put("resturantName",resturantName);
        result.put("ingredients",ingredients);
        result.put("dishDescription",dishDescription);
        result.put("imageUrl",imageUrl);
        result.put("stars",stars);
        result.put("userID",userID);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String,Object> map){
//        result.put("id",id);
        id = (String) map.get("id");
        dishName = (String) map.get("dishName");
        resturantName = (String) map.get("resturantName");
        ingredients = (String) map.get("ingredients");
        dishDescription = (String) map.get("dishDescription");
        imageUrl = (String) map.get("imageUrl");
        stars = (Double) map.get("stars");
        userID = (String)map.get("userID");
        Timestamp timestamp =  (Timestamp)map.get("lastUpdated");
        lastUpdated = timestamp.getSeconds();
    }


    @NonNull
    public String getId() {
        return id;
    }

    public String getDishName() {
        return dishName;
    }

    public String getResturantName() {
        return resturantName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
