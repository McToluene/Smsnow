package com.initiative.smsnow.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.initiative.smsnow.db.dao.MessageDao;
import com.initiative.smsnow.db.model.MessageEntity;
import com.initiative.smsnow.db.model.UserEntity;
import com.initiative.smsnow.util.DateConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MessageEntity.class, UserEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
  private static final String DATABASE_NAME = "smsnowDB";
  private static volatile AppDatabase instance;
  public abstract MessageDao messageDao();

  private static final int NUMBER_OF_THREADS = 4;
  public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  public static AppDatabase getInstance(Context application) {
    if (instance == null){
      synchronized (AppDatabase.class){
        if (instance == null) {
          instance = Room.databaseBuilder(application.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        }
      }
    }
    return instance;
  }
}
