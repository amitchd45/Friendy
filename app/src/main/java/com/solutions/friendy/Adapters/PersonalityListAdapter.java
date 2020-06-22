package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.Models.GetPersonalityListModel;
import com.solutions.friendy.R;

import java.util.ArrayList;
import java.util.List;

public class PersonalityListAdapter extends RecyclerView.Adapter<PersonalityListAdapter.ViewHolder> {

    Context context;
    private List<GetPersonalityListModel.Detail> personalityList= new ArrayList<>();
    Select select;

    public interface Select{
        void Choose(int position, String title,String id,int status);
    }

    public PersonalityListAdapter(Context context, List<GetPersonalityListModel.Detail> personalityList, Select select) {
        this.context = context;
        this.personalityList = personalityList;
        this.select = select;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_personality,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.cb_industry_name.setText(personalityList.get(position).getText());

        if (personalityList.get(position).getSelectStatus().equalsIgnoreCase("1")){
            holder.cb_industry_name.setChecked(true);
        }
        else {
            holder.cb_industry_name.setChecked(false);
        }
//        holder.cb_industry_name.setChecked(Boolean.parseBoolean(personalityList.get(position).getSelectStatus()));


        holder.cb_industry_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    select.Choose(position,personalityList.get(position).getText(),personalityList.get(position).getId(),1);
                }
                else {
                    select.Choose(position,personalityList.get(position).getText(),personalityList.get(position).getId(),0);

                }

            }

        });


    }

    @Override
    public int getItemCount() {
        return personalityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_industry_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cb_industry_name=itemView.findViewById(R.id.cb_industry_name);


        }
    }
}
