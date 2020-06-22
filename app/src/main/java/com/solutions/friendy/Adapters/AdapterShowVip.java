package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.R;

public class AdapterShowVip extends RecyclerView.Adapter<AdapterShowVip.ViewHolder> {

    Context context;
    Integer[] image;
    Select select;

    public interface Select{
        void Choose(int position);
    }

    public AdapterShowVip(Context context, Integer[] image) {
        this.context = context;
        this.image = image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_vip_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.iv_vip_plan_image.setImageResource(image[position]);

    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_vip_plan_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_vip_plan_image=itemView.findViewById(R.id.iv_vip_plan_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select.Choose(getLayoutPosition());
                }
            });
        }
    }
}

