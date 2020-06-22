package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.Models.GetIndustryListModel;
import com.solutions.friendy.R;

import java.util.ArrayList;
import java.util.List;

public class IndustryListAdapter extends RecyclerView.Adapter<IndustryListAdapter.ViewHolder> {

    Context context;
    private List<GetIndustryListModel.Detail> industryList=new ArrayList<>();

    public IndustryListAdapter(Context context, List<GetIndustryListModel.Detail> industryList, Select select) {
        this.context = context;
        this.industryList = industryList;
        this.select = select;
    }

    Select select;

    public interface Select{
        void Choose(int position, GetIndustryListModel.Detail title,GetIndustryListModel.Detail id);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_industry,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_industry_name.setText(industryList.get(position).getTitile());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select.Choose(position,industryList.get(position),industryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return industryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_industry_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_industry_name=itemView.findViewById(R.id.tv_industry_name);


        }
    }
}
