package com.solutions.friendy.Fragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solutions.friendy.R;
import com.suke.widget.SwitchButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncognitoFragment extends Fragment {
    private View view;
    private SwitchButton switchButton;


    public IncognitoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_incognito, container, false);

        findIds(view);

        return view;
    }

    private void findIds(View view) {

        switchButton = view.findViewById(R.id.switch_button);
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    NotificationDialog();

                }
                else {
                    Toast.makeText(getActivity(), "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void NotificationDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_notification);

        TextView tv_allow = dialog.findViewById(R.id.tv_allow);
        TextView tv_dont_allow = dialog.findViewById(R.id.tv_dont_allow);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
        tv_allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Allow", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
//
        tv_dont_allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Don't Allow", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
