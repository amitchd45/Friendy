package com.solutions.friendy.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.solutions.friendy.Models.SendContactInfoModelClass;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VmUserSetting;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title;
    private ImageView iv_back, iv_upload_help_pic, iv_check;
    private Uri resultUri;
    private Button btn_send;
    private String strDetail, strContactInfo, strEmail, strImagePath = "";
    private EditText et_detail, et_info, et_email;
    private VmUserSetting vmUserSetting;
    private LinearLayout ll_main;


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);

        vmUserSetting = ViewModelProviders.of(this).get(VmUserSetting.class);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Help");

        ll_main = view.findViewById(R.id.ll_main);
        ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = getActivity().getCurrentFocus();

                if (view!=null){
                    Toast.makeText(getActivity(), "hide KeyPad", Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        et_detail = view.findViewById(R.id.et_detail);
        et_detail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Toast.makeText(getActivity(), "hide KeyPad", Toast.LENGTH_SHORT).show();
                }
            }
        });
        et_info = view.findViewById(R.id.et_info);
        et_info.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Toast.makeText(getActivity(), "hide KeyPad", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_email = view.findViewById(R.id.et_email);
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Toast.makeText(getActivity(), "hide KeyPad", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_check = view.findViewById(R.id.iv_check);
        iv_upload_help_pic = view.findViewById(R.id.iv_upload_help_pic);
        iv_upload_help_pic.setOnClickListener(this);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.iv_upload_help_pic:
                CropImage.activity()
                        .start(getContext(), this);
                break;

            case R.id.btn_send:
                validate();
                break;
        }
    }

    private void validate() {

        strDetail = et_detail.getText().toString().trim();
        strContactInfo = et_info.getText().toString().trim();
        strEmail = et_email.getText().toString().trim();

        if (strImagePath.isEmpty()) {
            Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
        } else if (strDetail.isEmpty()) {
            et_detail.setError("Please enter your Query");
        } else if (strContactInfo.isEmpty()) {
            et_info.setError("Please enter Contact Info");
        } else if (strEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            et_email.setError("Please enter valid email address");
        } else {
            sendInfo();
        }

    }

    private void sendInfo() {
        RequestBody detailR = RequestBody.create(MediaType.parse("Text/plain"), strDetail);
        RequestBody emailR = RequestBody.create(MediaType.parse("Text/plain"), strEmail);

        File file = new File(strImagePath);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        RequestBody contactInfoR = RequestBody.create(MediaType.parse("Text/plain"), strContactInfo);

        vmUserSetting.getSendContactInfo(getActivity(), detailR, emailR, image, contactInfoR).observe(HelpFragment.this, new Observer<SendContactInfoModelClass>() {
            @Override
            public void onChanged(SendContactInfoModelClass sendContactInfoModelClass) {
                if (sendContactInfoModelClass.getSuccess().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), sendContactInfoModelClass.getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), sendContactInfoModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                if (resultUri != null) {
                    strImagePath = resultUri.getPath();
                    iv_check.setVisibility(View.VISIBLE);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
