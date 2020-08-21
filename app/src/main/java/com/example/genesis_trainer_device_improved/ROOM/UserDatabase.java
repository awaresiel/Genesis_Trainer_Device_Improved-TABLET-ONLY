package com.example.genesis_trainer_device_improved.ROOM;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.Statistics;
import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.Entity.User;
import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;

//@Database(entities = {User.class, Trainer.class, Client.class}, version = 1, exportSchema = false)
@Database(entities = {User.class, Trainer.class, Client.class, UserTrainingSettingsTable.class, Statistics.class}, version = 2, exportSchema = false)

public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    public UserDatabase()
    {

    }

    private static volatile UserDatabase INSTANCE;


    public static UserDatabase getUserDatabaseInstance(final Context context){
        if (INSTANCE ==null){
            synchronized (UserDatabase.class){
                if (INSTANCE ==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,"User_DB")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
