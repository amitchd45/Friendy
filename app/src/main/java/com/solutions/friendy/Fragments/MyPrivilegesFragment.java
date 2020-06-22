package com.solutions.friendy.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutions.friendy.Adapters.SliderAdapters;
import com.solutions.friendy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPrivilegesFragment extends Fragment {

    private View view;
    private TextView tv_title;

//    private List<SliderItem> sliderItems =new ArrayList<>();
//    private List<SliderItem> name =new ArrayList<>();
    private ViewPager2 viewPager2;
    private Handler sliderHandler =new Handler();
    private ImageView iv_back;
    private Integer[] sliderItems={R.drawable.shadow_1,R.drawable.shadow_2,R.drawable.shadow_3};
    private String[] name={"VIP","See who like me","Step-Up"};
    private String[] subName={"VIP Privileges","Find out who like you","Allow 10x more people to view"};
    private String[] status={"VIP not yet activated","Privileges not yet activated","0 left"};



    public MyPrivilegesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_my_privileges, container, false);

        findIds(view);

        return view;
    }

    private void findIds(View view) {

        tv_title =view.findViewById(R.id.tv_title);
        tv_title.setText("My Privileges");

        iv_back=view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        viewPager2 =view.findViewById(R.id.viewPagerImageSlider);

//        sliderItems.add(new SliderItem(R.layout.vip));
//        sliderItems.add(new SliderItem(R.layout.step_up));
//        sliderItems.add(new SliderItem(R.layout.like));
//        sliderItems.add(new SliderItem(R.drawable.shadow_1));
//        sliderItems.add(new SliderItem(R.drawable.shadow_2));
//        sliderItems.add(new SliderItem(R.drawable.shadow_3));

        viewPager2.setAdapter(new SliderAdapters(sliderItems,name,subName,status, viewPager2, new SliderAdapters.Select() {
            @Override
            public void ClickSlider(int position) {

            }
        }));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                if(position==0){
                    addFrag(new VipFragment());
                }else if(position==1){
                    addFrag(new LikeFragment());
                }else if(position==2){
                    addFrag(new StepUpFragment());
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(25));
//        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r =1-Math.abs(position);
                page.setScaleY(0.85f +r * 0.20f);
//                page.setScaleY(0.85f +r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

    }

    private void addFrag(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {

            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };
    }


