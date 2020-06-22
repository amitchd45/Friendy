package com.solutions.friendy.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.solutions.friendy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerificationFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_title;
    private ImageView iv_back, tv_chat;


    public VerificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_verification, container, false);

        findIds(view);
        return view;
    }

    private void findIds(View view) {
        tv_chat = view.findViewById(R.id.tv_chat);
        tv_chat.setVisibility(View.GONE);
        tv_chat.setOnClickListener(this);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Verification");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.tv_chat:
                NavDirections navDirections =VerificationFragmentDirections.actionVerificationFragmentToChatConversationFragment();
                Navigation.findNavController(view).navigate(navDirections);

                break;
        }

    }
}
