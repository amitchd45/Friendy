package com.solutions.friendy.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.omninos.util_data.CommonUtils;
import com.solutions.friendy.Adapters.SwipePagerAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Fragments.LoginFragment;
import com.solutions.friendy.Fragments.LoginFragmentDirections;
import com.solutions.friendy.Models.CheckSocialId;
import com.solutions.friendy.Models.SocialLoginPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.Retrofit.StringContract;
import com.solutions.friendy.ViewModel.VMRegisterUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;

import me.relex.circleindicator.CircleIndicator;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Button btn_phone_no, btn_continue_with_fb;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    private String idb,fbId = "", fbFirstName = "", fbLastName = "", fbEmail = "", fbSocialUserName = "", fbPhoneNumber = "", fbGender = "", fbDateOfBirth = "", fbCountry = "", fbProfilePicture = "";
    private URL fbProfilePicturenew;

    private String userName = "", userStringEmail = "", socialId = "", loginType, userImage = "", phoneNumber = "", gender = "", dateofbirth = "", country;
    private VMRegisterUser vmRegisterUser;

    private ViewPager viewPager;
    private CircleIndicator sliderDotspanel;
    private int dotscount;
    int currentPage = 0;
    int NUM_PAGES = 0;
    private SwipePagerAdapter swipePagerAdapter;
    private LoginActivity activity=LoginActivity.this;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        turnGPSOn();

        findIds();
        FacebookSdk.sdkInitialize(this.getApplication());

        setSwipe();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    private void setSwipe() {

        swipePagerAdapter = new SwipePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipePagerAdapter);
        sliderDotspanel.setViewPager(viewPager);
        swipePagerAdapter.notifyDataSetChanged();

    }

    private void findIds() {
        btn_continue_with_fb =findViewById(R.id.btn_continue_with_fb);
        btn_continue_with_fb.setOnClickListener(this);

        btn_phone_no =findViewById(R.id.btn_phone_no);
        btn_phone_no.setOnClickListener(this);
        viewPager = findViewById(R.id.viewPager);
        sliderDotspanel =findViewById(R.id.SliderDots);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_phone_no:
                startActivity(new Intent(LoginActivity.this,PhoneNumberActivity.class));
                break;

            case R.id.btn_continue_with_fb:
                facebookBtn();
                break;
        }
    }


    private void facebookBtn() {
        progressDialog.show();

        if (CommonUtils.isNetworkConnected(activity)){
            FacebookSdk.sdkInitialize(activity);

            boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

            if (!loggedOut) {
                getUserProfile(AccessToken.getCurrentAccessToken());
            }
            FacebookSdk.sdkInitialize(activity);
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    progressDialog.dismiss();
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    getUserProfile(AccessToken.getCurrentAccessToken());
                }

                @Override
                public void onCancel() {
                    // App code
                    progressDialog.dismiss();
                    Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    progressDialog.dismiss();
                    Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
        else {
            progressDialog.dismiss();
            Toast.makeText(activity, "Check internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserProfile(AccessToken currentAccessToken) {

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("myTag", object.toString());

                        moveNext(object);

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void moveNext(JSONObject object) {
        try {
            fbFirstName = object.getString("first_name");
            fbLastName = object.getString("last_name");
            fbEmail = object.getString("email");
            fbId = object.getString("id");
            String image_url = "https://graph.facebook.com/" + fbId + "/picture?type=normal";


            fbSocialUserName = fbFirstName + " " + fbLastName;

            userName = fbSocialUserName;
            userStringEmail = fbEmail;
            socialId = fbId;
            phoneNumber = fbPhoneNumber;
            gender = fbGender;
            dateofbirth = fbDateOfBirth;
            country = fbCountry;
            loginType = "Facebook";



            if (image_url != null) {
                userImage = image_url;
            } else {
                userImage = "";
            }

            CheckSocialId();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void  CheckSocialId(){
        vmRegisterUser.getSocialData(activity,socialId,"1","android").observe(LoginActivity.this, new Observer<CheckSocialId>() {
            @Override
            public void onChanged(CheckSocialId checkSocialId) {
                if(checkSocialId.getSuccess().equalsIgnoreCase("1")){
                    Toast.makeText(activity, checkSocialId.getMessage(), Toast.LENGTH_SHORT).show();
                    App.getAppPreference().saveUserDetails(AppConstants.LOGIN_DETAIL, checkSocialId);
                    App.getAppPreference().SaveString(AppConstants.TOKEN, "1");

                    progressDialog.dismiss();

//                    NavDirections directions=LoginFragmentDirections.actionLoginFragmentToHomePageFragment();
//                    Navigation.findNavController(getView()).navigate(directions);

                }else {
                    Toast.makeText(activity, checkSocialId.getMessage(), Toast.LENGTH_SHORT).show();
                    App.getSinlton().setName(userName);
                    App.getSinlton().setPhone(phoneNumber);
                    App.getSinlton().setFb_image(userImage);
                    App.getSinlton().setSocial_id(socialId);
                    App.getSinlton().setFb_email(userStringEmail);
                    App.getSinlton().setLoginType(loginType);
                    App.getSinlton().setCheckType("1");

                    vmRegisterUser.getSocialLoginFacebook(activity,App.getSinlton().getSocial_id(),"1","android",App.getSinlton().getName(),App.getSinlton().getPhone(),App.getSinlton().getFb_image(),App.getSinlton().getFb_email(),App.getSinlton().getLoginType()).observe(activity, new Observer<SocialLoginPojo>() {
                        @Override
                        public void onChanged(SocialLoginPojo socialLoginPojo) {
                            if (socialLoginPojo.getSuccess().equalsIgnoreCase("1")){

                                App.getAppPreference().SaveString("id",socialLoginPojo.getDetails().getId());
                                Toast.makeText(activity, socialLoginPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                String id=socialLoginPojo.getDetails().getId();

                                User user = new User();
                                user.setUid(id); // Replace with your uid for the user to be created.
                                user.setName(socialLoginPojo.getDetails().getName()); // Replace with the name of the user
                                user.setAvatar(userImage);
                                CometChat.createUser(user, StringContract.AppDetails.API_KEY, new CometChat.CallbackListener<User>() {
                                    @Override
                                    public void onSuccess(User user) {
                                        Log.d("createUser", user.toString());

                                        progressDialog.dismiss();
//
//                                        NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToHomePageFragment();
//                                        Navigation.findNavController(view).navigate(navDirections);

                                        Toast.makeText(activity, socialLoginPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onError(CometChatException e) {
                                        Log.e("createUser", e.getMessage());
                                    }
                                });

                            }
                            else {
                                Toast.makeText(activity, "facebook login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


//                    NavDirections directions=LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
//                    Navigation.findNavController(getView()).navigate(directions);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void turnGPSOn() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            checkPermissions();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(activity, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            Toast.makeText(activity, "off", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

    }

    private void checkPermissions() {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.VIBRATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE}, 1001);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                int count = 0;
                if (grantResults.length > 0)
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                            count++;
                    }
//
                if (count == grantResults.length) {
                    checkPermissions();
                } else if ((Build.VERSION.SDK_INT > 23 && !shouldShowRequestPermissionRationale(permissions[0])
                        && !shouldShowRequestPermissionRationale(permissions[1])
                        && !shouldShowRequestPermissionRationale(permissions[2])
                        && !shouldShowRequestPermissionRationale(permissions[3])
                        && !shouldShowRequestPermissionRationale(permissions[4])
                        && !shouldShowRequestPermissionRationale(permissions[5])
                        && !shouldShowRequestPermissionRationale(permissions[6])
                        && !shouldShowRequestPermissionRationale(permissions[7])
                        && !shouldShowRequestPermissionRationale(permissions[8]))) {
                    rationale();
                } else {
                    Toast.makeText(activity, "Permission Not granted", Toast.LENGTH_LONG).show();
                    checkPermissions();
                }
            }
//
        }
    }

    void rationale() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle("Mandatory Permissions")
                .setMessage("Manually allow permissions in App settings")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 1);

//                        SplashScreen.this.finish();
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}