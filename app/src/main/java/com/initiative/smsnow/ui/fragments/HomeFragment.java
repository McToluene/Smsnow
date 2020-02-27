package com.initiative.smsnow.ui.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.initiative.smsnow.R;
import com.initiative.smsnow.ui.adapters.HomeAdapter;
import com.initiative.smsnow.ui.view.HomeView;
import com.initiative.smsnow.ui.viewmodel.HomeViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {


  private HomeViewModel viewModel;
  private HomeAdapter homeAdapter;

  public HomeFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // initialize view model
    viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    RecyclerView messagesRecycler = view.findViewById(R.id.recycler_messages_list);
    homeAdapter = new HomeAdapter(getContext(), this);
    messagesRecycler.setAdapter(homeAdapter);
    observeMessages();
    messagesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


    return view;
  }

  private void observeMessages() {
    viewModel.userEntities.observe(getViewLifecycleOwner(), userEntities -> {
      homeAdapter.setUserEntities(userEntities);
    });
  }

  @Override
  public void loadMessage(String uniqueAddress, View view) {
    HomeFragmentDirections.ActionHomeFragmentToReadFragment action = HomeFragmentDirections.actionHomeFragmentToReadFragment(uniqueAddress);
    Navigation.findNavController(view).navigate(action);
  }
}
