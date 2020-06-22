package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.R;

public class AdapterPhotoDemo extends RecyclerView.Adapter<AdapterPhotoDemo.ViewHolder> {

    Context context;
    private int[] realPhoto;
    private int[] check_icon;
    Select select;

    public interface Select{
        void Choose(int position);
    }

    public AdapterPhotoDemo(Context context, int[] realPhoto, int[] check_icon, Select select) {
        this.context = context;
        this.realPhoto = realPhoto;
        this.check_icon = check_icon;
        this.select = select;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_realphoto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.iv_realPhoto.setImageResource(realPhoto[position]);
        holder.iv_check.setImageResource(check_icon[position]);



    }

    @Override
    public int getItemCount() {
        return realPhoto.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_realPhoto,iv_check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_realPhoto=itemView.findViewById(R.id.iv_realPhoto);
            iv_check=itemView.findViewById(R.id.iv_check);
        }
    }
}

