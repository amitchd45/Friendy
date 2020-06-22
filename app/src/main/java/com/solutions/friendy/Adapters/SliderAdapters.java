package com.solutions.friendy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.solutions.friendy.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class SliderAdapters extends RecyclerView.Adapter<SliderAdapters.SliderViewHolder> {

//    private List<SliderItem> sliderItems;
    private Integer[] sliderItems;
    private String[] name;
    private String[] subName;
    private String[] status;
    private ViewPager2 viewPager2;
    private Select select;

    public interface Select{
        void ClickSlider(int position);
    }

    public SliderAdapters(Integer[] sliderItems, String[] name, String[] subName, String[] status, ViewPager2 viewPager2, Select select) {
        this.sliderItems = sliderItems;
        this.name = name;
        this.subName = subName;
        this.status = status;
        this.viewPager2 = viewPager2;
        this.select = select;
    }

    //    public SliderAdapters(List<SliderItem> sliderItems, ViewPager2 viewPager2, Select select) {
//        this.sliderItems = sliderItems;
//        this.viewPager2 = viewPager2;
//        this.select = select;
//    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        holder.imageView.setImageResource(sliderItems[position]);
        holder.tv_vip_name.setText(name[position]);
        holder.tv_subName.setText(subName[position]);
        holder.tv_verification_text.setText(status[position]);
//        if (position== sliderItems.size()-2){
//            viewPager2.post(sliderRunnable);
//        }

    }

    @Override
    public int getItemCount() {
        return sliderItems.length;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;
        private TextView tv_vip_name,tv_subName,tv_verification_text;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_verification_text=itemView.findViewById(R.id.tv_verification_text);
            tv_subName=itemView.findViewById(R.id.tv_subName);
            tv_vip_name=itemView.findViewById(R.id.tv_vip_name);
            imageView=itemView.findViewById(R.id.imageSlider);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select.ClickSlider(getLayoutPosition());
                }
            });
        }

//        void setImage(SliderItem sliderItem){
//
//            imageView.setImageResource(sliderItem.getImage());
//
//        }
    }

//    private Runnable sliderRunnable=new Runnable() {
//        @Override
//        public void run() {
//
//            sliderItems.addAll(sliderItems);
//            notifyDataSetChanged();
//        }
//    };
}
