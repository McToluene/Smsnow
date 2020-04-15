package com.initiative.smsnow.ui.adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.initiative.smsnow.R;
import com.initiative.smsnow.db.model.MessageEntity;
import com.initiative.smsnow.db.model.UserEntity;
import com.initiative.smsnow.ui.Repository;
import com.initiative.smsnow.ui.view.HomeView;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
  private List<UserEntity> userEntities;
  private HomeView callback;
  private Repository repository;

  public HomeAdapter(Application ctx, HomeView homeView) {
    callback = homeView;
    repository = Repository.getINSTANCE(ctx);
  }

  @NonNull
  @Override
  public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_overview_card, parent, false);
    return new HomeViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
    holder.bindData(userEntities.get(position), callback);
  }


  @Override
  public int getItemCount() {
    return userEntities != null ? userEntities.size() : 0;
  }

  public void setUserEntities(List<UserEntity> userEntities) {
    this.userEntities = userEntities;
    notifyDataSetChanged();
  }

  class HomeViewHolder extends RecyclerView.ViewHolder {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private MessageEntity message;
    private MaterialTextView senderName, messageBody, messageDate;
    private int position;

    HomeViewHolder(@NonNull View itemView) {
      super(itemView);
      senderName = itemView.findViewById(R.id.tv_sender_name);
      messageBody = itemView.findViewById(R.id.tv_message_body);
      messageDate = itemView.findViewById(R.id.tv_received_date);

    }

    void bindData(UserEntity userEntity, HomeView callback){
      position = getAdapterPosition();
      senderName.setText(userEntity.getName());
      fetchLastMessage(userEntity.getName());
      setMessageAndDate();

      itemView.setOnClickListener(v -> callback.loadMessage(userEntities.get(position).getName(), v));
    }

    private void fetchLastMessage(String name) {
      executor.execute(()-> message = repository.getMessage(name));
    }

    private void setMessageAndDate() {
      CountDownLatch latch = new CountDownLatch(1);
      for (int i = 0; i < 1; i++){
        executor.submit(() -> {
          try {
            messageDate.setText(formatDate(message.date));
            messageBody.setText(message.messageBody);
            notifyDataSetChanged();
            latch.countDown();
          } catch (Exception e) {
            Thread.currentThread().interrupt();
          }
        });
      }
    }

    private String formatDate(Date date) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
      return dateFormat.format(date);
    }
  }
}
