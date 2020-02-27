package com.initiative.smsnow.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.initiative.smsnow.R;
import com.initiative.smsnow.db.model.UserEntity;
import com.initiative.smsnow.ui.view.HomeView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
  private List<UserEntity> userEntities;
  private Context context;
  private HomeView callback;

  public HomeAdapter(Context ctx, HomeView homeView) {
    context = ctx;
    callback = homeView;
  }

  @NonNull
  @Override
  public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.message_overview_card, parent, false);
    return new HomeViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
    UserEntity userEntity = userEntities.get(position);
    holder.senderName.setText(userEntity.name);
    holder.position = position;
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
    private MaterialTextView senderName, messageBody;
    private int position;
    HomeViewHolder(@NonNull View itemView) {
      super(itemView);
      senderName = itemView.findViewById(R.id.tv_sender_name);
      messageBody = itemView.findViewById(R.id.tv_message_body);

      itemView.setOnClickListener(v -> {
        callback.loadMessage(userEntities.get(position).name, v);
      });
    }
  }
}
