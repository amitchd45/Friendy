package com.solutions.friendy.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.solutions.friendy.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private Integer[] IMAGES;
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Integer[] IMAGES, Context context) {
        this.IMAGES = IMAGES;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }





    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return IMAGES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = layoutInflater.inflate(R.layout.list_image_slider, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.imageView_sale);


        imageView.setImageResource(IMAGES[position]);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
