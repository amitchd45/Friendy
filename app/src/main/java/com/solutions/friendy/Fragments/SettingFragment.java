package com.solutions.friendy.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.omninos.util_data.CommonUtils;
import com.solutions.friendy.Adapters.AdapterInterestUser;
import com.solutions.friendy.Adapters.ListPersonalityAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.GetProfileDataModel;
import com.solutions.friendy.Models.GetUserSettingModelClass;
import com.solutions.friendy.Models.UserSettingModelClass;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VMRegisterUser;
import com.solutions.friendy.ViewModel.VmReceiveData;
import com.solutions.friendy.ViewModel.VmUserSetting;
import com.solutions.friendy.service.LatLongService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title, tv_show_distance, tv_age_minRange, tv_age_maxRange, tv_select_gender,tv_state,tv_city;
    private ImageView iv_back;
    private RelativeLayout rl_information, rl_pricicy_noti, rl_data_storage, rl_account, rl_help_feedback;
    private SeekBar seek_distance, seek_age;
    private int minValue = 18;
    private VmUserSetting vmUserSetting;

    private ProgressDialog dialog;
    private SwitchCompat switch_button_auto_distance;
    private String distance = "0", StrAutoDistance = "0", userId, srtAge="", strGender = "1", strGenderName = "both",check="";

    private VmReceiveData vmReceiveData;
    private Geocoder geocoder;
    private List<Address> addresses;
    private Double lat,lon;
    private Button btn_sign_out;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        vmUserSetting = ViewModelProviders.of(getActivity()).get(VmUserSetting.class);
        vmReceiveData = ViewModelProviders.of(getActivity()).get(VmReceiveData.class);
        lat= Double.parseDouble(App.getAppPreference().GetString(AppConstants.LAT));
        lon= Double.parseDouble(App.getAppPreference().GetString(AppConstants.LONG));

//        dialog = new ProgressDialog(getActivity());
//        dialog.setMessage("Please wait...");
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();

        userId = App.getAppPreference().GetString("id");
        findIds(view);

        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {


            addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName();

            tv_city.setText(city);
            tv_state.setText(state);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserSettingsData();
        getProfile();
    }

    private void getUserSettingsData() {
        if (CommonUtils.isNetworkConnected(getActivity())) {
            vmUserSetting.settingDetails(getActivity(), userId).observe(getActivity(), new Observer<GetUserSettingModelClass>() {
                @Override
                public void onChanged(GetUserSettingModelClass getUserSettingModelClass) {

                    if (getUserSettingModelClass.getSuccess().equalsIgnoreCase("1")) {
                        distance = getUserSettingModelClass.getDetails().getDistance();
                        tv_show_distance.setText(distance + " Kms");
                        seek_distance.setProgress(Integer.parseInt(distance));

                        srtAge = getUserSettingModelClass.getDetails().getAge();
                        tv_age_maxRange.setText(srtAge + "");
                        seek_age.setProgress(Integer.parseInt(srtAge));

                        if (getUserSettingModelClass.getDetails().getAutomaticDistance().equalsIgnoreCase("1")) {
                            switch_button_auto_distance.setChecked(true);
                            StrAutoDistance = getUserSettingModelClass.getDetails().getAutomaticDistance();
                        } else {
                            switch_button_auto_distance.setChecked(false);
                            StrAutoDistance = getUserSettingModelClass.getDetails().getAutomaticDistance();
                        }

                        if (getUserSettingModelClass.getDetails().getGender().equalsIgnoreCase("1")){
                            strGender=getUserSettingModelClass.getDetails().getGender();
                            strGenderName="both";
                            tv_select_gender.setText(strGenderName);
                        }

                        if (getUserSettingModelClass.getDetails().getGender().equalsIgnoreCase("2")){
                            strGender=getUserSettingModelClass.getDetails().getGender();
                            strGenderName="only male";
                            tv_select_gender.setText(strGenderName);
                        }

                        if (getUserSettingModelClass.getDetails().getGender().equalsIgnoreCase("3")){
                            strGender=getUserSettingModelClass.getDetails().getGender();
                            strGenderName="only Female";
                            tv_select_gender.setText(strGenderName);
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Not set any filter", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), "Internet is not connect", Toast.LENGTH_SHORT).show();
        }

    }

    private void filterUserSetting() {
        if (CommonUtils.isNetworkConnected(getActivity())) {
            vmUserSetting.filterUser(getActivity(), userId, StrAutoDistance, strGender, srtAge, distance).observe(SettingFragment.this, new Observer<UserSettingModelClass>() {
                @Override
                public void onChanged(UserSettingModelClass userSettingModelClass) {
                    if (userSettingModelClass.getSuccess().equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), userSettingModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getActivity(), userSettingModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Internet not connected.", Toast.LENGTH_SHORT).show();
        }

    }

    private void findIds(View view) {

        tv_city = view.findViewById(R.id.tv_city);
        tv_state = view.findViewById(R.id.tv_state);

        btn_sign_out = view.findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(this);

        tv_select_gender = view.findViewById(R.id.tv_select_gender);
        tv_select_gender.setOnClickListener(this);

        rl_help_feedback = view.findViewById(R.id.rl_help_feedback);
        rl_help_feedback.setOnClickListener(this);

        rl_information = view.findViewById(R.id.rl_information);
        rl_information.setOnClickListener(this);

        rl_account = view.findViewById(R.id.rl_account);
        rl_account.setOnClickListener(this);

        rl_data_storage = view.findViewById(R.id.rl_data_storage);
        rl_data_storage.setOnClickListener(this);

        rl_pricicy_noti = view.findViewById(R.id.rl_pricicy_noti);
        rl_pricicy_noti.setOnClickListener(this);

        tv_show_distance = view.findViewById(R.id.tv_show_distance);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Settings");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_age_maxRange = view.findViewById(R.id.tv_age_maxRange);
        tv_age_minRange = view.findViewById(R.id.tv_age_minRange);
        tv_age_minRange.setText("18");


        switch_button_auto_distance = view.findViewById(R.id.switch_button_auto_distance);
        switch_button_auto_distance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    StrAutoDistance = "1";
                } else {
                    StrAutoDistance = "0";
                }
            }
        });


        seek_distance = view.findViewById(R.id.seek_distance);
        seek_age = view.findViewById(R.id.seek_set_age);
        seek_age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seektime1 = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                srtAge = String.valueOf(progress);

                tv_age_maxRange.setText(progress + "");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seektime = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                distance= String.valueOf(progress);
                tv_show_distance.setText(progress + " Kms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_gender:
                selectGender();
                break;

                case R.id.btn_sign_out:

                    App.getAppPreference().Logout();
                    CometChat.logout(new CometChat.CallbackListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            NavDirections navDirections4=SettingFragmentDirections.actionSettingFragmentToLoginFragment();
                            Navigation.findNavController(view).navigate(navDirections4);
                        }

                        @Override
                        public void onError(CometChatException e) {
                            Snackbar.make(view,"Login Error:"+e.getCode(),Snackbar.LENGTH_LONG).show();
                        }
                    });
                break;
            case R.id.iv_back:
                filterUserSetting();
                break;

            case R.id.rl_information:

                NavDirections navDirections1 = SettingFragmentDirections.actionSettingFragmentToPersonalInformationFragment();
                Navigation.findNavController(view).navigate(navDirections1);

                break;

            case R.id.rl_pricicy_noti:
//
                NavDirections navDirections2 = SettingFragmentDirections.actionSettingFragmentToPrivacyNotificationFragment();
                Navigation.findNavController(view).navigate(navDirections2);
                break;

            case R.id.rl_data_storage:

                NavDirections navDirections3 = SettingFragmentDirections.actionSettingFragmentToDataAndStorageFragment();
                Navigation.findNavController(view).navigate(navDirections3);

                break;

            case R.id.rl_account:
                NavDirections navDirections4 = SettingFragmentDirections.actionSettingFragmentToAccountSecurityFragment();
                Navigation.findNavController(view).navigate(navDirections4);

                break;

            case R.id.rl_help_feedback:
                NavDirections navDirections5 = SettingFragmentDirections.actionSettingFragmentToHwlpAndFeedbackFragment();
                Navigation.findNavController(view).navigate(navDirections5);

                break;
        }
    }

    private void selectGender() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.select_gender);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        RadioButton all = dialog.findViewById(R.id.radio_all);
        RadioButton man = dialog.findViewById(R.id.radio_man);
        RadioButton woman = dialog.findViewById(R.id.radio_woman);



        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strGender = "1";
                    strGenderName = "both";
                    Toast.makeText(getActivity(), strGender +":"+strGenderName, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //--------man-----

        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strGender = "2";
                strGenderName = "only male";
                Toast.makeText(getActivity(), strGender +":"+strGenderName, Toast.LENGTH_SHORT).show();

            }
        });

        //-----woman----

        woman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strGender = "3";
                strGenderName = "only Female";
                Toast.makeText(getActivity(), strGender +":"+strGenderName, Toast.LENGTH_SHORT).show();

            }
        });


        Button ok = dialog.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (strGender.equals("1")){
                    strGenderName = "both";
                    tv_select_gender.setText(strGenderName);
                }
                else if (strGender.equals("2")){
                    strGenderName = "only Male";
                    tv_select_gender.setText(strGenderName);
                }
                else if (strGender.equals("3")){
                    strGenderName = "only Female";
                    tv_select_gender.setText(strGenderName);
                }



                dialog.dismiss();
            }
        });
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void getProfile() {
        vmReceiveData.get_profile(getActivity(), userId).observe(getActivity(), new Observer<GetProfileDataModel>() {
            @Override
            public void onChanged(GetProfileDataModel getProfileDataModel) {
                if (getProfileDataModel.getSuccess().equalsIgnoreCase("1")) {

                    App.getAppPreference().SaveString(AppConstants.USER_NAME,getProfileDataModel.getDetails().getName());
                    App.getAppPreference().SaveString(AppConstants.USER_DOB, getProfileDataModel.getDetails().getDob());
                    App.getAppPreference().SaveString(AppConstants.USER_GENDER, getProfileDataModel.getDetails().getGender());
                    App.getAppPreference().SaveString(AppConstants.PHONE, getProfileDataModel.getDetails().getPhone());

                } else {
                    Toast.makeText(getActivity(), "response error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
