package com.adiandnoy.RatEat;

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

public class GenericViewModel extends ViewModel {
    private LiveData<List<Dish>> stList = Model.instance.getAllDishes();
    private LiveData<List<User>> user = Model.instance.getAllUsers();

    public LiveData<List<Dish>> getList(){
        return stList;
    }
//    public LiveData<User> getUser(){
//        return user;
//    }

}
