package com.solutions.friendy.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.solutions.friendy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyNotificationFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_title;
    private ImageView iv_back;
    private RelativeLayout rl_mute_user;


    public PrivacyNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_privacy_notification, container, false);

//        rl_mute_user = view.findViewById(R.id.rl_mute_user);
//        rl_mute_user.setOnClickListener(this);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Privacy & Notification");

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

//            case R.id.rl_mute_user:
//
//                NavDirections navDirections1=PrivacyNotificationFragmentDirections.actionPrivacyNotificationFragmentToMutaedUserFragment();
//                Navigation.findNavController(view).navigate(navDirections1);
//
//                break;
        }
    }
}
