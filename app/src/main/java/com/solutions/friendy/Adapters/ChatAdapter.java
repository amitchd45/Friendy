package com.solutions.friendy.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.solutions.friendy.Models.ChatModelClass;
import com.solutions.friendy.R;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<ChatModelClass> messageList;


    public ChatAdapter(Activity activity, ArrayList<ChatModelClass> messageList) {
        this.activity = activity;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (messageList.get(position).getType().equalsIgnoreCase("from")) {
            if (messageList.get(position).getMedia() != null) {
                holder.ll_receiversidelayout.setVisibility(View.GONE);
                holder.ll_sendsidelayout.setVisibility(View.GONE);
                holder.ivSender.setVisibility(View.GONE);
                holder.ivReceiver.setVisibility(View.VISIBLE);
                Glide.with(activity).load(messageList.get(position).getMedia()).into(holder.ivReceiver);
            } else {
                holder.ivReceiver.setVisibility(View.GONE);
                holder.ivSender.setVisibility(View.GONE);
                holder.ll_receiversidelayout.setVisibility(View.VISIBLE);
                holder.tv_receiver.setText(messageList.get(position).getMsg());
                holder.ll_sendsidelayout.setVisibility(View.GONE);
                holder.tvFromTime.setText(messageList.get(position).getTime());
            }
        } else {
            if (messageList.get(position).getMedia() != null) {
                holder.ll_receiversidelayout.setVisibility(View.GONE);
                holder.ll_sendsidelayout.setVisibility(View.GONE);
                holder.ivReceiver.setVisibility(View.GONE);
                holder.ivSender.setVisibility(View.VISIBLE);
                Glide.with(activity).load(messageList.get(position).getMedia()).into(holder.ivSender);

            } else {
                holder.ivReceiver.setVisibility(View.GONE);
                holder.ivSender.setVisibility(View.GONE);
                holder.ll_sendsidelayout.setVisibility(View.VISIBLE);
                holder.tv_sender.setText(messageList.get(position).getMsg());
                holder.ll_receiversidelayout.setVisibility(View.GONE);
                holder.tvMyTime.setText(messageList.get(position).getTime());

            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_receiver, tv_sender, tvMyTime, tvFromTime;
        LinearLayout ll_receiversidelayout, ll_sendsidelayout;
        RoundedImageView ivReceiver, ivSender;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_receiver = itemView.findViewById(R.id.tv_receiver);
            tv_sender = itemView.findViewById(R.id.tv_sender);
            ll_receiversidelayout = itemView.findViewById(R.id.ll_receiversidelayout);
            ll_sendsidelayout = itemView.findViewById(R.id.ll_sendsidelayout);

            tvMyTime = itemView.findViewById(R.id.tvMyTime);
            tvFromTime = itemView.findViewById(R.id.tvFromTime);
            ivReceiver = itemView.findViewById(R.id.ivReceiver);
            ivReceiver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(activity, ChatPhotoViewActivity.class);
//                    intent.putExtra("url",messageList.get(getLayoutPosition()).getMedia());
//                    activity.startActivity(intent);

                }
            });
            ivSender = itemView.findViewById(R.id.ivSender);
            ivSender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(activity, ChatPhotoViewActivity.class);
//                    intent.putExtra("url",messageList.get(getLayoutPosition()).getMedia());
//                    activity.startActivity(intent);
                }
            });

        }
    }
}
