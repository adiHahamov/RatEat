package com.adiandnoy.RatEat.model;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public final static Model instance = new Model();

    private Model(){
        for(int i=0;i<100;i++) {
            Student student = new Student();
//            user.id = "" + i;
            student.name = "Moshe " + i;
            data.add(student);
        }
    }

    List<Student> data = new LinkedList<Student>();

    public List<Student> getAllStudents() {
        return data;
    }

    //Dish function
    public interface GetAllDishesListener{
        void onComplete(List<Dish> data);
    }

    public void getAllDishes(GetAllDishesListener listener) {
        class MyAsyncTask extends AsyncTask {
            List<Dish> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDB.db.dishDao().getAllDishes();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface AddDisheListener{
        void onComplete();
    }

    public void addDish(final Dish dish,AddDisheListener listener){
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

    public void getAllUsers(GetAllUsersListener listener) {
        class MyAsyncTask extends AsyncTask {
            List<User> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                List<User> data = AppLocalDB.db.userDao().getAllUsers();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface AddUserListener{
        void onComplete();
    }

    public void addUser(final User user,AddUserListener listener){
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
