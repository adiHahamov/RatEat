package com.adiandnoy.RatEat.model;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public final static Model instance = new Model();
    ModelFirebase modelFirebase = new ModelFirebase();
    ModelSql modelSql = new ModelSql();

    //Dish function
    public interface GetAllDishesListener{
        void onComplete(List<Dish> data);
    }

    public void getAllDishes(GetAllDishesListener listener) {
        modelFirebase.getAllDishes(listener);
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

    public void deleteDish(Dish dish,DeleteDisheListener listener){
        modelFirebase.deleteDish(dish,listener);
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

    public void addUser(final User user,AddUserListener listener){
        modelFirebase.addUser(user,listener);
    };

    public interface uploadImageListener{
        public void onComplete(String url);
    }
    public void uploadImage(Bitmap imageBmp, String name, final uploadImageListener listener){
        modelFirebase.uploadImage(imageBmp, name,listener);
    }

}
