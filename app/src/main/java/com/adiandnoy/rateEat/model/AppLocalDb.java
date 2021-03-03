package com.adiandnoy.rateEat.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.adiandnoy.rateEat.MyApplication;

@Database(entities = {Student.class}, version = 5)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract StudentDao studentDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

