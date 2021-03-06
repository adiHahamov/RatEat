package com.adiandnoy.RatEat;

import android.icu.lang.UScript;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.adiandnoy.RatEat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DishListViewModel extends ViewModel {
    private LiveData<List<Dish>> stList = Model.instance.getAllDishes();
    private LiveData<List<User>> userLisr = Model.instance.getAllUsers();
    private LiveData<List<Dish>> stPersonList = new MutableLiveData<>();

    public LiveData<List<Dish>> getList(){
        return stList;
    }
    public LiveData<List<User>> getUserList(){
        return userLisr;
    }

}
