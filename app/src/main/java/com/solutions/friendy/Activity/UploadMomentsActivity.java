package com.solutions.friendy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.AddUserMomentsPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VmUserSetting;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadMomentsActivity extends AppCompatActivity {
    private TextView tv_title;
    private EditText tv_titleName,tv_desc,tv_location;
    private ImageView iv_back,iv_uploadImage;
    private VmUserSetting vmUserSetting;
    private String userId,strTitle,strDesc,strLocation,imagePath="";
    private Button btn_send;
    private Boolean imageCheck=false;
    private ProgressDialog progressDialog;
    public Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_moments);
        vmUserSetting= ViewModelProviders.of(this).get(VmUserSetting.class);
        userId=App.getAppPreference().GetString("id");
        init();
        progressDialog=new ProgressDialog(this);
    }

    private void init() {
        tv_titleName=findViewById(R.id.tv_titleName);
        tv_desc=findViewById(R.id.tv_desc);
        tv_location=findViewById(R.id.tv_location);
        iv_uploadImage=findViewById(R.id.iv_uploadImage);
        iv_uploadImage.setOnClickListener(task->{
            imageCheck = true;
            selectImage();
        });

        btn_send=findViewById(R.id.btn_send);
        btn_send.setOnClickListener(task->{
            Validate();
        });

        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("Add a Caption");
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(task->{
            onBackPressed();
        });
    }

    private void Validate() {
        strTitle=tv_titleName.getText().toString().trim();
        strDesc=tv_desc.getText().toString().trim();
        strLocation=tv_location.getText().toString().trim();

        if (strTitle.isEmpty()){
            tv_titleName.setError("enter your title");
        }else if (strDesc.isEmpty()){
            tv_desc.setError("enter your Description");
        }else if (strLocation.isEmpty()){
            tv_location.setError("enter your Location");
        }else if (imagePath.isEmpty()){
            tv_location.setError("Upload your moments photo");
        }
        else {
            upload();
        }

    }

    private void upload() {

        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestBody idRequestBody = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody titleRequestBody = RequestBody.create(MediaType.parse("text/plain"), strTitle);
        RequestBody descRequestBody = RequestBody.create(MediaType.parse("text/plain"), strDesc);
        RequestBody locationRequestBody = RequestBody.create(MediaType.parse("text/plain"), strLocation);

        MultipartBody.Part body;

        if (imageCheck) {
            File storeImage = new File(String.valueOf(imagePath));

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), storeImage);
            body = MultipartBody.Part.createFormData("image", storeImage.getName(), requestFile);

        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body = MultipartBody.Part.createFormData("image", "", requestFile);
        }

        vmUserSetting.UpdateMomentsList(UploadMomentsActivity.this,idRequestBody,titleRequestBody,descRequestBody,locationRequestBody,body).observe(UploadMomentsActivity.this, new Observer<AddUserMomentsPojo>() {
            @Override
            public void onChanged(AddUserMomentsPojo addUserMomentsPojo) {
                if (addUserMomentsPojo.getSuccess().equalsIgnoreCase("1")){
                    Toast.makeText(UploadMomentsActivity.this, ""+addUserMomentsPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    tv_titleName.setText("");
                    tv_desc.setText("");
                    tv_location.setText("");
                    imagePath="";


                }else {
                    Toast.makeText(UploadMomentsActivity.this, ""+addUserMomentsPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void selectImage() {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK && data != null) {

                        resultUri = data.getData();

                        if (resultUri != null) {
                            imagePath = getRealPathFromURI(resultUri);
                            Glide.with(this).load(imagePath).placeholder(R.drawable.add).into(iv_uploadImage);
                        }

                    }
                    break;
            }
        }


//        try {
//            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                if (resultCode == RESULT_OK) {
//                    resultUri = result.getUri();
//                    if (position.equals("00")) {
//                        image_file = new File(resultUri.getPath());
//                        imagePath = resultUri.getPath();
//                        App.getAppPreference().SaveString("image", imagePath);
//                        Glide.with(getActivity()).load(App.getAppPreference().GetString("image")).into(iv_take_image);
//                    } else if (position.equals("01")) {
//                        image1_file = new File(resultUri.getPath());
//                        String image1Path = resultUri.getPath();
//                        App.getAppPreference().SaveString("image1", image1Path);
//                        Glide.with(getActivity()).load(App.getAppPreference().GetString("image1")).into(iv_take_image1);
//
//                    } else if (position.equals("02")) {
//                        image2_file = new File(resultUri.getPath());
//                        String image2Path = resultUri.getPath();
//                        App.getAppPreference().SaveString("image2", image2Path);
//                        Glide.with(getActivity()).load(App.getAppPreference().GetString("image2")).into(iv_take_image2);
//                    } else if (position.equals("03")) {
//                        image3_file = new File(resultUri.getPath());
//                        String image3Path = resultUri.getPath();
//                        App.getAppPreference().SaveString("image3", image3Path);
//                        Glide.with(getActivity()).load(App.getAppPreference().GetString("image3")).into(iv_take_image3);
//                    } else if (position.equals("04")) {
//                        image4_file = new File(resultUri.getPath());
//                        String image4Path = resultUri.getPath();
//                        App.getAppPreference().SaveString("image4", image4Path);
//                        Glide.with(getActivity()).load(App.getAppPreference().GetString("image4")).into(iv_take_image4);
//                    } else if (position.equals("05")) {
//                        image5_file = new File(resultUri.getPath());
//                        String image5Path = resultUri.getPath();
//                        App.getAppPreference().SaveString("image5", image5Path);
//                        Glide.with(getActivity()).load(App.getAppPreference().GetString("image5")).into(iv_take_image5);
//                    } else {
//                        Toast.makeText(getActivity(), "Not found any position", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                    Exception erroe = result.getError();
//                    Toast.makeText(getActivity(), "error,please try again.", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }
}