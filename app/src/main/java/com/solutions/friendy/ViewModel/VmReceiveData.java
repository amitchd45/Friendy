package com.solutions.friendy.ViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solutions.friendy.Models.GetIndustryListModel;
import com.solutions.friendy.Models.GetInterestListModel;
import com.solutions.friendy.Models.GetPersonalityListModel;
import com.solutions.friendy.Models.GetProfileDataModel;
import com.solutions.friendy.Models.GetUpdateProfileModel;
import com.solutions.friendy.Models.UpdateDobModel;
import com.solutions.friendy.Models.UpdateNameModel;
import com.solutions.friendy.Retrofit.APIClient;
import com.solutions.friendy.Retrofit.APIInterface;
import com.omninos.util_data.CommonUtils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VmReceiveData extends ViewModel {

    private APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    private MutableLiveData<GetIndustryListModel>industryList;
    private MutableLiveData<GetPersonalityListModel>personalityList;
    private MutableLiveData<GetInterestListModel>interestList;
    private MutableLiveData<GetUpdateProfileModel>update_profile;
    private MutableLiveData<GetProfileDataModel>profile;
    private MutableLiveData<UpdateNameModel>update_name;
    private MutableLiveData<UpdateDobModel>update_dob;


    //Todo Update name

    public LiveData<UpdateNameModel>get_Update_name(final Activity activity,String name,String userId){

        update_name= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){
            apiInterface.updateName(name, userId).enqueue(new Callback<UpdateNameModel>() {
                @Override
                public void onResponse(Call<UpdateNameModel> call, Response<UpdateNameModel> response) {
                    if (response.body() != null) {
                        update_name.postValue(response.body());
                    }
                    else {
                        Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateNameModel> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(activity, "Please check internet", Toast.LENGTH_SHORT).show();
        }

        return update_name;
    }

    //Todo Update DOB
    public LiveData<UpdateDobModel>get_Update_dob(final Activity activity, String dob, String userId){

        update_dob= new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){
            apiInterface.updateDob(dob, userId).enqueue(new Callback<UpdateDobModel>() {
                @Override
                public void onResponse(Call<UpdateDobModel> call, Response<UpdateDobModel> response) {
                    if (response.body() != null) {
                        update_dob.postValue(response.body());
                    }
                    else {
                        Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateDobModel> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(activity, "Please check internet", Toast.LENGTH_SHORT).show();
        }

        return update_dob;
    }


    //Todo Get User profile data

    public LiveData<GetProfileDataModel>get_profile(final Activity activity,String id){

        profile=new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)){
            apiInterface.getUserProfile(id).enqueue(new Callback<GetProfileDataModel>() {
                @Override
                public void onResponse(Call<GetProfileDataModel> call, Response<GetProfileDataModel> response) {
                    if (response.body() != null) {
                        profile.postValue(response.body());
                    }
                    else {
                        Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetProfileDataModel> call, Throwable t) {

                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }else {
            Toast.makeText(activity, "Internet is not connected please check internet.", Toast.LENGTH_SHORT).show();
        }

        return profile;
    }

    //Todo Update User Profile

    public LiveData<GetUpdateProfileModel>get_updateProfile(final Activity activity, MultipartBody.Part image, MultipartBody.Part image1, MultipartBody.Part image2, MultipartBody.Part image3, MultipartBody.Part image4, MultipartBody.Part image5, RequestBody about,RequestBody industry,RequestBody company,RequestBody home_town,RequestBody my_personality,RequestBody my_interests,RequestBody my_note,RequestBody userId,RequestBody address){

        update_profile=new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)){
            CommonUtils.showProgress(activity);
            apiInterface.updateProfile(image, image1, image2, image3, image4, image5, about, industry, company, home_town, my_personality, my_interests, my_note, userId,address).enqueue(new Callback<GetUpdateProfileModel>() {
                @Override
                public void onResponse(Call<GetUpdateProfileModel> call, Response<GetUpdateProfileModel> response) {
                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        update_profile.postValue(response.body());
                    }
                    else {
                        Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetUpdateProfileModel> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }else {
            Toast.makeText(activity, "Please check Internet", Toast.LENGTH_SHORT).show();
        }
        return update_profile;
    }

    //Todo  Get Industry List

    public LiveData<GetIndustryListModel>get_industrylist(final Activity activity){

        industryList=new MutableLiveData<>();
        CommonUtils.showProgress(activity);

        apiInterface.getIndustry().enqueue(new Callback<GetIndustryListModel>() {
            @Override
            public void onResponse(Call<GetIndustryListModel> call, Response<GetIndustryListModel> response) {
                CommonUtils.dismissProgress();
                if (response.body() != null) {
                    industryList.postValue(response.body());
                }
                else {
                    Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetIndustryListModel> call, Throwable t) {
                CommonUtils.dismissProgress();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return industryList;
    }

    //Todo get Personality List
    public LiveData<GetPersonalityListModel>get_personalityList(final Activity activity,String id){

        personalityList=new MutableLiveData<>();
//        CommonUtils.showProgress(activity);

        apiInterface.getPersonality(id).enqueue(new Callback<GetPersonalityListModel>() {
            @Override
            public void onResponse(Call<GetPersonalityListModel> call, Response<GetPersonalityListModel> response) {
//                CommonUtils.dismissProgress();
                if (response.body() != null) {
                    personalityList.postValue(response.body());
                }
                else {
                    Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPersonalityListModel> call, Throwable t) {
                CommonUtils.dismissProgress();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return personalityList;
    }

    // Todo get Interest List

    public LiveData<GetInterestListModel>get_interestList(final Activity activity,String id){

        interestList=new MutableLiveData<>();

        apiInterface.getInterest(id).enqueue(new Callback<GetInterestListModel>() {
            @Override
            public void onResponse(Call<GetInterestListModel> call, Response<GetInterestListModel> response) {
                if (response.body() != null) {
                    interestList.postValue(response.body());
                }
                else {
                    Toast.makeText(activity, "response error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetInterestListModel> call, Throwable t) {
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return interestList;
    }

}
