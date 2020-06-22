package com.solutions.friendy.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.hbb20.CountryCodePicker;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneForgetPassFragment extends Fragment implements View.OnClickListener {

    private View view;
    private EditText mPhone;
    private ImageView iv_back;
    private TextView tv_title;
    private Button btn_continue;
    private CountryCodePicker countryCodePicker;
    private String phone, code;
    private VMRegisterUser vmRegisterUser;


    public PhoneForgetPassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phone_number, container, false);

        findIds(view);
        vmRegisterUser = ViewModelProviders.of(getActivity()).get(VMRegisterUser.class);


        return view;
    }

    private void findIds(View view) {
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Phone Number");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        btn_continue = view.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);

        mPhone = view.findViewById(R.id.et_phone);

        countryCodePicker = view.findViewById(R.id.ccp);
        countryCodePicker.registerCarrierNumberEditText(mPhone);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.btn_continue:

                validate();
                break;
        }
    }

    private void validate() {
        phone = mPhone.getText().toString().trim();


        if (phone.isEmpty()) {
            mPhone.setError("Enter Phone number");
        } else if (phone.length() <= 8) {
            mPhone.setError("Enter Proper length of Number");
        } else {

            code=countryCodePicker.getSelectedCountryCodeWithPlus();

            getOtp();


        }

    }

    private void getOtp()
    {

        vmRegisterUser.forgetPassResults(getActivity(),code+phone).observe(PhoneForgetPassFragment.this, new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if(map.get("success").toString().equalsIgnoreCase("1"))
                {

                    Toast.makeText(getContext(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                    App.getSinlton().setNewOtpForget(map.get("otp").toString());

                    App.getSinlton().setNewPhone(code+phone);

                    NavDirections navDirections=PhoneForgetPassFragmentDirections.actionPhoneForgetPassFragmentToUpdatePasswordFragment();
                    Navigation.findNavController(view).navigate(navDirections);
                }
                else
                {
                    Toast.makeText(getContext(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


