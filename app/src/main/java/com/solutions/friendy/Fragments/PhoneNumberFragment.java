package com.solutions.friendy.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.SentOtpPhone;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VMRegisterUser;
import com.hbb20.CountryCodePicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneNumberFragment extends Fragment implements View.OnClickListener {

    private View view;
    private EditText mPhone;
    private ImageView iv_back;
    private TextView tv_title;
    private Button btn_continue;
    private CountryCodePicker countryCodePicker;
    private String phone,code;
    private VMRegisterUser vmRegisterUser;
    private static final int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;



    public PhoneNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phone_number, container, false);

        findIds(view);
        vmRegisterUser= ViewModelProviders.of(getActivity()).get(VMRegisterUser.class);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();


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

        mPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mPhone, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        countryCodePicker=view.findViewById(R.id.ccp);
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
        }
        else {
            code=countryCodePicker.getSelectedCountryCodeWithPlus();
            vmRegisterUser.getPhoneResult(getActivity(),code+phone,"reg","android",App.getAppPreference().GetString(AppConstants.LATITUDE),App.getAppPreference().GetString(AppConstants.LONGITUDE)).observe(getActivity(), new Observer<SentOtpPhone>() {
                @Override
                public void onChanged(SentOtpPhone sentOtpPhone) {

                    if (sentOtpPhone.getSuccess().equalsIgnoreCase("1")){
                        //fullyVerified
                        App.getAppPreference().SaveString("id",sentOtpPhone.getDetails().getId());
                        App.getSinlton().setShowUser_number(code+phone);
                        App.getSinlton().setPhone(sentOtpPhone.getDetails().getPhone());
                        App.getSinlton().setId(sentOtpPhone.getDetails().getId());
                        App.getSinlton().setOtp(sentOtpPhone.getDetails().getOtp());
                        App.getSinlton().setCheckUserExist("0");

                        NavDirections navDirections = PhoneNumberFragmentDirections.actionPhoneNumberFragmentToOtpFragment();
                        Navigation.findNavController(view).navigate(navDirections);
                        Toast.makeText(getActivity(), sentOtpPhone.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //new  exist or unVerified
                        App.getAppPreference().SaveString("id",sentOtpPhone.getDetails().getId());
                        App.getSinlton().setShowUser_number(code+phone);
                        App.getSinlton().setPhone(sentOtpPhone.getDetails().getPhone());
                        App.getSinlton().setId(sentOtpPhone.getDetails().getId());
                        App.getSinlton().setOtp(sentOtpPhone.getDetails().getOtp());
                        App.getSinlton().setCheckUserExist("1");

                        NavDirections navDirections = PhoneNumberFragmentDirections.actionPhoneNumberFragmentToOtpFragment();
                        Navigation.findNavController(view).navigate(navDirections);
                        Toast.makeText(getActivity(), sentOtpPhone.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    }

    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    App.getAppPreference().SaveString(AppConstants.LATITUDE,String.valueOf(location.getLatitude()));
                                    App.getAppPreference().SaveString(AppConstants.LONGITUDE,String.valueOf(location.getLongitude()));

                                }
                            }
                        }
                );
            } else {
                Toast.makeText(getActivity(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            App.getAppPreference().SaveString(AppConstants.LATITUDE,String.valueOf(mLastLocation.getLatitude()));
            App.getAppPreference().SaveString(AppConstants.LONGITUDE,String.valueOf(mLastLocation.getLongitude()));

        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
}
