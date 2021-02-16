package com.adiandnoy.RatEat.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dish {
    @PrimaryKey
    @NonNull
    private String id;
    private String dishName;
    private String resturantName;
    private String ingredients;
    private String dishDescription;

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
}
