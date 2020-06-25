package com.solutions.friendy.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
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
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView iv_back,iv_password_show;
    private TextView tv_title;
    private Button btn_signup;
    private LinearLayout ll_hide_for_fb;

    private EditText nameEt, dobEt, passwordEt;
    private RadioButton male, female;
    private String genderStr = "0", nameStr, dobStr = "", passwordStr, id, imagePath = "";
    private Boolean imageCheck = false;
    private File storeImage;


    private static final int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    private String latitude, longitude;
    private SimpleDateFormat simpleDateFormat;
    private int y,m,d;
    private DatePickerDialog picker;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        id = App.getAppPreference().GetString("id");

        findIds(view);
        simpleDateFormat = new SimpleDateFormat("yyyy mm dd", Locale.US);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();


        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    genderStr = "0";
                }
            }
        });

        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    genderStr = "1";
                }
            }
        });

        return view;
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

    private void findIds(View view) {

        ll_hide_for_fb = view.findViewById(R.id.ll_hide_for_fb);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Register");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        btn_signup = view.findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        nameEt = view.findViewById(R.id.email_address);
        dobEt = view.findViewById(R.id.et_dob);
        dobEt.setOnClickListener(this);
        passwordEt = view.findViewById(R.id.et_password);
        iv_password_show = view.findViewById(R.id.iv_password_show);
        iv_password_show.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        passwordEt.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        passwordEt.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }

                return true;
            }
        });


        male = view.findViewById(R.id.rb_male);
        male.setChecked(true);
        female = view.findViewById(R.id.rb_female);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.et_dob:
                spinnerDate();
                break;

            case R.id.btn_signup:
                if (App.getAppPreference().GetString(AppConstants.LATITUDE)!=null && App.getAppPreference().GetString(AppConstants.LATITUDE) !=null){
                    validate();
                }else {
                    Toast.makeText(getActivity(), "latLong is empty", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    private void spinnerDate() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.date_picker_spiner);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_date_of_birth=dialog.findViewById(R.id.tv_date_of_birth);
        Button btn_setDate=dialog.findViewById(R.id.btn_setDate);
        DatePicker datePicker=dialog.findViewById(R.id.picker);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        y=calendar.get(Calendar.YEAR);
        m=calendar.get(Calendar.MONTH);
        d=calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(y, m, d, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                tv_date_of_birth.setText(year+"-"+(month + 1) + "-"+dayOfMonth);
                y=year;
                 m=month;
                 d=dayOfMonth;

            }
        });

        btn_setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dobEt.setText(y+"-"+(m + 1) + "-"+d);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void setDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker.getDatePicker().setSpinnersShown(true);
        picker = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        dobEt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        dobEt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        picker.show();
    }

    private void validate() {
        nameStr = nameEt.getText().toString().trim();
        dobStr = dobEt.getText().toString().trim();
        passwordStr = passwordEt.getText().toString().trim();

        if (nameStr.length() == 0 || dobStr.isEmpty() || dobStr.equalsIgnoreCase("") || passwordStr.isEmpty() || passwordStr.length() < 8) {
            if (nameStr.length() == 0) {
                nameEt.setError("Field required");
            }
            if (dobStr.isEmpty() || dobStr.equalsIgnoreCase("")) {
                dobEt.setError("Field required");
            }
            if (passwordStr.length() < 8 || passwordStr.isEmpty()) {
                if (passwordStr.length() < 8) {
                    passwordEt.setError("Password should be 8 character.");
                } else if (passwordStr.isEmpty()) {
                    passwordEt.setError("Field required");
                }
            }
        } else {

            App.getAppPreference().SaveString(AppConstants.RUSER_NAME,nameStr);
            App.getAppPreference().SaveString(AppConstants.RGENDER,genderStr);
            App.getAppPreference().SaveString(AppConstants.RDOB,dobStr);
            App.getAppPreference().SaveString(AppConstants.RPASS,passwordStr);

            NavDirections navDirections = RegisterFragmentDirections.actionRegisterFragmentToUploadRealPhotoFragment();
            Navigation.findNavController(view).navigate(navDirections);

        }

    }


}
