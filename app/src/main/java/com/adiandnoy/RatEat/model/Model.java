package com.adiandnoy.RatEat.model;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public final static Model instance = new Model();
    ModelFirebase modelFirebase = new ModelFirebase();
    ModelSql modelSql = new ModelSql();
//    private Model(){
//        for(int i=0;i<100;i++) {
//            Student student = new Student();
//            Dish dish = new Dish();
////            user.id = "" + i;
//            student.name = "Moshe " + i;
//            data.add(student);
//
//        }
//    }
//
//    List<Student> data = new LinkedList<Student>();
//
//    public List<Student> getAllStudents() {
//        return data;
//    }

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

}
