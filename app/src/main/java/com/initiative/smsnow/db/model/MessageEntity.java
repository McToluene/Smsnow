package com.initiative.smsnow.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity
public class MessageEntity {
  @PrimaryKey(autoGenerate = true)
  public int id;
  public String senderName;
  public String messageBody;
  public Date date;

}
