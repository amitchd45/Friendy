package com.solutions.friendy.BottomSheet;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solutions.friendy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuperLikeFragment extends Fragment {

    private View view;


    public SuperLikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_super_like, container, false);

        return view;
    }

}
