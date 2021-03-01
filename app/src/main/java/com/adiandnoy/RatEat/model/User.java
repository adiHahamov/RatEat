package com.adiandnoy.RatEat.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
