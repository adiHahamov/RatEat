package com.adiandnoy.RatEat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;

import java.util.List;

public class DishListViewModel extends ViewModel {
    private MutableLiveData<List<Dish>> stList = Model.instance.getAllDishes();

   public MutableLiveData<List<Dish>> getList(){
        return stList;
    }
}
