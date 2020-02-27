package com.initiative.smsnow.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.initiative.smsnow.db.model.MessageEntity;
import com.initiative.smsnow.db.model.UserEntity;

import java.util.List;

@Dao
public interface MessageDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(MessageEntity entity);

  @Query("SELECT * FROM messageentity")
  LiveData<List<MessageEntity>> getMessages();

  @Query("SELECT * FROM userentity")
  LiveData<List<UserEntity>> getUsers();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertUser(UserEntity userEntity);
}
