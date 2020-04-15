package com.initiative.smsnow.ui;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.initiative.smsnow.db.AppDatabase;
import com.initiative.smsnow.db.model.MessageEntity;
import com.initiative.smsnow.db.model.UserEntity;
import com.initiative.smsnow.util.BlowFish;
import com.klinker.android.send_message.Message;
import com.klinker.android.send_message.Settings;
import com.klinker.android.send_message.Transaction;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
  private static Repository INSTANCE;
  private static AppDatabase database;
  private static Context context;
  private static ExecutorService executorService = Executors.newSingleThreadExecutor();

  public static Repository getINSTANCE(Application application) {
    if (INSTANCE == null){
      INSTANCE = new Repository();
      database = AppDatabase.getInstance(application);
      context = application.getApplicationContext();
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

  private String encryptMessage(String messageText){
    return BlowFish.encrypt(messageText);
  }

  public void sendMessage(String messageText, String addressToSendTo) {
    executorService.execute(()->{
      Settings settings = new Settings();
      settings.setUseSystemSending(true);
      Transaction transaction = new Transaction(context, settings);
      Message message = new Message(encryptMessage(messageText), addressToSendTo);
      transaction.sendNewMessage(message, Transaction.NO_THREAD_ID);
    });

  }
}
