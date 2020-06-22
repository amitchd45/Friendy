//package com.solutions.friendy.Fragments;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.solutions.friendy.R;
//import com.solutions.friendy.Utills.SliderItem;
//
//import java.util.List;
//
//class SliderAdaptersPri extends RecyclerView.Adapter<SliderAdaptersPri.SliderViewHolder> {
//
//    private List<SliderItemPri> sliderItems1;
//    private ViewPager2 viewPager2;
//    private Select select;
//
//    public interface Select{
//        void ClickSlider(int position);
//    }
//
//
//    public SliderAdaptersPri(List<SliderItemPri> sliderItems1, ViewPager2 viewPager2, Select select) {
//        this.sliderItems1 = sliderItems1;
//        this.viewPager2 = viewPager2;
//        this.select = select;
//    }
//
//    @NonNull
//    @Override
//    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container1,parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
//
//        holder.setImage(sliderItems1.get(position));
//        if (position== sliderItems1.size()-2){
//            viewPager2.post(sliderRunnable);
//
//
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return sliderItems.size();
//    }
//
//    public class SliderViewHolder extends RecyclerView.ViewHolder {
//
//        private RoundedImageView imageView;
//        public SliderViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imageView=itemView.findViewById(R.id.imageSlider);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    select.ClickSlider(getLayoutPosition());
//                }
//            });
//
//        }
//
//        void setImage(SliderItem sliderItem){
//
//            imageView.setImageResource(sliderItem.getImage());
//
//        }
//    }
//
//    private Runnable sliderRunnable=new Runnable() {
//        @Override
//        public void run() {
//            sliderItems1.addAll(sliderItems);
//            notifyDataSetChanged();
//        }
//    };
//}
//
