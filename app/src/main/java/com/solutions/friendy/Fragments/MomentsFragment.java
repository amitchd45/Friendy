package com.solutions.friendy.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutions.friendy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MomentsFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title;
    private ImageView iv_back;


    public MomentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_moments, container, false);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Moments");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }
    }
}
