package com.adiandnoy.RatEat.model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ModelSql {

    //Dish function
    public interface GetAllDishesListener{
        void onComplete(List<Dish> data);
    }

    public LiveData<List<Dish>> getAllDishes() {
        return AppLocalDB.db.dishDao().getAllDishes();
    }

    public interface AddDisheListener{
        void onComplete();
    }

    public void addDish(final Dish dish, Model.AddDisheListener listener){
        class MyAsyncTask  extends AsyncTask{
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.dishDao().insertAll(dish);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    };

    //User function
    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }

    public LiveData<List<User>> getAllUsers() {
        return AppLocalDB.db.userDao().getAllUsers();
//        class MyAsyncTask extends AsyncTask {
//            List<User> data;
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                List<User> data = AppLocalDB.db.userDao().getAllUsers();
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//                listener.onComplete(data);
//            }
//        }
//        MyAsyncTask task = new MyAsyncTask();
//        task.execute();
    }

    public interface AddUserListener{
        void onComplete();
    }

    public void addUser(final User user, Model.AddUserListener listener){
        class MyAsyncTask  extends AsyncTask{
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.userDao().insertAll(user);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    };

}
