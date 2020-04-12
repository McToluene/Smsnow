package com.initiative.smsnow.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {
  @PrimaryKey
  @NonNull
  private String name;

  public UserEntity(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }
}
