package com.solutions.friendy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.solutions.friendy.Adapters.AdapterInterestUser;
import com.solutions.friendy.Adapters.ListPersonalityAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Fragments.ProfileFragmentDirections;
import com.solutions.friendy.Models.GetProfileDataModel;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VmReceiveData;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_edit_profile, iv_back, iv_image;
    private VmReceiveData vmReceiveData;
    private String user_id;
    private List<GetProfileDataModel.Details.PersonalityTitle> list1 = new ArrayList<>();
    private List<GetProfileDataModel.Details.InterestTitle> list2 = new ArrayList<>();

    private ListPersonalityAdapter listPersonalityAdapter;
    private RecyclerView rv_List_personality;

    private TextView tv_user_name, tv_dob, tv_about, tv_industry, tv_company, tv_hometown, tv_title_personality;
    private ImageView iv_icon_about, iv_ic_industry, iv_ic_company, iv_ic_hometown;
    private TextView edit_query, tv_title_about, tv_title_info, tv_title_note;
    private RelativeLayout rl_personality;
    private LinearLayout ll_tv_interest_title;
    private RecyclerView interestRecyclerView;
    private AdapterInterestUser adapterInterestUser;
    private MaterialToolbar toolbar;
    private FloatingActionButton btn_edit;
    private ImageView iv_topProfile;
    private ProgressDialog progressDialog;
    private CollapsingToolbarLayout toolbar_layout;
    private Activity activity=ProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        vmReceiveData = ViewModelProviders.of(this).get(VmReceiveData.class);
        user_id = App.getAppPreference().GetString("id");
        progressDialog=new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000);
        getProfile();
    }

    private void init() {


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

        btn_edit=findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(this);

        edit_query = findViewById(R.id.edit_query);

        rv_List_personality = findViewById(R.id.rv_List_personality);
        rl_personality = findViewById(R.id.rl_personality);
        ll_tv_interest_title = findViewById(R.id.ll_tv_interest_title);
        tv_title_personality = findViewById(R.id.tv_title_personality);

        tv_user_name = findViewById(R.id.tv_user_name);
        tv_dob = findViewById(R.id.tv_dob);

        tv_title_info = findViewById(R.id.tv_title_info);
        iv_ic_industry = findViewById(R.id.iv_ic_industry);

        iv_ic_company = findViewById(R.id.iv_ic_company);

        tv_about = findViewById(R.id.tv_about);
        iv_icon_about = findViewById(R.id.iv_icon_about);
        tv_title_about = findViewById(R.id.tv_title_about);
        tv_title_note = findViewById(R.id.tv_title_note);

        tv_industry = findViewById(R.id.tv_industry);
        tv_company = findViewById(R.id.tv_company);
        tv_hometown = findViewById(R.id.tv_hometown);

        iv_ic_hometown = findViewById(R.id.iv_ic_hometown);

        interestRecyclerView = findViewById(R.id.rv_interest);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back: 
                onBackPressed();
                break;
            case R.id.btn_edit:
                NavDirections navDirections = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment();
                Navigation.findNavController(view).navigate(navDirections);
                break;

        }
    }

    private void getProfile() {
        vmReceiveData.get_profile(activity, user_id).observe(ProfileActivity.this, new Observer<GetProfileDataModel>() {
            @Override
            public void onChanged(GetProfileDataModel getProfileDataModel) {
                if (getProfileDataModel.getSuccess().equalsIgnoreCase("1")) {

                    App.getSinlton().setUser_image(getProfileDataModel.getDetails().getImage());
                    App.getSinlton().setUser_image1(getProfileDataModel.getDetails().getImage1());
                    App.getSinlton().setUser_image2(getProfileDataModel.getDetails().getImage2());
                    App.getSinlton().setUser_image3(getProfileDataModel.getDetails().getImage3());
                    App.getSinlton().setUser_image4(getProfileDataModel.getDetails().getImage4());
                    App.getSinlton().setUser_image5(getProfileDataModel.getDetails().getImage5());

                    App.getAppPreference().SaveString(AppConstants.USER_NAME,getProfileDataModel.getDetails().getName());
                    App.getAppPreference().SaveString(AppConstants.USER_DOB, getProfileDataModel.getDetails().getDob());
                    App.getAppPreference().SaveString(AppConstants.USER_GENDER, getProfileDataModel.getDetails().getGender());

//                    App.getSinlton().setName(getProfileDataModel.getDetails().getName());
//                    App.getSinlton().setAge(getProfileDataModel.getDetails().getAge());
                    App.getSinlton().setAbout(getProfileDataModel.getDetails().getAbout());
                    App.getSinlton().setIndustry(getProfileDataModel.getDetails().getIndustryTitle());
                    App.getSinlton().setCompany(getProfileDataModel.getDetails().getCompany());
                    App.getSinlton().setHometown(getProfileDataModel.getDetails().getHomeTown());
                    App.getSinlton().setNote(getProfileDataModel.getDetails().getMyNote());

                    toolbar_layout.setTitle(App.getSinlton().getName());
                    toolbar_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                    toolbar_layout.setExpandedTitleColor(getResources().getColor(R.color.white));
                    Glide.with(activity).load(AppConstants.USERIMAGE+App.getSinlton().getUser_image()).into(iv_topProfile);



                    setData();

                    list1 = getProfileDataModel.getDetails().getPersonalityTitle();
                    list2 = getProfileDataModel.getDetails().getInterestTitle();

                    if (list1.size() > 0) {
                        tv_title_personality.setVisibility(View.VISIBLE);
                        rl_personality.setVisibility(View.VISIBLE);
                        listPersonalityAdapter = new ListPersonalityAdapter(activity, list1);
                        rv_List_personality.setAdapter(listPersonalityAdapter);
                    } else {
                        tv_title_personality.setVisibility(View.GONE);
                        rl_personality.setVisibility(View.GONE);
                    }

                    if (list2.size() > 0) {
                        ll_tv_interest_title.setVisibility(View.VISIBLE);
                        adapterInterestUser = new AdapterInterestUser(activity, list2);
                        interestRecyclerView.setAdapter(adapterInterestUser);
                    } else {
                        ll_tv_interest_title.setVisibility(View.GONE);

                    }

//                    Toast.makeText(activity, getProfileDataModel.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
        tv_dob.setText(App.getSinlton().getAge()+" years");
        tv_user_name.setText(App.getSinlton().getName());

        if (!App.getSinlton().getAbout().equalsIgnoreCase("")) {
            iv_icon_about.setVisibility(View.VISIBLE);
            tv_title_about.setVisibility(View.VISIBLE);
            tv_about.setText(App.getSinlton().getAbout());
        } else {
            iv_icon_about.setVisibility(View.GONE);
            tv_title_about.setVisibility(View.GONE);
        }


        if (!App.getSinlton().getIndustry().equalsIgnoreCase("")) {
            tv_title_info.setVisibility(View.VISIBLE);
            iv_ic_industry.setVisibility(View.VISIBLE);
            tv_industry.setText(App.getSinlton().getIndustry());
        } else {
            tv_title_info.setVisibility(View.GONE);
            iv_ic_industry.setVisibility(View.GONE);
        }

        if (!App.getSinlton().getCompany().equalsIgnoreCase("")) {
            iv_ic_company = findViewById(R.id.iv_ic_company);
            tv_title_info.setVisibility(View.VISIBLE);
            iv_ic_company.setVisibility(View.VISIBLE);
            tv_company.setText(App.getSinlton().getCompany());
        } else {
            iv_ic_company = findViewById(R.id.iv_ic_company);
            tv_title_info.setVisibility(View.GONE);
            iv_ic_company.setVisibility(View.GONE);
        }

        if (!App.getSinlton().getHometown().equalsIgnoreCase("")) {
            tv_title_info.setVisibility(View.VISIBLE);
            iv_ic_hometown.setVisibility(View.VISIBLE);
            tv_hometown.setText(App.getSinlton().getHometown());
        } else {
            tv_title_info.setVisibility(View.GONE);
            iv_ic_hometown.setVisibility(View.GONE);
        }


        if (!App.getSinlton().getNote().equalsIgnoreCase("")) {
            tv_title_note.setVisibility(View.VISIBLE);
            edit_query.setText(App.getSinlton().getNote());
        } else {
            tv_title_note.setVisibility(View.GONE);

        }

    }
}