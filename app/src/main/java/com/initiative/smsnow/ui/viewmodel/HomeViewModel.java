package com.initiative.smsnow.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.initiative.smsnow.db.model.UserEntity;
import com.initiative.smsnow.ui.Repository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
  public LiveData<List<UserEntity>> userEntities;

  public HomeViewModel(@NonNull Application application) {
    super(application);
    Repository repository = Repository.getINSTANCE(application);
    userEntities = repository.getUsers();
  }
}
