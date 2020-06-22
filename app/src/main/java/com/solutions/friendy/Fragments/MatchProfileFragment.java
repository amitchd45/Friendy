package com.solutions.friendy.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solutions.friendy.Adapters.AdapterInterestUser_2;
import com.solutions.friendy.Adapters.ListPersonality_2Adapter;
import com.solutions.friendy.Adapters.ViewSliderAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.GetSlideUserProfileModelClass;
import com.solutions.friendy.Models.ImageStoreModel;
import com.solutions.friendy.R;
import com.solutions.friendy.ViewModel.SwipeItemsViewModelClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchProfileFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView heading;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private String user_id,uq_id;
    private FloatingActionButton btn_close;

    private ImageView iv_icon_about,iv_back,iv_image,iv_ic_industry,iv_ic_company,iv_ic_hometown;
    private TextView edit_query,tv_title_about,tv_title_info,tv_title_note;
    private List <GetSlideUserProfileModelClass.Details.PersonalityTitle> list1=new ArrayList<>();
    private List <GetSlideUserProfileModelClass.Details.InterestTitle> list2=new ArrayList<>();

    private ListPersonality_2Adapter listPersonality_2Adapter;
    private RecyclerView rv_List_personality;

    private TextView tv_user_name,tv_dob,tv_about,tv_industry,tv_company,tv_hometown,tv_title_personality;
    private RecyclerView interestRecyclerView;

    private AdapterInterestUser_2 adapterInterestUser_2;

    private SwipeItemsViewModelClass swipeItemsViewModelClass;
    List<ImageStoreModel>image_store=new ArrayList<>();

    private ProgressDialog dialog;

    public MatchProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_match_profile, container, false);
        swipeItemsViewModelClass= ViewModelProviders.of(getActivity()).get(SwipeItemsViewModelClass.class);

        intID(view);

        Context context;
        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.dismiss();
            }
        }, 4000);

        user_id= App.getSinlton().getId();
        uq_id=App.getSinlton().getUq_id();

        getProfile();

        return view;
    }

    private void intID(View view) {
        iv_back=view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        btn_close=view.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);

        heading=view.findViewById(R.id.tv_title);
//        heading.setVisibility(View.VISIBLE);
        heading.setText("Match Profile");

        viewPager = view.findViewById(R.id.viewPager);

        sliderDotspanel = view.findViewById(R.id.SliderDots);

        edit_query=view.findViewById(R.id.edit_query);


//        iv_edit_profile=view.findViewById(R.id.iv_edit_profile);
//        iv_edit_profile.setOnClickListener(this);

        iv_back=view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_user_name=view.findViewById(R.id.tv_user_name);
        tv_dob=view.findViewById(R.id.tv_dob);

        tv_title_info=view.findViewById(R.id.tv_title_info);
        iv_ic_industry=view.findViewById(R.id.iv_ic_industry);

        iv_ic_company=view.findViewById(R.id.iv_ic_company);

        tv_about=view.findViewById(R.id.tv_about);
        iv_icon_about=view.findViewById(R.id.iv_icon_about);
        tv_title_about=view.findViewById(R.id.tv_title_about);
        tv_title_note=view.findViewById(R.id.tv_title_note);

        tv_industry=view.findViewById(R.id.tv_industry);
        tv_company=view.findViewById(R.id.tv_company);
        tv_hometown=view.findViewById(R.id.tv_hometown);

        iv_ic_hometown=view.findViewById(R.id.iv_ic_hometown);

        interestRecyclerView=view.findViewById(R.id.rv_interest);

//        setSlider();


    }

    private void setSlider() {
        ViewSliderAdapter viewPagerAdapter = new ViewSliderAdapter(getActivity(),image_store);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_back:

                break;

                case R.id.btn_close:
                getActivity().onBackPressed();
                break;
        }
    }

    private void getProfile() {
        swipeItemsViewModelClass.getSlideUserProfile(getActivity(),uq_id).observe(getActivity(), new Observer<GetSlideUserProfileModelClass>() {
            @Override
            public void onChanged(GetSlideUserProfileModelClass getSlideUserProfileModelClass) {
                if (getSlideUserProfileModelClass.getSuccess().equalsIgnoreCase("1")){


                    App.getSinlton().setF_name(getSlideUserProfileModelClass.getDetails().getName());
                    App.getSinlton().setF_age(getSlideUserProfileModelClass.getDetails().getAge());
                    App.getSinlton().setF_about(getSlideUserProfileModelClass.getDetails().getAbout());
                    App.getSinlton().setF_industry(getSlideUserProfileModelClass.getDetails().getIndustryTitle());
                    App.getSinlton().setF_company(getSlideUserProfileModelClass.getDetails().getCompany());
                    App.getSinlton().setF_hometown(getSlideUserProfileModelClass.getDetails().getHomeTown());
                    App.getSinlton().setF_note(getSlideUserProfileModelClass.getDetails().getMyNote());


                   image_store.add(new ImageStoreModel(getSlideUserProfileModelClass.getDetails().getImage()));
                   image_store.add(new ImageStoreModel(getSlideUserProfileModelClass.getDetails().getImage1()));
                   image_store.add(new ImageStoreModel(getSlideUserProfileModelClass.getDetails().getImage2()));
                   image_store.add(new ImageStoreModel(getSlideUserProfileModelClass.getDetails().getImage3()));
                   image_store.add(new ImageStoreModel(getSlideUserProfileModelClass.getDetails().getImage4()));
                   image_store.add(new ImageStoreModel(getSlideUserProfileModelClass.getDetails().getImage5()));

                    setSlider();

                    list1=getSlideUserProfileModelClass.getDetails().getPersonalityTitle();
                    list2=getSlideUserProfileModelClass.getDetails().getInterestTitle();


                    adapterInterestUser_2=new AdapterInterestUser_2(getActivity(),list2);
                    interestRecyclerView.setAdapter(adapterInterestUser_2);


                    setData();
                    Toast.makeText(getActivity(), getSlideUserProfileModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "response error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {

        rv_List_personality=view.findViewById(R.id.rv_List_personality);
        tv_title_personality=view.findViewById(R.id.tv_title_personality);

        tv_user_name.setText(App.getSinlton().getF_name());
        tv_dob.setText(App.getSinlton().getF_age());

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
            iv_ic_company=view.findViewById(R.id.iv_ic_company);
            tv_title_info.setVisibility(View.VISIBLE);
            iv_ic_company.setVisibility(View.VISIBLE);
            tv_company.setText(App.getSinlton().getF_company());
        }else {
            iv_ic_company=view.findViewById(R.id.iv_ic_company);
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




        setup();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getProfile();
    }

    private void setup() {
        if (list1.size()!=0){
            tv_title_personality.setVisibility(View.VISIBLE);
            rv_List_personality.setVisibility(View.VISIBLE);
            listPersonality_2Adapter= new ListPersonality_2Adapter(getActivity(),list1);
            rv_List_personality.setAdapter(listPersonality_2Adapter);
        }else {
            tv_title_personality.setVisibility(View.GONE);
            rv_List_personality.setVisibility(View.GONE);
        }

    }
}
