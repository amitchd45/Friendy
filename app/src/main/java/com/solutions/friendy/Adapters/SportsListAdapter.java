package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.Models.GetInterestListModel;
import com.solutions.friendy.R;

import java.util.ArrayList;
import java.util.List;

public class SportsListAdapter extends RecyclerView.Adapter<SportsListAdapter.ViewHolder> {

    Context context;
    private List<GetInterestListModel.Detail> sport_datalist = new ArrayList<>();

    Select select;

    public interface Select {
        void Choose(int position, String title, int status);

        void onClick(int pos, String title);
    }

    public SportsListAdapter(Context context, List<GetInterestListModel.Detail> sport_datalist, Select select) {
        this.context = context;
        this.sport_datalist = sport_datalist;
        this.select = select;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sport, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.tv_sport.setText(sport_datalist.get(position).getTitle());
//        if (sport_datalist.get(position).getType() != null || sport_datalist.get(position).getType().size() > 0) {
//
//            ShowSportListAdapter showSportListAdapter = new ShowSportListAdapter(context, sport_datalist.get(position).getType());
//            holder.recyclerView.setAdapter(showSportListAdapter);
//
//        }

//        holder.cb_sport_name.setText(sport_datalist.get(position).getType().get(position).getTitle());
//        holder.cb_sport_name.setChecked(Boolean.parseBoolean(sport_datalist.get(position).getType().get(position).getTitle()));


//        holder.cb_sport_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    select.Choose(position,sport_datalist.get(position).getType().get(position).getTitle(),1);
//                }
//                else {
//                    select.Choose(position,sport_datalist.get(position).getType().get(position).getTitle(),1);
//                }
//
//            }
//
//        });

    }

    @Override
    public int getItemCount() {
        return sport_datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_sport_name;
        private RecyclerView recyclerView;
        TextView tv_sport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.rv_sublistInterest);
            tv_sport = itemView.findViewById(R.id.tv_sport);
//            cb_sport_name=itemView.findViewById(R.id.cb_sport_name);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select.onClick(getLayoutPosition(), sport_datalist.get(getLayoutPosition()).getTitle());
                }
            });

        }
    }
}
