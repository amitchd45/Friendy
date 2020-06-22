package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.solutions.friendy.Models.ImageStoreModel;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class ViewSliderAdapter extends PagerAdapter {

    private Context context;
    List<ImageStoreModel> images=new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ViewSliderAdapter(Context context, List<ImageStoreModel> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.list_slide_profile_image, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

            Glide.with(context).load(AppConstants.USERIMAGE+images.get(position).getStoreImage()).into(imageView);

//        imageView.setImageResource(images.get(position));


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}