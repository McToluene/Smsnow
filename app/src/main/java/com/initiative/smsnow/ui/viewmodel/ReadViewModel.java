package com.initiative.smsnow.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.initiative.smsnow.db.model.MessageEntity;
import com.initiative.smsnow.ui.Repository;

import java.util.List;

public class ReadViewModel extends AndroidViewModel {
  public LiveData<List<MessageEntity>> messages;
  private Repository repository;
  public ReadViewModel(@NonNull Application application) {
    super(application);
    repository = Repository.getINSTANCE(application);
  }

  public void getMessages(String name) {
    messages = repository.getMessages(name);
  }

  public void sendMessage(String message, String addressToSendTo) {
    repository.sendMessage(message, addressToSendTo);
  }
}
