package com.solutions.friendy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.solutions.friendy.Adapters.AdapterMoments;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.UserMOmentsPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VmUserSetting;

import java.util.ArrayList;
import java.util.List;

public class UserMomentsActivity extends AppCompatActivity {

    private RecyclerView rv_momentsList;
    private AdapterMoments adapterMoments;
    private TextView tv_title,not_fond;
    private ImageView iv_back;
    private VmUserSetting vmUserSetting;
    private String userId;
    private List<UserMOmentsPojo.Detail>listMoments= new ArrayList<>();
    private ProgressBar progress_circular;
    private FloatingActionButton add_caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_moments);

        vmUserSetting= ViewModelProviders.of(this).get(VmUserSetting.class);
        userId= App.getAppPreference().GetString("id");
        init();
        list();

    }

    private void list() {

        vmUserSetting.GetMomentsList(UserMomentsActivity.this,userId).observe(UserMomentsActivity.this, new Observer<UserMOmentsPojo>() {
            @Override
            public void onChanged(UserMOmentsPojo userMOmentsPojo) {
                if (userMOmentsPojo.getSuccess().equalsIgnoreCase("1")){
                    Toast.makeText(UserMomentsActivity.this, ""+userMOmentsPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    listMoments=userMOmentsPojo.getDetails();
                    adapterMoments = new AdapterMoments(UserMomentsActivity.this, listMoments, new AdapterMoments.Select() {
                        @Override
                        public void Choose(String postId) {
                            App.getAppPreference().SaveString(AppConstants.COMT_POST_ID,postId);
                            BottonSheetDialogOpenFragment bottonSheetDialogOpenFragment = new BottonSheetDialogOpenFragment();
                            bottonSheetDialogOpenFragment.show(getSupportFragmentManager(), bottonSheetDialogOpenFragment.getTag());

                        }
                    });
                    rv_momentsList.setAdapter(adapterMoments);
                    progress_circular.setVisibility(View.GONE);
                }else {
                    progress_circular.setVisibility(View.GONE);
                    not_fond.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void init() {
        add_caption=findViewById(R.id.add_caption);
        progress_circular=findViewById(R.id.progress_circular);
        rv_momentsList=findViewById(R.id.rv_momentsList);
        tv_title=findViewById(R.id.tv_title);
        not_fond=findViewById(R.id.not_fond);
        tv_title.setText("Moments");
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(task->{
            onBackPressed();
        });
        add_caption.setOnClickListener(task->{
            startActivity(new Intent(UserMomentsActivity.this,UploadMomentsActivity.class));
        });

    }
}