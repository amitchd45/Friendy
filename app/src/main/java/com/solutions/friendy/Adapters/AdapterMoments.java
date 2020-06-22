package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.solutions.friendy.Models.GetProfileDataModel;
import com.solutions.friendy.Models.UserMOmentsPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class AdapterMoments extends RecyclerView.Adapter<AdapterMoments.ViewHolder> {

    Context context;
    private List<UserMOmentsPojo.Detail>listMoments= new ArrayList<>();

    private Select select;

    public AdapterMoments(Context context, List<UserMOmentsPojo.Detail> listMoments, Select select) {
        this.context = context;
        this.listMoments = listMoments;
        this.select = select;
    }

    public interface Select{
        void Choose(String postId);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moments_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

       Glide.with(context).load(AppConstants.USERIMAGE+listMoments.get(position).getUserImage()).into(holder.userImage);
        holder.userName.setText(listMoments.get(position).getName());
        holder.tv_postTime.setText(listMoments.get(position).getTime());
        holder.tv_title.setText(listMoments.get(position).getTitle());
        holder.tv_desc.setText(listMoments.get(position).getDescription());

        Glide.with(context).load(AppConstants.USERIMAGE+listMoments.get(position).getImage()).into(holder.postImage);

        holder.ll_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select.Choose(listMoments.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMoments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImage,postImage;
        private TextView tv_title,tv_desc,userName,tv_postTime;
        private LinearLayout ll_comments;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage=itemView.findViewById(R.id.userImage);
            postImage=itemView.findViewById(R.id.postImage);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_desc=itemView.findViewById(R.id.tv_desc);
            userName=itemView.findViewById(R.id.userName);
            tv_postTime=itemView.findViewById(R.id.tv_postTime);
            ll_comments=itemView.findViewById(R.id.ll_comments);

        }
    }
}

