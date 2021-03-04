package com.adiandnoy.RatEat.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DishDao {
    @Query("select * from Dish")
    LiveData<List<Dish>> getAllDishes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Dish... dishes);

    @Delete
    void delete(Dish dish);

}
