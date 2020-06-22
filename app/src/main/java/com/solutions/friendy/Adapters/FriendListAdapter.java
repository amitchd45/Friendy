package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.solutions.friendy.Activity.ChatListActivity;
import com.solutions.friendy.Models.GetUserFriendListModelClass;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    Context context;
    List<GetUserFriendListModelClass.Detail> listFriend = new ArrayList<>();

    Select select;

    public interface Select{
        void Choose(int position,String name,String UID);
    }

    public FriendListAdapter(Context context, List<GetUserFriendListModelClass.Detail> listFriend, Select select) {
        this.context = context;
        this.listFriend = listFriend;
        this.select = select;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(!listFriend.get(position).getImage().equalsIgnoreCase(""))
        {
            Glide.with(context).load(AppConstants.USERIMAGE+listFriend.get(position).getImage()).into(holder.iv_friendImage);
        }
        holder.tv_friend_name.setText(listFriend.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select.Choose(position,listFriend.get(position).getName(),listFriend.get(position).getId());
//                ChatListActivity.start(context,listFriend.get(position).getId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return listFriend.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView iv_friendImage;
        private TextView tv_friend_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_friendImage=itemView.findViewById(R.id.iv_friendImage);
            tv_friend_name=itemView.findViewById(R.id.tv_friend_name);

        }
    }
}
