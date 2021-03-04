package com.adiandnoy.RatEat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;

import java.util.List;

public class DishListViewModel extends ViewModel {
    private LiveData<List<Dish>> stList = Model.instance.getAllDishes();

   public LiveData<List<Dish>> getList(){
        return stList;
    }
}
