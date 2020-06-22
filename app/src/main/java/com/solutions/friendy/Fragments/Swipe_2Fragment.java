package com.solutions.friendy.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solutions.friendy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Swipe_2Fragment extends Fragment {

    private View view;


    public Swipe_2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_swipe_2, container, false);

        return  view;
    }

}
