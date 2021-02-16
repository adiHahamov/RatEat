package com.adiandnoy.RatEat.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.adiandnoy.RatEat.AppMainActivity;
import com.adiandnoy.RatEat.MyApplication;

@Database(entities = {Dish.class,User.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
        public abstract DishDao dishDao();
        public abstract UserDao userDao();
}

public class AppLocalDB {
        static public AppLocalDbRepository db =
                Room.databaseBuilder(MyApplication.context,
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                        .fallbackToDestructiveMigration()
                        .build();
    }

