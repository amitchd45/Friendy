package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.Models.GetSlideUserProfileModelClass;
import com.solutions.friendy.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterInterestUser_2 extends RecyclerView.Adapter<AdapterInterestUser_2.ViewHolder> {

    Context context;
    List<GetSlideUserProfileModelClass.Details.InterestTitle> list;

    private AdapterSubListInterestUser_2 adapterSubListInterestUser_2;
    List<GetSlideUserProfileModelClass.Details.InterestTitle.InterestType> subList=new ArrayList<>();


    public AdapterInterestUser_2(Context context, List<GetSlideUserProfileModelClass.Details.InterestTitle> list) {
        this.context = context;
        this.list = list;
    }

    public interface Select{
        void Choose(int position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_interest_1_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.mainTitle.setText(list.get(position).getTitle());

        if(list.get(position).getInterestType().size()>0||list.get(position).getInterestType()!=null)
        {

            subList=list.get(position).getInterestType();

            adapterSubListInterestUser_2=new AdapterSubListInterestUser_2(context,subList);

            holder.recyclerView.setAdapter(adapterSubListInterestUser_2);

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mainTitle;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainTitle=itemView.findViewById(R.id.tv_mainInterest);
            recyclerView=itemView.findViewById(R.id.rv_sublistInterest);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    select.Choose(getLayoutPosition());
                }
            });
        }
    }
}

