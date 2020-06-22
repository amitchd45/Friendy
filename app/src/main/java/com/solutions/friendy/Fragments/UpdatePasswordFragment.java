package com.solutions.friendy.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.solutions.friendy.App;
import com.solutions.friendy.R;
import com.solutions.friendy.ViewModel.VMRegisterUser;

import java.util.Map;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePasswordFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title, et_verification_code, et_new_password, tv_show_number;
    private ImageView iv_back;
    private Button btn_update;
    private String newOtpStr, newPasswordStr, phoneNumber, otp1;
    private OtpTextView otpTextView;
    private VMRegisterUser vmRegisterUser;


    public UpdatePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_password, container, false);

        vmRegisterUser = ViewModelProviders.of(getActivity()).get(VMRegisterUser.class);

        newOtpStr = App.getSinlton().getNewOtpForget();
        Toast.makeText(getContext(), "otp" + newOtpStr, Toast.LENGTH_SHORT).show();
        phoneNumber = App.getSinlton().getNewPhone();

        findIds(view);


        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                otp1 = otp;
            }
        });

        return view;
    }

    private void findIds(View view) {
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Set & Update Password");

        btn_update = view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);

        tv_show_number = view.findViewById(R.id.tv_show_number);
        tv_show_number.setText(phoneNumber);

        et_new_password = view.findViewById(R.id.et_new_password);

        otpTextView = view.findViewById(R.id.otp_view);

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.btn_update:
                validate();
                break;

        }
    }

    private void validate() {

        newPasswordStr = et_new_password.getText().toString();

        if (otp1.equals(newOtpStr)) {

            vmRegisterUser.updatepassResults(getActivity(), phoneNumber, newPasswordStr).observe(UpdatePasswordFragment.this, new Observer<Map>() {
                @Override
                public void onChanged(Map map) {
                    if (map.get("success").toString().equalsIgnoreCase("1")) {
                        //
                        Toast.makeText(getContext(), map.get("message").toString(), Toast.LENGTH_SHORT).show();

                        if (App.getAppPreference().GetString("updatePass").equalsIgnoreCase("1")) {
                            NavDirections navDirections1 = UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToAccountSecurityFragment();
                            Navigation.findNavController(view).navigate(navDirections1);
                        }
                        else {
                            NavDirections navDirections = UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToPasswordFragment();
                            Navigation.findNavController(view).navigate(navDirections);
                        }
                    } else {
                        Toast.makeText(getContext(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getContext(), "Otp mismatch", Toast.LENGTH_SHORT).show();
        }

    }


}
