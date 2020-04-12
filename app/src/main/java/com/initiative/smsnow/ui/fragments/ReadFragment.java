package com.initiative.smsnow.ui.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.initiative.smsnow.R;
import com.initiative.smsnow.ui.adapters.ReadAdapter;
import com.initiative.smsnow.ui.viewmodel.ReadViewModel;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadFragment extends Fragment {


  private ReadViewModel viewModel;
  private ReadAdapter adapter;
  private TextInputEditText newMessage;
  private MaterialTextView counter;
  private static int updateCount = 160;
  private static int messageCount = 0;
  private int page = 1;

  public ReadFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    // create view model
    viewModel = new ViewModelProvider(this).get(ReadViewModel.class);
    String name = ReadFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getUniqueAddress();
    viewModel.getMessages(name);

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_read, container, false);
    newMessage = view.findViewById(R.id.ed_message);
    counter = view.findViewById(R.id.tv_counter);

    updateCounter();

    inflateView(view);
    observeData();
    return view;
  }

  private void updateCounter() {
    newMessage.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        messageCount += count;
        if (updateCount == 0 || messageCount == 160) {
          count = 0;
        }
        updateCount(count);
      }

      @Override
      public void afterTextChanged(Editable s) {
        String textToShow;
        if (updateCount == 0) {
          page += 1;
          textToShow = getString(R.string.message_count_page, updateCount, page );
          updateCount = 160;
        } else if(updateCount == 160){
          page -= 1;
          textToShow = getString(R.string.message_count_page, updateCount, page );
        } else if(page >= 2) {
          textToShow = getString(R.string.message_count_page, updateCount, page );
        }
        else {
          textToShow = getString(R.string.message_count, updateCount);
        }
        counter.setText(textToShow);
      }
    });
  }

  private void updateCount(int count) {
    if(count == 1) {
      updateCount -= count;
    } else{
      updateCount += 1;
      messageCount -= count;
    }
  }

  private void observeData() {
    viewModel.messages.observe(getViewLifecycleOwner(), messageEntities -> {
      if (messageEntities != null) {
        adapter.setMessageEntities(messageEntities);
      }
    });
  }

  private void inflateView(View view) {
    RecyclerView messageRecycler = view.findViewById(R.id.messages_recycler);
    adapter = new ReadAdapter(getContext());
    messageRecycler.setAdapter(adapter);
    messageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
  }
}
