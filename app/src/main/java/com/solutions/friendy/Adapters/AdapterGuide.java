package com.solutions.friendy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.R;

public class AdapterGuide extends RecyclerView.Adapter<AdapterGuide.ViewHolder> {

    Context context;
    Select select;

    public interface Select{
        void Choose(int position);
    }

    public AdapterGuide(Context context,Select select) {
        this.context = context;
        this.select = select;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {




    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select.Choose(getLayoutPosition());
                }
            });
        }
    }
}

