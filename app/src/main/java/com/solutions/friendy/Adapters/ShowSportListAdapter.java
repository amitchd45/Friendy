package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.Models.GetInterestListModel;
import com.solutions.friendy.R;

import java.util.List;

public class ShowSportListAdapter extends RecyclerView.Adapter<ShowSportListAdapter.ViewHolder> {

    Context context;
//    private List<String> datalist;

    private List<GetInterestListModel.Detail.Type> list;

    public ShowSportListAdapter(Context context, List<GetInterestListModel.Detail.Type> list,Select select) {
        this.context = context;
        this.list = list;
        this.select=select;
    }

        Select select;

    public interface Select{
        void Choose(int position, String title,String id);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_personality_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getTitle());

        if (list.get(position).getSelectStatus().equalsIgnoreCase("1")){
            holder.name.setChecked(true);
        }
        else {
            holder.name.setChecked(false);
        }


        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                select.Choose(position,list.get(position).getTitle(),list.get(position).getId());
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                select.Choose(position,list.get(position).getTitle());
//
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView name;
        CheckBox name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);


        }
    }
}
