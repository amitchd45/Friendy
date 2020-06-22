package com.solutions.friendy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions.friendy.App;

import com.solutions.friendy.ClickListner.SwipeClickListener;
import com.solutions.friendy.Models.GetSwipeItemsModelClass;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Manjinder Singh on 27 , January , 2020
 */
public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.MyViewHolder> {

    List<GetSwipeItemsModelClass.Detail> usersProfilesList;
    List<GetSwipeItemsModelClass.Detail> userImagesList;

    private Context context;

    private SwipeClickListener swipeClickListener;
    int photoFlag = 0;

    int photoCount;

    public CardStackAdapter(Activity context, List<GetSwipeItemsModelClass.Detail> imagesList) {
        this.context = context;
        this.usersProfilesList = imagesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView personImageView;
        public TextView nameTextView;
        public TextView ageTextView;
        public TextView tv_address;
        public TextView photoCountTextView;
        LinearLayout previousPhotoView, nextPhotoView, profileView;

        public MyViewHolder(View view) {
            super(view);
            personImageView = view.findViewById(R.id.swipe_image_view);
            nameTextView = view.findViewById(R.id.name_text_view_item);
            ageTextView = view.findViewById(R.id.age_text_view_item);
            tv_address = view.findViewById(R.id.tv_address);

            previousPhotoView = view.findViewById(R.id.previous_photo_view);
            nextPhotoView = view.findViewById(R.id.next_photo_view);
            profileView = view.findViewById(R.id.profile_view_partition);

            photoCountTextView = view.findViewById(R.id.number_images);

//            previousPhotoView.setOnClickListener(this);
//            nextPhotoView.setOnClickListener(this);
//            profileView.setOnClickListener(this);
//            profileView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    swipeClickListener.bottomClick(v,getAdapterPosition(),usersProfilesList.get(getLayoutPosition()).getId());
//                }
//            });

        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId())
//            {
//                case R.id.previous_photo_view:
//                    if (swipeClickListener != null) {
//                        swipeClickListener.leftClick(v, getAdapterPosition());
//                    }
//                    break;
//
//                case R.id.next_photo_view:
//                    if (swipeClickListener != null) {
//                        swipeClickListener.rightClick(v, getAdapterPosition());
//                    }
//                    break;
//
//                case R.id.profile_view_partition:
//                    if (swipeClickListener != null) {
//                        swipeClickListener.bottomClick(v, getAdapterPosition(),);
//                    }
//                    break;
//            }
//        }
    }

    public void setItemClickListener(SwipeClickListener itemClickListener) {
        this.swipeClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CardStackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.swipe_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CardStackAdapter.MyViewHolder myViewHolder, int i) {

        photoCount = 0;

        final GetSwipeItemsModelClass.Detail swipeItemsModelClass = usersProfilesList.get(i);
//        GetSwipeItemsModelClass.Detail.UserImage imagesObj = userImagesList.get(0);
//        myViewHolder.personImageView.setImageResource(Integer.getInteger(swipeItemsModelClass.getUserImage().getImage()));
//
        if(!swipeItemsModelClass.getImage().equalsIgnoreCase(""))
            photoCount++;

        if(!swipeItemsModelClass.getImage1().equalsIgnoreCase(""))
            photoCount++;

        if(!swipeItemsModelClass.getImage2().equalsIgnoreCase(""))
            photoCount++;

        if(!swipeItemsModelClass.getImage3().equalsIgnoreCase(""))
            photoCount++;

        if(!swipeItemsModelClass.getImage4().equalsIgnoreCase(""))
            photoCount++;

        if(!swipeItemsModelClass.getImage5().equalsIgnoreCase(""))
            photoCount++;


        Picasso.with(context).load(AppConstants.USERIMAGE+swipeItemsModelClass.getImage()).placeholder(R.drawable.avatar).into(myViewHolder.personImageView);

//        RequestOptions requestOption = new RequestOptions();
//                requestOption.placeholder(R.drawable.placeholder).centerCrop();
//        GlideApp.with(context).load(swipeItemsModelClass.getUserImage().getImage())
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .apply(requestOption)
//                .into(myViewHolder.personImageView);


        myViewHolder.nameTextView.setText(swipeItemsModelClass.getName() + ", ");
        myViewHolder.ageTextView.setText(String.valueOf(swipeItemsModelClass.getAge())+" Year");
        myViewHolder.tv_address.setText(swipeItemsModelClass.getAddress());
        myViewHolder.photoCountTextView.setText(photoCount + " images");

        myViewHolder.previousPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.getAppPreference().GetString("swiped").equalsIgnoreCase("1"))
                {
                    photoFlag = 0;
                    App.getAppPreference().SaveString("swiped", "0");
                }
                if(photoFlag != 0)
                {
                    photoFlag--;
                    setPhoto(swipeItemsModelClass, myViewHolder);
                }

            }
        });

        myViewHolder.nextPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoCount = 0;
                if(App.getAppPreference().GetString("swiped").equalsIgnoreCase("1"))
                {
                    photoFlag = 0;
                    App.getAppPreference().SaveString("swiped", "0");
                }
                if(!swipeItemsModelClass.getImage().equalsIgnoreCase(""))
                    photoCount++;

                if(!swipeItemsModelClass.getImage1().equalsIgnoreCase(""))
                    photoCount++;

                if(!swipeItemsModelClass.getImage2().equalsIgnoreCase(""))
                    photoCount++;

                if(!swipeItemsModelClass.getImage3().equalsIgnoreCase(""))
                    photoCount++;

                if(!swipeItemsModelClass.getImage4().equalsIgnoreCase(""))
                    photoCount++;

                if(!swipeItemsModelClass.getImage5().equalsIgnoreCase(""))
                    photoCount++;
                if(photoFlag != (photoCount-1))
                {
                    photoFlag++;
                    setPhoto(swipeItemsModelClass, myViewHolder);
                }

            }
        });

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeClickListener.bottomClick(v,i,usersProfilesList.get(i).getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return usersProfilesList.size();
    }

    void setPhoto(GetSwipeItemsModelClass.Detail swipeItemsModelClass, MyViewHolder  myViewHolder)
    {
        switch (photoFlag)
        {
            case 0:
                if(!swipeItemsModelClass.getImage().equalsIgnoreCase(""))
                    Picasso.with(context).load(AppConstants.USERIMAGE+swipeItemsModelClass.getImage()).placeholder(R.drawable.avatar).into(myViewHolder.personImageView);
                break;

            case 1:
                if(!swipeItemsModelClass.getImage1().equalsIgnoreCase(""))
                    Picasso.with(context).load(AppConstants.USERIMAGE+swipeItemsModelClass.getImage1()).placeholder(R.drawable.avatar).into(myViewHolder.personImageView);
                break;

            case 2:
                if(!swipeItemsModelClass.getImage2().equalsIgnoreCase(""))
                    Picasso.with(context).load(AppConstants.USERIMAGE+swipeItemsModelClass.getImage2()).placeholder(R.drawable.avatar).into(myViewHolder.personImageView);
                break;

            case 3:
                if(!swipeItemsModelClass.getImage3().equalsIgnoreCase(""))
                    Picasso.with(context).load(AppConstants.USERIMAGE+swipeItemsModelClass.getImage3()).placeholder(R.drawable.avatar).into(myViewHolder.personImageView);
                break;

            case 4:
                if(!swipeItemsModelClass.getImage4().equalsIgnoreCase(""))
                    Picasso.with(context).load(AppConstants.USERIMAGE+swipeItemsModelClass.getImage4()).placeholder(R.drawable.avatar).into(myViewHolder.personImageView);
                break;

            case 5:
                if(!swipeItemsModelClass.getImage5().equalsIgnoreCase(""))
                    Picasso.with(context).load(AppConstants.USERIMAGE+swipeItemsModelClass.getImage5()).placeholder(R.drawable.avatar).into(myViewHolder.personImageView);
                break;
        }
    }
}

