package com.initiative.smsnow.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.initiative.smsnow.db.AppDatabase;
import com.initiative.smsnow.db.model.MessageEntity;
import com.initiative.smsnow.db.model.UserEntity;

import java.util.List;

public class Repository {
  private static Repository INSTANCE;
  private static AppDatabase database;

  public static Repository getINSTANCE(Application application) {
    if (INSTANCE == null){
      INSTANCE = new Repository();
      database = AppDatabase.getInstance(application);
    }
    return INSTANCE;
  }

  public LiveData<List<MessageEntity>> getMessages() {
    return database.messageDao().getMessages();
  }
  public MessageEntity getMessage(String name) {
    return database.messageDao().getMessage(name);
  };
  public LiveData<List<UserEntity>> getUsers() {return database.messageDao().getUsers();}
  public LiveData<List<MessageEntity>> getMessages(String name) {
    return database.messageDao().getMessages(name);
  }
}
