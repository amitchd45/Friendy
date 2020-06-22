package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.Models.GetProfileDataModel;
import com.solutions.friendy.R;

import java.util.ArrayList;
import java.util.List;

public class ListPersonalityAdapter extends RecyclerView.Adapter<ListPersonalityAdapter.ViewHolder> {

    Context context;
    private List <GetProfileDataModel.Details.PersonalityTitle> datalist=new ArrayList<>();

    public ListPersonalityAdapter(Context context, List <GetProfileDataModel.Details.PersonalityTitle> datalist) {
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(datalist.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);


        }
    }
}
