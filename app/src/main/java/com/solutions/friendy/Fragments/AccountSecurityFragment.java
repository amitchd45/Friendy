package com.solutions.friendy.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.google.android.material.snackbar.Snackbar;
import com.solutions.friendy.App;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VmUserSetting;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountSecurityFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title,tv_deleteAccount;
    private ImageView iv_back;
    private RelativeLayout rl_update_password;
    private Button btn_change_no;
    private String phoneNumber;
    private VmUserSetting vmUserSetting;
    private ProgressDialog dialog;


    public AccountSecurityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_security, container, false);

        vmUserSetting = ViewModelProviders.of(this).get(VmUserSetting.class);

        phoneNumber = App.getSinlton().getNewPhone();

        btn_change_no = view.findViewById(R.id.btn_change_no);
        btn_change_no.setText(App.getAppPreference().GetString(AppConstants.PHONE));
        btn_change_no.setOnClickListener(this);

        rl_update_password = view.findViewById(R.id.rl_update_password);
        rl_update_password.setOnClickListener(this);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Account & Security");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_deleteAccount = view.findViewById(R.id.tv_deleteAccount);
        tv_deleteAccount.setOnClickListener(this);


        dialog= new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.rl_update_password:

                App.getAppPreference().SaveString("updatePass","1");

                NavDirections navDirections=AccountSecurityFragmentDirections.actionAccountSecurityFragmentToPhoneForgetPassFragment();
                Navigation.findNavController(view).navigate(navDirections);
                break;

            case R.id.btn_change_no:
                NavDirections navDirections1=AccountSecurityFragmentDirections.actionAccountSecurityFragmentToPhoneNoChangeFragment();
                Navigation.findNavController(view).navigate(navDirections1);

                break;

            case R.id.tv_deleteAccount:
                deleteAccount();
                break;
        }
    }

    private void deleteAccount() {
        dialog.show();
        vmUserSetting.deleteAcc(getActivity(),App.getAppPreference().GetString("id")).observe(AccountSecurityFragment.this, new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if (map.get("success").equals("1")){
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "account deleted successfully", Toast.LENGTH_SHORT).show();

                    App.getAppPreference().Logout();
                    CometChat.logout(new CometChat.CallbackListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            NavDirections navDirections4=AccountSecurityFragmentDirections.actionAccountSecurityFragmentToLoginFragment();
                            Navigation.findNavController(view).navigate(navDirections4);
                        }

                        @Override
                        public void onError(CometChatException e) {
                            Snackbar.make(view,"Login Error:"+e.getCode(),Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
