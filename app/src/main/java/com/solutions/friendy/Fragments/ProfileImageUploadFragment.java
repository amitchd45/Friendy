package com.solutions.friendy.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.solutions.friendy.Adapters.AdapterPhotoDemo;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.LoginRegisterPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VMRegisterUser;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class ProfileImageUploadFragment extends Fragment {

    private LinearLayout ll_take_photo;
    private View view;
    private ImageView iv_back, iv_take_photo;
    private TextView tv_title;
    private Button btn_signup, btn_start;
    public Uri resultUri;
    private RecyclerView rv_photoDemo;
    private AdapterPhotoDemo adapterPhotoDemo;
    private int[] realPhoto = {R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_4, R.drawable.girl_2};
    private int[] check_icon = {R.drawable.ic_check_circle_black_24dp, R.drawable.ic_cancel_black_24dp, R.drawable.ic_cancel_black_24dp, R.drawable.ic_cancel_black_24dp};

    private String genderStr, nameStr, dobStr, passwordStr, id, fb_social_id, fb_userName, fb_phone, fb_image, fb_email, fb_loginType;
    private String imagePath = "";
    private File storeImage;

    private ProgressDialog progressDialog;
    private VMRegisterUser vmRegisterUser;


    public ProfileImageUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_image_upload, container, false);

        vmRegisterUser = ViewModelProviders.of(this).get(VMRegisterUser.class);

        nameStr = App.getAppPreference().GetString(AppConstants.RUSER_NAME);
        dobStr = App.getAppPreference().GetString(AppConstants.RDOB);
        passwordStr =App.getAppPreference().GetString(AppConstants.RPASS);
        genderStr = App.getAppPreference().GetString(AppConstants.RGENDER);
        id = App.getAppPreference().GetString("id");

        init(view);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        return view;
    }

    private void init(View view) {
        ll_take_photo=view.findViewById(R.id.ll_take_photo);
        ll_take_photo.setOnClickListener(task->{
            ImagePicker.Companion.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        iv_take_photo=view.findViewById(R.id.iv_take_photo);

        rv_photoDemo = view.findViewById(R.id.rv_photoDemo);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Upload Your Real Photo");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(task->{
            getActivity().onBackPressed();
        });

        btn_start = view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(task->{
            validation();
        });


    }

    private void validation() {
        if (imagePath.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please Upload Profile image", Toast.LENGTH_SHORT).show();
        } else {
            register();
        }
    }

    private void register() {
        progressDialog.show();
        MultipartBody.Part body;

        RequestBody nameRequestBody = RequestBody.create(MediaType.parse("text/plain"), nameStr);
        RequestBody dobRequestBody = RequestBody.create(MediaType.parse("text/plain"), dobStr);
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), passwordStr);
        RequestBody genderRequestBody = RequestBody.create(MediaType.parse("text/plain"), genderStr);
        RequestBody idRequestBody = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody latitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), "30.7333");
        RequestBody longitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), "76.7794");

//        RequestBody latitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), App.getAppPreference().GetString(AppConstants.LATITUDE));
//        RequestBody longitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), App.getAppPreference().GetString(AppConstants.LONGITUDE));

        storeImage = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), storeImage);
        body = MultipartBody.Part.createFormData("image", storeImage.getName(), requestFile);

        vmRegisterUser.registerResults(getActivity(), nameRequestBody, dobRequestBody, genderRequestBody, passwordRequestBody, idRequestBody, latitudeRequestBody, longitudeRequestBody, body).observe(getActivity(), new Observer<LoginRegisterPojo>() {
            @Override
            public void onChanged(LoginRegisterPojo loginRegisterPojo) {
                if (loginRegisterPojo.getSuccess().equalsIgnoreCase("1")) {
                    progressDialog.dismiss();
                    App.getAppPreference().SaveString("newUser", "1");
                    App.getAppPreference().SaveString("name", loginRegisterPojo.getDetails().getName());
                    App.getAppPreference().SaveString("image", loginRegisterPojo.getDetails().getImage());

                    App.getAppPreference().saveUserDetails(AppConstants.REGISTER_DETAILS, loginRegisterPojo);
                    App.getAppPreference().SaveString(AppConstants.TOKEN, "1");
                    NavDirections navDirections = UploadRealPhotoFragmentDirections.actionUploadRealPhotoFragmentToHomePageFragment();
                    Navigation.findNavController(view).navigate(navDirections);

//                    User user = new User();
//                    user.setUid(id); // Replace with your uid for the user to be created.
//                    user.setName(nameStr); // Replace with the name of the user
//                    user.setAvatar(AppConstants.USERIMAGE+loginRegisterPojo.getDetails().getImage());
//
//                    CometChat.createUser(user, StringContract.AppDetails.API_KEY, new CometChat.CallbackListener<User>() {
//                        @Override
//                        public void onSuccess(User user) {
//                            progressDialog.dismiss();
//
//                            App.getAppPreference().SaveString(AppConstants.TOKEN,"1");
//                            NavDirections navDirections = UploadRealPhotoFragmentDirections.actionUploadRealPhotoFragmentToHomePageFragment();
//                            Navigation.findNavController(view).navigate(navDirections);
//
//                            Toast.makeText(getContext(), loginRegisterPojo.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onError(CometChatException e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });

                } else {
                    Toast.makeText(getContext(), loginRegisterPojo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            resultUri = data.getData();
            iv_take_photo.setImageURI(resultUri);
            imagePath=resultUri.getPath();

        }else {
            Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}