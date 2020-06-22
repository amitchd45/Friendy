package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.Models.GetSlideUserProfileModelClass;
import com.solutions.friendy.R;

import java.util.List;

public class AdapterSubListInterestUser_2 extends RecyclerView.Adapter<AdapterSubListInterestUser_2.ViewHolder> {

    Context context;
    List<GetSlideUserProfileModelClass.Details.InterestTitle.InterestType> list;

    public AdapterSubListInterestUser_2(Context context, List<GetSlideUserProfileModelClass.Details.InterestTitle.InterestType> list) {
        this.context = context;
        this.list = list;
    }

    public interface Select{
        void Choose(int position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_interest_sub_list_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.mainTitle.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button mainTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainTitle=itemView.findViewById(R.id.tv_sublist_interest);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    select.Choose(getLayoutPosition());
                }
            });
        }
    }
}

