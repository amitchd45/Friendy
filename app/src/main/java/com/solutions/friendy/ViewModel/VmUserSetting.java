package com.solutions.friendy.ViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omninos.util_data.CommonUtils;
import com.solutions.friendy.Models.AddUserMomentsPojo;
import com.solutions.friendy.Models.GetUserSendContactList;
import com.solutions.friendy.Models.GetUserSettingModelClass;
import com.solutions.friendy.Models.PurchagePlanPojo;
import com.solutions.friendy.Models.SendContactInfoModelClass;
import com.solutions.friendy.Models.SendCrushPojo;
import com.solutions.friendy.Models.SocialLoginPojo;
import com.solutions.friendy.Models.SubscribePlanPojo;
import com.solutions.friendy.Models.UpdatePhonePojo;
import com.solutions.friendy.Models.UserMOmentsPojo;
import com.solutions.friendy.Models.UserSettingModelClass;
import com.solutions.friendy.Retrofit.APIClient;
import com.solutions.friendy.Retrofit.APIInterface;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VmUserSetting extends ViewModel {
    private MutableLiveData<UserSettingModelClass> userSettingModelClass;
    private MutableLiveData<GetUserSettingModelClass> getUserSettingModelClass;
    private APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    //------get userSetting data---------

    public LiveData<GetUserSettingModelClass>settingDetails(final Activity activity,String userId){

        getUserSettingModelClass=new MutableLiveData<>();
        apiInterface.getSettingData(userId).enqueue(new Callback<GetUserSettingModelClass>() {
            @Override
            public void onResponse(Call<GetUserSettingModelClass> call, Response<GetUserSettingModelClass> response) {
                if (response.body()!=null){
                    getUserSettingModelClass.postValue(response.body());
                }
                else {
                    Toast.makeText(activity, "response error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserSettingModelClass> call, Throwable t) {

                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        return getUserSettingModelClass;
    }

    public LiveData<UserSettingModelClass>filterUser(final Activity activity,String userId,String automatic_distance,String gender,String age,String distance){

        userSettingModelClass=new MutableLiveData<>();
        apiInterface.getUserSetting(userId, automatic_distance, gender, age, distance).enqueue(new Callback<UserSettingModelClass>() {
            @Override
            public void onResponse(Call<UserSettingModelClass> call, Response<UserSettingModelClass> response) {
                if (response.body()!=null){
                    userSettingModelClass.postValue(response.body());
                }else {
                    Toast.makeText(activity, "Response error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSettingModelClass> call, Throwable t) {
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return userSettingModelClass;
    }

    //  for help info send----

    private MutableLiveData<SendContactInfoModelClass>sendContactInfoModel;

    public LiveData<SendContactInfoModelClass>getSendContactInfo(final Activity activity, RequestBody details,RequestBody email,MultipartBody.Part image, RequestBody contactInfo){

        sendContactInfoModel=new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){
            CommonUtils.showProgress(activity);

            apiInterface.getContactInfo(details,email,image, contactInfo).enqueue(new Callback<SendContactInfoModelClass>() {
                @Override
                public void onResponse(Call<SendContactInfoModelClass> call, Response<SendContactInfoModelClass> response) {
                    CommonUtils.dismissProgress();
                    if (response.body()!=null){
                        sendContactInfoModel.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<SendContactInfoModelClass> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(activity, "Network Issue.", Toast.LENGTH_SHORT).show();
        }

        return sendContactInfoModel;
    }

    //update phone

    private MutableLiveData<UpdatePhonePojo> updatePhonePojoMutableLiveData;
    public LiveData<UpdatePhonePojo>getPhone(final Activity activity,String phone,String userId){
        updatePhonePojoMutableLiveData= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.updatePhone(phone, userId).enqueue(new Callback<UpdatePhonePojo>() {
                @Override
                public void onResponse(Call<UpdatePhonePojo> call, Response<UpdatePhonePojo> response) {
                    if (response.body()!=null){
                        updatePhonePojoMutableLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<UpdatePhonePojo> call, Throwable t) {

                }
            });

        }else {
            Toast.makeText(activity, "Network not connected.", Toast.LENGTH_SHORT).show();
        }
        return updatePhonePojoMutableLiveData;
    }

    //delete Account

    private MutableLiveData<Map> mapMutableLiveData;
    public LiveData<Map>deleteAcc(final Activity activity,String id){
        mapMutableLiveData= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.deleteAccount(id).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (response.body()!=null){
                        mapMutableLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<Map> call, Throwable t) {

                }
            });

        }else {
            Toast.makeText(activity, "Network not connected.", Toast.LENGTH_SHORT).show();
        }
        return mapMutableLiveData;
    }


    //SubscribePlan

    private MutableLiveData<SubscribePlanPojo> subscribePlanPojoMutableLiveData;
    public LiveData<SubscribePlanPojo>planShow(final Activity activity){
        subscribePlanPojoMutableLiveData= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.getSubscribePlan().enqueue(new Callback<SubscribePlanPojo>() {
                @Override
                public void onResponse(Call<SubscribePlanPojo> call, Response<SubscribePlanPojo> response) {
                    if (response.body()!=null){
                        subscribePlanPojoMutableLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<SubscribePlanPojo> call, Throwable t) {
                    Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            Toast.makeText(activity, "Network not connected.", Toast.LENGTH_SHORT).show();
        }
        return subscribePlanPojoMutableLiveData;
    }


    //planBuy

    private MutableLiveData<PurchagePlanPojo> purchagePlanPojoMutableLiveData;
    public LiveData<PurchagePlanPojo>planBuy(final Activity activity,String userId,String planId){
        purchagePlanPojoMutableLiveData= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.planBuy(userId, planId).enqueue(new Callback<PurchagePlanPojo>() {
                @Override
                public void onResponse(Call<PurchagePlanPojo> call, Response<PurchagePlanPojo> response) {
                    if (response.body()!=null){
                        purchagePlanPojoMutableLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<PurchagePlanPojo> call, Throwable t) {
                    Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            Toast.makeText(activity, "Network not connected.", Toast.LENGTH_SHORT).show();
        }
        return purchagePlanPojoMutableLiveData;
    }

    private MutableLiveData<UserMOmentsPojo> userMOmentsPojoMutableLiveData;
    public LiveData<UserMOmentsPojo>GetMomentsList(final Activity activity,String userId){
        userMOmentsPojoMutableLiveData= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.getMoments(userId).enqueue(new Callback<UserMOmentsPojo>() {
                @Override
                public void onResponse(Call<UserMOmentsPojo> call, Response<UserMOmentsPojo> response) {
                    if (response.body()!=null){
                        userMOmentsPojoMutableLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<UserMOmentsPojo> call, Throwable t) {
                    Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            Toast.makeText(activity, "Network not connected.", Toast.LENGTH_SHORT).show();
        }
        return userMOmentsPojoMutableLiveData;
    }


    private MutableLiveData<AddUserMomentsPojo> addUserMomentsPojoMutableLiveData;
    public LiveData<AddUserMomentsPojo>UpdateMomentsList(final Activity activity,RequestBody user_id,RequestBody title,RequestBody description,RequestBody location,MultipartBody.Part image){
        addUserMomentsPojoMutableLiveData= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.UploadMomentsData(user_id, title, description, location, image).enqueue(new Callback<AddUserMomentsPojo>() {
                @Override
                public void onResponse(Call<AddUserMomentsPojo> call, Response<AddUserMomentsPojo> response) {
                    if (response.body()!=null){
                        addUserMomentsPojoMutableLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<AddUserMomentsPojo> call, Throwable t) {
                    Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            Toast.makeText(activity, "Network not connected.", Toast.LENGTH_SHORT).show();
        }
        return addUserMomentsPojoMutableLiveData;
    }


    private MutableLiveData<SendCrushPojo> sendCrushPojoMutableLiveData;
    public LiveData<SendCrushPojo>sendCrushFriend(final Activity activity,String userId,String name,String phone){
        sendCrushPojoMutableLiveData= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.UserSendCrush(userId, name,phone).enqueue(new Callback<SendCrushPojo>() {
                @Override
                public void onResponse(Call<SendCrushPojo> call, Response<SendCrushPojo> response) {
                    if (response.body()!=null){
                        sendCrushPojoMutableLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<SendCrushPojo> call, Throwable t) {
                    Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            Toast.makeText(activity, "Network not connected.", Toast.LENGTH_SHORT).show();
        }
        return sendCrushPojoMutableLiveData;
    }


    private MutableLiveData<GetUserSendContactList> getUserSendContactListMutableLiveData;
    public LiveData<GetUserSendContactList>GetSentCrushesList(final Activity activity,String userId){
        getUserSendContactListMutableLiveData= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){

            apiInterface.getCrushesList(userId).enqueue(new Callback<GetUserSendContactList>() {
                @Override
                public void onResponse(Call<GetUserSendContactList> call, Response<GetUserSendContactList> response) {
                    if (response.body()!=null){
                        getUserSendContactListMutableLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<GetUserSendContactList> call, Throwable t) {
                    Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            Toast.makeText(activity, "Network not connected.", Toast.LENGTH_SHORT).show();
        }
        return getUserSendContactListMutableLiveData;
    }

}
