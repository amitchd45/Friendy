package com.solutions.friendy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.solutions.friendy.Adapters.AdapterInterestUser_2;
import com.solutions.friendy.Adapters.ListPersonality_2Adapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Fragments.HomePageFragment;
import com.solutions.friendy.MainActivity;
import com.solutions.friendy.Models.GetSlideUserProfileModelClass;
import com.solutions.friendy.Models.ImageStoreModel;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.Retrofit.StringContract;
import com.solutions.friendy.ViewModel.SwipeItemsViewModelClass;
import com.solutions.friendy.ViewModel.VmReceiveData;

import java.util.ArrayList;
import java.util.List;

import screen.unified.CometChatUnified;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UserProfileActivity";
    private Activity activity=UserProfileActivity.this;

    private ImageView iv_icon_about,iv_back,iv_image,iv_ic_industry,iv_ic_company,iv_ic_hometown;
    private TextView edit_query,tv_title_about,tv_title_info,tv_title_note;
    private List<GetSlideUserProfileModelClass.Details.PersonalityTitle> list1=new ArrayList<>();
    private List <GetSlideUserProfileModelClass.Details.InterestTitle> list2=new ArrayList<>();

    private ListPersonality_2Adapter listPersonality_2Adapter;
    private RecyclerView rv_List_personality;

    private TextView tv_user_name,tv_dob,tv_about,tv_industry,tv_company,tv_hometown,tv_title_personality;
    private RelativeLayout rl_personality;
    private LinearLayout ll_tv_interest_title;
    private RecyclerView interestRecyclerView;

    private AdapterInterestUser_2 adapterInterestUser_2;

    private SwipeItemsViewModelClass swipeItemsViewModelClass;
    List<ImageStoreModel>image_store=new ArrayList<>();

    private String UserID=App.getSinlton().getUq_id(),uid;
    private MaterialToolbar toolbar;
    private FloatingActionButton btn_chat;
    private ImageView iv_topProfile;
    private ProgressDialog progressDialog;
    private CollapsingToolbarLayout toolbar_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        swipeItemsViewModelClass = ViewModelProviders.of(UserProfileActivity.this).get(SwipeItemsViewModelClass.class);
        UserID=App.getSinlton().getUq_id();
        uid=App.getAppPreference().GetString("id");

        login(uid);

        initView();
        progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000);

        getUserProfile();

    }

    private void initView() {
        toolbar_layout=findViewById(R.id.toolbar_layout);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_topProfile=findViewById(R.id.iv_topProfile);

        btn_chat=findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(this);

        edit_query=findViewById(R.id.edit_query);


        rv_List_personality=findViewById(R.id.rv_List_personality);
        rl_personality=findViewById(R.id.rl_personality);
        ll_tv_interest_title=findViewById(R.id.ll_tv_interest_title);
        tv_title_personality=findViewById(R.id.tv_title_personality);

        tv_user_name=findViewById(R.id.tv_user_name);
        tv_dob=findViewById(R.id.tv_dob);

        tv_title_info=findViewById(R.id.tv_title_info);
        iv_ic_industry=findViewById(R.id.iv_ic_industry);

        iv_ic_company=findViewById(R.id.iv_ic_company);

        tv_about=findViewById(R.id.tv_about);
        iv_icon_about=findViewById(R.id.iv_icon_about);
        tv_title_about=findViewById(R.id.tv_title_about);
        tv_title_note=findViewById(R.id.tv_title_note);

        tv_industry=findViewById(R.id.tv_industry);
        tv_company=findViewById(R.id.tv_company);
        tv_hometown=findViewById(R.id.tv_hometown);

        iv_ic_hometown=findViewById(R.id.iv_ic_hometown);

        interestRecyclerView=findViewById(R.id.rv_interest);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_chat:
//                startActivity(new Intent(UserProfileActivity.this, CometChatUnified.class));
                startActivity(new Intent(UserProfileActivity.this, ConversationActivity.class));
                break;
        }
    }

    private void getUserProfile() {
        swipeItemsViewModelClass.getSlideUserProfile(this, UserID).observe(UserProfileActivity.this, new Observer<GetSlideUserProfileModelClass>() {
            @Override
            public void onChanged(GetSlideUserProfileModelClass getSlideUserProfileModelClass) {
                if (getSlideUserProfileModelClass.getSuccess().equalsIgnoreCase("1")) {

                    String name=getSlideUserProfileModelClass.getDetails().getName();
                    String image=getSlideUserProfileModelClass.getDetails().getImage();

                    App.getSinlton().setF_name(getSlideUserProfileModelClass.getDetails().getName());
                    App.getSinlton().setF_age(getSlideUserProfileModelClass.getDetails().getAge());
                    App.getSinlton().setF_about(getSlideUserProfileModelClass.getDetails().getAbout());
                    App.getSinlton().setF_industry(getSlideUserProfileModelClass.getDetails().getIndustryTitle());
                    App.getSinlton().setF_company(getSlideUserProfileModelClass.getDetails().getCompany());
                    App.getSinlton().setF_hometown(getSlideUserProfileModelClass.getDetails().getHomeTown());
                    App.getSinlton().setF_note(getSlideUserProfileModelClass.getDetails().getMyNote());

                    toolbar_layout.setTitle(name);
                    toolbar_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                    toolbar_layout.setExpandedTitleColor(getResources().getColor(R.color.white));
                    Glide.with(UserProfileActivity.this).load(AppConstants.USERIMAGE+image).into(iv_topProfile);

                    Log.d("showImage", "onChanged: " + image_store);

                    setData();

                    list1 = getSlideUserProfileModelClass.getDetails().getPersonalityTitle();
                    list2 = getSlideUserProfileModelClass.getDetails().getInterestTitle();

                    if (list1.size()>0){
                        tv_title_personality.setVisibility(View.VISIBLE);
                        rl_personality.setVisibility(View.VISIBLE);
                        listPersonality_2Adapter= new ListPersonality_2Adapter(activity,list1);
                        rv_List_personality.setAdapter(listPersonality_2Adapter);
                    }else {
                        tv_title_personality.setVisibility(View.GONE);
                        rl_personality.setVisibility(View.GONE);
                    }

                    if (list2.size()>0){
                        ll_tv_interest_title.setVisibility(View.VISIBLE);
                        adapterInterestUser_2 = new AdapterInterestUser_2(activity, list2);
                        interestRecyclerView.setAdapter(adapterInterestUser_2);
                    }else {
                        ll_tv_interest_title.setVisibility(View.GONE);

                    }
                    Toast.makeText(UserProfileActivity.this, getSlideUserProfileModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserProfileActivity.this, "response error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
        tv_user_name.setText(App.getSinlton().getF_name());
        tv_dob.setText(App.getSinlton().getF_age()+" years");

        if (!App.getSinlton().getF_about().equalsIgnoreCase("")) {
            iv_icon_about.setVisibility(View.VISIBLE);
            tv_title_about.setVisibility(View.VISIBLE);
            tv_about.setText(App.getSinlton().getF_about());
        }else {
            iv_icon_about.setVisibility(View.GONE);
            tv_title_about.setVisibility(View.GONE);
        }


        if(!App.getSinlton().getF_industry().equalsIgnoreCase("")){
            tv_title_info.setVisibility(View.VISIBLE);
            iv_ic_industry.setVisibility(View.VISIBLE);
            tv_industry.setText(App.getSinlton().getF_industry());
        }else {
            tv_title_info.setVisibility(View.GONE);
            iv_ic_industry.setVisibility(View.GONE);
        }

        if(!App.getSinlton().getF_company().equalsIgnoreCase("")){
            iv_ic_company=findViewById(R.id.iv_ic_company);
            tv_title_info.setVisibility(View.VISIBLE);
            iv_ic_company.setVisibility(View.VISIBLE);
            tv_company.setText(App.getSinlton().getF_company());
        }else {
            iv_ic_company=findViewById(R.id.iv_ic_company);
            tv_title_info.setVisibility(View.GONE);
            iv_ic_company.setVisibility(View.GONE);
        }

        if(!App.getSinlton().getF_hometown().equalsIgnoreCase("")){
            tv_title_info.setVisibility(View.VISIBLE);
            iv_ic_hometown.setVisibility(View.VISIBLE);
            tv_hometown.setText(App.getSinlton().getF_hometown());
        }else {
            tv_title_info.setVisibility(View.GONE);
            iv_ic_hometown.setVisibility(View.GONE);
        }


        if(!App.getSinlton().getF_note().equalsIgnoreCase("")){
            tv_title_note.setVisibility(View.VISIBLE);
            edit_query.setText(App.getSinlton().getF_note());
        }else {
            tv_title_note.setVisibility(View.GONE);

        }

    }

    private void login(String uid) {

        CometChat.login(uid, StringContract.AppDetails.API_KEY, new CometChat.CallbackListener<User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "onSuccess: "+user);
//                startActivity(new Intent(LoginActivity.this, SelectActivity.class));
//                finish();
            }

            @Override
            public void onError(CometChatException e) {
                Toast.makeText(UserProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
