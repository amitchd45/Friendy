package com.solutions.friendy.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hbb20.CountryCodePicker;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.MatchTokenPojo;
import com.solutions.friendy.Models.UpdatePhonePojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VMRegisterUser;
import com.solutions.friendy.ViewModel.VmUserSetting;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneNoChangeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_title;
    private ImageView iv_back;
    private EditText mPhone;
    private CountryCodePicker countryCodePicker;
    private Button btn_getOtp, btn_phone_update;
    private String phone, code, id;
    private VmUserSetting vmUserSetting;
    private OtpTextView otpTextView;
    private VMRegisterUser vmRegisterUser;
    private ProgressDialog dialog;


    public PhoneNoChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phone_no_change, container, false);

        vmUserSetting = ViewModelProviders.of(this).get(VmUserSetting.class);
        vmRegisterUser = ViewModelProviders.of(this).get(VMRegisterUser.class);

        id = App.getAppPreference().GetString("id");

        otpTextView = view.findViewById(R.id.otp_view);
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Update Phone");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        btn_getOtp = view.findViewById(R.id.btn_getOtp);
        btn_getOtp.setOnClickListener(this);

        btn_phone_update = view.findViewById(R.id.btn_phone_update);
        btn_phone_update.setOnClickListener(this);


        mPhone = view.findViewById(R.id.et_phone);

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        dialog= new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);

        otp();

        mPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mPhone, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        countryCodePicker = view.findViewById(R.id.ccp);
        countryCodePicker.registerCarrierNumberEditText(mPhone);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.btn_getOtp:
                changePhone();
                break;

        }
    }

    private void changePhone() {
        phone = mPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            mPhone.setError("Enter Phone number");
        } else {
            dialog.show();
            code = countryCodePicker.getSelectedCountryCodeWithPlus();
            vmUserSetting.getPhone(getActivity(), code + phone, id).observe(PhoneNoChangeFragment.this, new Observer<UpdatePhonePojo>() {
                @Override
                public void onChanged(UpdatePhonePojo updatePhonePojo) {
                    if (updatePhonePojo.getSuccess().equalsIgnoreCase("1")) {
                        dialog.dismiss();
                        App.getAppPreference().SaveString("otp1", updatePhonePojo.getOtp());
                        Toast.makeText(getActivity(), "Otp : "+updatePhonePojo.getOtp(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), updatePhonePojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void otp() {


        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                final String newOtp = App.getAppPreference().GetString("otp1");
                if (newOtp.equals(otp)) {
                    matchToken(newOtp);

                } else {
                    Toast.makeText(getActivity(), "otp mismatch", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void matchToken(String newOtp) {
        dialog.show();

        vmRegisterUser.getmatchToken(getActivity(), id, newOtp).observe(getActivity(), new Observer<MatchTokenPojo>() {
            @Override
            public void onChanged(MatchTokenPojo matchToken) {
                if (matchToken.getSuccess().equalsIgnoreCase("1")) {
                    dialog.dismiss();
                    App.getAppPreference().SaveString(AppConstants.PHONE,code + phone);
                    Toast.makeText(getActivity(), "Your Phone Update Successfully", Toast.LENGTH_SHORT).show();

                    getActivity().onBackPressed();

                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), matchToken.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
