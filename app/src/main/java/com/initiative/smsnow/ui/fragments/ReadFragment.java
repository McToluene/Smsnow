package com.initiative.smsnow.ui.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.initiative.smsnow.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadFragment extends Fragment {


  public ReadFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_read, container, false);
  }

}
