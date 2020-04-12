package com.initiative.smsnow.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.initiative.smsnow.R;
import com.initiative.smsnow.db.model.MessageEntity;
import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ReadViewHolder> {
  private List<MessageEntity> messageEntities;
  private Context context;

  public ReadAdapter(Context context) {
    this.context = context;
  }

  @NonNull
  @Override
  public ReadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ReadViewHolder(LayoutInflater.from(context).inflate(R.layout.read_message_card, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ReadViewHolder holder, int position) {
    MessageEntity messageEntity = messageEntities.get(position);
    holder.message.setText(messageEntity.messageBody);
    holder.position = position;
  }

  @Override
  public int getItemCount() {
    return messageEntities != null ? messageEntities.size() : 0;
  }

  public void setMessageEntities(List<MessageEntity> messageEntities) {
    this.messageEntities = messageEntities;
    notifyDataSetChanged();
  }

  static class ReadViewHolder extends RecyclerView.ViewHolder {
    private MaterialTextView message;
    private int position;

    ReadViewHolder(@NonNull View itemView) {
      super(itemView);
      message = itemView.findViewById(R.id.tv_message);
    }
  }
}
