package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.R;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    Context context;
    String[] sport_list;

    public MoviesListAdapter(Context context, String[] sport_list, Select select) {
        this.context = context;
        this.sport_list = sport_list;
        this.select = select;
    }

    Select select;

    public interface Select{
        void Choose(int position, String title, int status);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sport,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.cb_sport_name.setText(sport_list[position]);
        holder.cb_sport_name.setChecked(Boolean.parseBoolean(sport_list[position]));
//        holder.cb_industry_name.setTag(position);


        holder.cb_sport_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    select.Choose(position,sport_list[position],1);
                }
                else {
                    select.Choose(position,sport_list[position],0);
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return sport_list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_sport_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            cb_sport_name=itemView.findViewById(R.id.cb_sport_name);


        }
    }
}
