package com.solutions.friendy.ViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solutions.friendy.Models.GetSlideUserProfileModelClass;
import com.solutions.friendy.Models.GetSwipeItemsModelClass;
import com.solutions.friendy.Models.GetSwipeStatusModelClass;
import com.solutions.friendy.Models.GetUserFriendListModelClass;
import com.solutions.friendy.Retrofit.APIClient;
import com.solutions.friendy.Retrofit.APIInterface;
import com.omninos.util_data.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeItemsViewModelClass extends ViewModel {

    private APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
    private MutableLiveData<GetSwipeItemsModelClass> swipeItemsModelClassMutableLiveData;
    private MutableLiveData<GetSwipeStatusModelClass> getSwipeStatusModelClassMutableLiveData;
    private MutableLiveData<GetUserFriendListModelClass> FriendList;
    private MutableLiveData<GetSlideUserProfileModelClass> SlideUserProfile;

    public LiveData<GetSwipeItemsModelClass> getSwipeItems(final Activity activity, String userId,String latitude,String longitude) {

        swipeItemsModelClassMutableLiveData = new MutableLiveData<>();
        apiInterface.getSwipeItems(userId,latitude,longitude).enqueue(new Callback<GetSwipeItemsModelClass>() {
            @Override
            public void onResponse(Call<GetSwipeItemsModelClass> call, Response<GetSwipeItemsModelClass> response) {
                if (response.body() !=null ) {
                    swipeItemsModelClassMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetSwipeItemsModelClass> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return swipeItemsModelClassMutableLiveData;
    }

    public LiveData<GetSwipeStatusModelClass> getSwipeStatus(final Activity activity,String userId,String friendId,String status)
    {
        getSwipeStatusModelClassMutableLiveData = new MutableLiveData<>();
        apiInterface.getSwipeStatusUser(userId, friendId, status).enqueue(new Callback<GetSwipeStatusModelClass>() {
            @Override
            public void onResponse(Call<GetSwipeStatusModelClass> call, Response<GetSwipeStatusModelClass> response) {
                if(response.body()!=null)
                {
                    getSwipeStatusModelClassMutableLiveData.postValue(response.body());
                }
                else {
                    Toast.makeText(activity, "Fail response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSwipeStatusModelClass> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return getSwipeStatusModelClassMutableLiveData;
    }

    public LiveData<GetUserFriendListModelClass>getUserFriendList(final Activity activity,String userId){
        FriendList=new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.getFriendList(userId).enqueue(new Callback<GetUserFriendListModelClass>() {
                @Override
                public void onResponse(Call<GetUserFriendListModelClass> call, Response<GetUserFriendListModelClass> response) {
                    if (response.body()!=null){
                        FriendList.postValue(response.body());
                    }else {
                        Toast.makeText(activity, "response error.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetUserFriendListModelClass> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(activity, "Internet is not working.", Toast.LENGTH_SHORT).show();
        }

        return FriendList;
    }

    public LiveData<GetSlideUserProfileModelClass>getSlideUserProfile(final Activity activity,String id){

        SlideUserProfile=new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.getSlideUserProfile(id).enqueue(new Callback<GetSlideUserProfileModelClass>() {
                @Override
                public void onResponse(Call<GetSlideUserProfileModelClass> call, Response<GetSlideUserProfileModelClass> response) {
                    if (response.body()!=null){
                        SlideUserProfile.postValue(response.body());
                    }else {
                        Toast.makeText(activity, "response error.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetSlideUserProfileModelClass> call, Throwable t) {
                    Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            Toast.makeText(activity, "Check Internet Connection.", Toast.LENGTH_SHORT).show();
        }

        return SlideUserProfile;
    }
}
