package com.solutions.friendy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.solutions.friendy.Models.ChatModelClass;
import com.solutions.friendy.Models.SubscribePlanPojo;
import com.solutions.friendy.R;

import java.util.ArrayList;
import java.util.List;


public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {
    private Context context;
    private List<SubscribePlanPojo.Detail> planList= new ArrayList<>();
    Select select;
    int index=-1;

    public interface Select{
        void planClick(String id);
    }

    public PlanAdapter(Context context, List<SubscribePlanPojo.Detail> planList, Select select) {
        this.context = context;
        this.planList = planList;
        this.select = select;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_offer_alert1.setText(String.valueOf(planList.get(position).getDiscount()+" off"));
        holder.tv_period_alert1.setText(planList.get(position).getMonth());
        holder.tv_price_alert1.setText(planList.get(position).getTitle());
        holder.tv_fullPrice_alert1.setText("₹"+planList.get(position).getPlan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select.planClick(planList.get(position).getId());
                index=position;
                notifyDataSetChanged();
//                if (index == position) {
//                    if (holder.tv_offer_alert1.getVisibility() == View.VISIBLE) {
//                        holder.tv_offer_alert1.setVisibility(View.INVISIBLE);
//                        holder.alert_subscription_banner1.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_banner));
//                    }
//                    else {
//                        holder.tv_offer_alert1.setVisibility(View.VISIBLE);
//                        holder.alert_subscription_banner1.setBackground(null);
//                    }
//                }

            }
        });

        if (index==position){
            if (position == 2) {
                holder.tv_offer_alert1.setText("");
                holder.tv_offer_alert1.setVisibility(View.INVISIBLE);
            }
            holder.tv_offer_alert1.setVisibility(View.VISIBLE);
            holder.alert_subscription_banner1.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_banner));
            holder.tv_period_alert1.setTextColor(Color.parseColor("#fc5068"));
            holder.tv_period_alert1_1.setTextColor(Color.parseColor("#fc5068"));
            holder.tv_price_alert1.setTextColor(Color.parseColor("#fc5068"));
            holder.tv_fullPrice_alert1.setTextColor(Color.parseColor("#fc5068"));

        }
        else{
            holder.tv_offer_alert1.setVisibility(View.INVISIBLE);
            holder.alert_subscription_banner1.setBackground(null);
            holder.tv_period_alert1.setTextColor(Color.BLACK);
            holder.tv_period_alert1_1.setTextColor(Color.BLACK);
            holder.tv_price_alert1.setTextColor(Color.BLACK);
            holder.tv_fullPrice_alert1.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_period_alert1,tv_price_alert1,tv_fullPrice_alert1,tv_offer_alert1,tv_period_alert1_1;
        private RelativeLayout alert_subscription_banner1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_period_alert1_1=itemView.findViewById(R.id.tv_period_alert1_1);
            tv_period_alert1=itemView.findViewById(R.id.tv_period_alert1);
            tv_price_alert1=itemView.findViewById(R.id.tv_price_alert1);
            tv_fullPrice_alert1=itemView.findViewById(R.id.tv_fullPrice_alert1);
            tv_offer_alert1=itemView.findViewById(R.id.tv_offer_alert1);
            alert_subscription_banner1=itemView.findViewById(R.id.alert_subscription_banner1);

        }
    }
}
