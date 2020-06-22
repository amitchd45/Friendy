package com.solutions.friendy.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.solutions.friendy.R;


public class ProfileImageUploadFragment extends Fragment {

    private LinearLayout ll_take_photo;
    private View view;
    private ImageView iv_take_photo;


    public ProfileImageUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_image_upload, container, false);

        init(view);

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri resultUri = data.getData();
            iv_take_photo.setImageURI(resultUri);

        }else {
            Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}