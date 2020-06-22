package com.solutions.friendy.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.solutions.friendy.R;
import com.solutions.friendy.Utills.SliderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendyFragment extends Fragment implements View.OnClickListener {
    private View view;

    private TextView tv_title;
    private ImageView iv_back;
    private List<SliderItem> sliderItems = new ArrayList<>();
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();


    public FriendyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friendy, container, false);
        findIds(view);
        return view;
    }

    private void findIds(View view) {

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Friendy");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);

        sliderItems.add(new SliderItem(R.drawable.shadow));
        sliderItems.add(new SliderItem(R.drawable.shadow));
        sliderItems.add(new SliderItem(R.drawable.shadow));
        sliderItems.add(new SliderItem(R.drawable.shadow));
        sliderItems.add(new SliderItem(R.drawable.shadow));
        sliderItems.add(new SliderItem(R.drawable.shadow));

//        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2, new SliderAdapters.Select() {
//            @Override
//            public void ClickSlider(int position) {
//                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
//            }
//        }));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);

            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {

            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }
    }
}


