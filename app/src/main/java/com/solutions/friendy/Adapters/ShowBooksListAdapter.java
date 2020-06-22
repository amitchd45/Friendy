package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.R;

import java.util.List;

public class ShowBooksListAdapter extends RecyclerView.Adapter<ShowBooksListAdapter.ViewHolder> {

    Context context;
    private List<String> datalist;

    public ShowBooksListAdapter(Context context, List<String> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    //    Select select;
//
//    public interface Select{
//        void Choose(int position, String title);
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_personality_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(datalist.get(position));
//        holder.cb_industry_name.setChecked(Boolean.parseBoolean(personality_listname[position]));
//        holder.cb_industry_name.setTag(position);

//
//        holder.cb_industry_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                select.Choose(position,personality_listname[position]);
//            }
//        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                select.Choose(position,personality_listname[position]);
//
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);


        }
    }
}
