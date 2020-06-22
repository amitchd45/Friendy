package com.solutions.friendy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.solutions.friendy.Models.Contact;
import com.solutions.friendy.Models.SubscribePlanPojo;
import com.solutions.friendy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShowContactsAdapter extends RecyclerView.Adapter<ShowContactsAdapter.MyViewHolder> {
    private Context context;
    private List<Contact> contactList=new ArrayList<>();
//    private ArrayList<String> nameList=new ArrayList<>();
//    private ArrayList<String> phoneList =new ArrayList<>();
    Select select;
    private int lastSelectedPosition = -1;


    public interface Select{
        void onClickPhone(String pName,String pNumber);
    }

    public ShowContactsAdapter(Context context, List<Contact> contactList, Select select) {
        this.context = context;
        this.contactList = contactList;
        this.select = select;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.contact_name.setText(contactList.get(position).getName());
        holder.contact_number.setText(contactList.get(position).getPhone());
        Picasso.with(context).load(contactList.get(position).getPhoto()).placeholder(R.drawable.ic_user).into(holder.contact_image);
        holder.contact_radio.setChecked(lastSelectedPosition == position);
        holder.contact_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select.onClickPhone(contactList.get(position).getName(),contactList.get(position).getPhone());
                lastSelectedPosition = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView contact_name,contact_number;
        private CircularImageView contact_image;
        private RadioButton contact_radio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            contact_image=itemView.findViewById(R.id.iv_ctImage);
            contact_name=itemView.findViewById(R.id.tv_ctName);
            contact_number=itemView.findViewById(R.id.tv_ctNumber);
            contact_radio=itemView.findViewById(R.id.rd_select);


        }
    }
}
