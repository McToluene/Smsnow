package com.initiative.smsnow.ui.fragments;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.initiative.smsnow.R;
import com.initiative.smsnow.ui.adapters.HomeAdapter;
import com.initiative.smsnow.ui.view.HomeView;
import com.initiative.smsnow.ui.viewmodel.HomeViewModel;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {


  private HomeViewModel viewModel;
  private HomeAdapter homeAdapter;

  public HomeFragment() {
    // Required empty public constructor
  }


  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // initialize view model
    viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    RecyclerView messagesRecycler = view.findViewById(R.id.recycler_messages_list);
    FloatingActionButton fab = view.findViewById(R.id.fab);
    fab.setColorFilter(Objects.requireNonNull(getActivity()).getColor(R.color.colorPrimaryDark));
    fab.setOnClickListener(v -> Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show());

    homeAdapter = new HomeAdapter(Objects.requireNonNull(getActivity()).getApplication(), this);
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
    HomeFragmentDirections.ActionHomeFragmentToReadFragment action = HomeFragmentDirections.actionHomeFragmentToReadFragment(uniqueAddress).setUniqueAddress(uniqueAddress);
    Navigation.findNavController(view).navigate(action);
  }
}
