package com.adiandnoy.RatEat.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.adiandnoy.RatEat.MyApplication;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public final static Model instance = new Model();
    ModelFirebase modelFirebase = new ModelFirebase();
    ModelSql modelSql = new ModelSql();

    public interface Listener<T>{
        void onComplete(T data);
    }
    //Dish function
    public interface GetAllDishesListener{
        void onComplete(List<Dish> data);
    }

    LiveData<List<Dish>> dishList;

    public LiveData<List<Dish>> getAllDishes() {
        if(dishList == null) {
            dishList = modelSql.getAllDishes();
            refreshAllDishes(null);
        }
        return dishList;
    }

    public void refreshAllDishes(final Listener listener){
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdateDate = sp.getLong("lastUpdateDate", 0);

        modelFirebase.getAllDishes(lastUpdateDate,new GetAllDishesListener() {
            @Override
            public void onComplete(List<Dish> data) {
                long lastUpdated = 0;
                for (Dish dish: data) {
                    modelSql.addDish(dish, null);
                    if(dish.getLastUpdated() > lastUpdated) {
                        lastUpdated = dish.getLastUpdated();
                    }
                }

                sp.edit().putLong("lastUpdateDate", lastUpdated).commit();
                if(listener != null) {
                    listener.onComplete(data);
                }
            }
            });
//        modelFirebase.getAllDishes(new GetAllDishesListener() {
//            @Override
//            public void onComplete(List<Dish> data) {
//                dishList.setValue(data);
//                listener.onComplete(null);
//            }
//        });
    }

    public interface GetDisheListener{
        void onComplete(Dish dish);
    }

    public void getDishe(String id, GetDisheListener listener) {
        modelFirebase.getDish(id,listener);
    }

    public interface AddDisheListener{
        void onComplete();
    }

    public void addDish(final Dish dish,AddDisheListener listener){
        modelFirebase.addDish(dish,listener);
    };

    interface DeleteDisheListener extends AddDisheListener{}
    interface UpdateDishListener extends AddDisheListener{}

    public void deleteDish(Dish dish,DeleteDisheListener listener){
        modelFirebase.deleteDish(dish,listener);
    };

    public void updateDish(Dish dish, UpdateDishListener listener){
        modelFirebase.updateDish(dish,listener);
    };

    //User function
    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }

    public void getAllUsers(GetAllUsersListener listener) {
        modelFirebase.getAllUsers(listener);
    }

    public interface AddUserListener{
        void onComplete();
    }

    public void addUser(final User user, AddUserListener listener){
        modelFirebase.addUser(user,listener);
    };

    public interface uploadImageListener{
        public void onComplete(String url);
    }
    public void uploadImage(Bitmap imageBmp, String name, final uploadImageListener listener){
        modelFirebase.uploadImage(imageBmp, name,listener);
    }

}
