package com.solutions.friendy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.solutions.friendy.App;
import com.solutions.friendy.Fragments.PasswordFragment;
import com.solutions.friendy.Fragments.PasswordFragmentDirections;
import com.solutions.friendy.Models.LoginUserPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VMRegisterUser;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_signin, reset, et_pass;
    private Button btn_signin;
    private String passStr, userID;
    private VMRegisterUser vmRegisterUser;
    private static final int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    private Activity activity = PasswordActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(PasswordActivity.this);
        vmRegisterUser = ViewModelProviders.of(this).get(VMRegisterUser.class);

        intID();

        userID = App.getAppPreference().GetString("id");

        getLastLocation();
    }

    private void intID() {
        et_pass = findViewById(R.id.et_pass);
        tv_signin = findViewById(R.id.tv_text);
        tv_signin.setText("Sign In");
        reset = findViewById(R.id.tv_reset);
        reset.setOnClickListener(this);
        btn_signin = findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text:
                break;

            case R.id.btn_signin:
                if (App.getAppPreference().GetString(AppConstants.LATITUDE) != null && App.getAppPreference().GetString(AppConstants.LATITUDE) != null) {
                    validate();
                } else {
                    Toast.makeText(activity, "latLong is empty", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.tv_reset:
                NavDirections navDirections = PasswordFragmentDirections.actionPasswordFragmentToPhoneForgetPassFragment();
                Navigation.findNavController(v).navigate(navDirections);
                break;
        }
    }


    private void validate() {
        passStr = et_pass.getText().toString().trim();

        if (passStr.isEmpty()) {
            et_pass.setError("Enter your password first");
        } else {
            loginWithPass();
        }
    }

    private void loginWithPass() {

        vmRegisterUser.loginUserPojo(activity, userID, passStr, "0.0", "0.0", "1").observe(PasswordActivity.this, new Observer<LoginUserPojo>() {
            @Override
            public void onChanged(LoginUserPojo loginUserPojo) {
                if (loginUserPojo.getSuccess().equalsIgnoreCase("1")) {

                    Toast.makeText(activity, loginUserPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PasswordActivity.this,HomeActivity.class));
                    finish();
                }

            }
        });

    }

    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    App.getAppPreference().SaveString(AppConstants.LATITUDE, String.valueOf(location.getLatitude()));
                                    App.getAppPreference().SaveString(AppConstants.LONGITUDE, String.valueOf(location.getLongitude()));

                                }
                            }
                        }
                );
            } else {
                Toast.makeText(activity, "Turn on location", Toast.LENGTH_LONG).show();
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
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
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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