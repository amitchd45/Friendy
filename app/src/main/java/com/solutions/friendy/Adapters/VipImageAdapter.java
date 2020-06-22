//package com.solutions.friendy.Adapters;
//
//import android.content.Context;
//import android.os.Parcelable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.viewpager.widget.PagerAdapter;
//
//import com.solutions.friendy.R;
//
//import java.util.ArrayList;
//
//public class VipImageAdapter extends PagerAdapter {
//
//
//    private int[] layouts;
//    private LayoutInflater inflater;
//    private Context context;
//
//    public VipImageAdapter(ArrayList<Integer> layouts, Context context) {
//        this.layouts = layouts;
//        this.context = context;
//
//        inflater = LayoutInflater.from(context);
//    }
//
//
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
//
//    @Override
//    public int getCount() {
//        return layouts.length;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup view, int position) {
//        View imageLayout = inflater.inflate(R.layout.list_vip, view, false);
//
//        assert imageLayout != null;
//        final ImageView imageView = (ImageView) imageLayout
//                .findViewById(R.id.image);
//
//
//        imageView.setImageResource(layouts[position]);
//
//        view.addView(imageLayout, 0);
//
//        return imageLayout;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view.equals(object);
//    }
//
//    @Override
//    public void restoreState(Parcelable state, ClassLoader loader) {
//    }
//
//    @Override
//    public Parcelable saveState() {
//        return null;
//    }
//
//
//}