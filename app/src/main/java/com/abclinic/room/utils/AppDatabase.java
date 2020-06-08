package com.abclinic.room.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abclinic.room.dao.DataDao;
import com.abclinic.room.dao.ScheduleDao;
import com.abclinic.room.dao.UserDao;
import com.abclinic.room.entity.DataEntity;
import com.abclinic.room.entity.ScheduleEntity;
import com.abclinic.room.entity.UserEntity;

@Database(entities = {UserEntity.class, DataEntity.class, ScheduleEntity.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "abclinic";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

    public abstract UserDao getUserDao();

    public abstract DataDao getDataDao();

    public abstract ScheduleDao getScheduleDao();
}
