package com.initiative.smsnow.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import androidx.annotation.RequiresApi;

import com.initiative.smsnow.db.AppDatabase;
import com.initiative.smsnow.db.model.MessageEntity;
import com.initiative.smsnow.db.model.UserEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageReceiver extends BroadcastReceiver {
  private static final String TAG = MessageReceiver.class.getSimpleName();
  public static final String pdu_type = "pdus";

  private ExecutorService executorService = Executors.newSingleThreadExecutor();


  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  public void onReceive(Context context, Intent intent) {
    // Get the SMS message.
    Bundle bundle = intent.getExtras();
    SmsMessage[] msgs;

    String format = bundle != null ? bundle.getString("format") : null;

    // Retrieve the SMS message received.
    Object[] pdus = (Object[]) (bundle != null ? bundle.get(pdu_type) : null);
    msgs = new SmsMessage[Objects.requireNonNull(pdus).length];
    for (int i = 0; i < msgs.length; i++) {
      msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
      saveMessage(context, msgs[i]);
    }

  }

  private void saveMessage(Context context, SmsMessage msg) {
    final AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
    final MessageEntity entity = new MessageEntity();
    UserEntity userEntity = new UserEntity(Objects.requireNonNull(msg.getOriginatingAddress()));

    entity.senderName = msg.getOriginatingAddress();
    entity.messageBody = msg.getDisplayMessageBody();
    entity.type = 0;
    entity.date = new Date(msg.getTimestampMillis());
//    Timestamp timestamp = new Timestamp(msg.getTimestampMillis());


    executorService.execute(() -> database.messageDao().insert(entity));
    executorService.execute(() -> {database.messageDao().insertUser(userEntity);});
  }
}
