package com.initiative.smsnow.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {
  @PrimaryKey(autoGenerate = true)
  public int id;
  public String name;
}
