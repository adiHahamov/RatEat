package com.adiandnoy.RatEat.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String imageUrl;

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("id",id);
        result.put("name",name);
        result.put("lastName",lastName);
        result.put("mail",mail);
        result.put("password",password);
        result.put("imageUrl",imageUrl);
        return result;
    }

    public void fromMap(Map<String,Object> map){
//        result.put("id",id);
        id = (String) map.get("id");
        name = (String) map.get("name");
        lastName = (String) map.get("lastName");
        mail = (String) map.get("mail");
        password = (String) map.get("password");
        imageUrl = (String) map.get("imageUrl");
    }


    @NonNull
    public String getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
