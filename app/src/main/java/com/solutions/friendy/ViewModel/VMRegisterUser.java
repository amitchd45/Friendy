package com.solutions.friendy.ViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solutions.friendy.Models.CheckSocialId;
import com.solutions.friendy.Models.LoginRegisterPojo;
import com.solutions.friendy.Models.LoginUserPojo;
import com.solutions.friendy.Models.ResendOtpPojo;
import com.solutions.friendy.Models.SentOtpPhone;
import com.solutions.friendy.Models.MatchTokenPojo;
import com.solutions.friendy.Models.SocialLoginPojo;
import com.solutions.friendy.Retrofit.APIClient;
import com.solutions.friendy.Retrofit.APIInterface;
import com.omninos.util_data.CommonUtils;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class VMRegisterUser extends ViewModel {

    private MutableLiveData<SentOtpPhone> phoneOtpResult;
    private MutableLiveData<LoginRegisterPojo> registerDetails;
    private MutableLiveData<ResendOtpPojo> resendOtpPojo;
    private MutableLiveData<LoginUserPojo> loginWithPass;
    private MutableLiveData<Map> forgetPass;
    private MutableLiveData<Map> updatePassword;
    private MutableLiveData<CheckSocialId> checkSocialId;
    private MutableLiveData<SocialLoginPojo> socialLogin;

    private APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    //-----Social login----------

    public LiveData<SocialLoginPojo>getSocialLoginFacebook(final Activity activity,String social_id,String reg_id,String device_type, String username,String phone,String image,String email,String loginType){

        socialLogin=new MutableLiveData<>();
        apiInterface.getSocialLogin(social_id, reg_id, device_type, username, phone, image, email, loginType).enqueue(new Callback<SocialLoginPojo>() {
            @Override
            public void onResponse(Call<SocialLoginPojo> call, Response<SocialLoginPojo> response) {
                if (response.body()!=null){
                    socialLogin.postValue(response.body());
                }else {
                    Toast.makeText(activity, "Response error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SocialLoginPojo> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return socialLogin;
    }

    // -------------Check social Id-------------------

    public LiveData<CheckSocialId>getSocialData(final Activity activity,String social_id,String reg_id,String device_type){

        checkSocialId=new MutableLiveData<>();
        apiInterface.getCheckSocialId(social_id, reg_id, device_type).enqueue(new Callback<CheckSocialId>() {
            @Override
            public void onResponse(Call<CheckSocialId> call, Response<CheckSocialId> response) {
                if (response.body() != null) {
                    checkSocialId.postValue(response.body());
                }
                else {
                    Toast.makeText(activity, "response error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckSocialId> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return checkSocialId;
    }

    public LiveData<SentOtpPhone> getPhoneResult(final Activity activity, String phone, String reg_id, String device_type,String latitude,String longitude) {

        phoneOtpResult = new MutableLiveData<>();

        CommonUtils.showProgress(activity);
        apiInterface.OtpPhone(phone, reg_id, device_type,latitude,longitude).enqueue(new Callback<SentOtpPhone>() {
            @Override
            public void onResponse(Call<SentOtpPhone> call, Response<SentOtpPhone> response) {
                CommonUtils.dismissProgress();
                if (response.body() != null) {
                    phoneOtpResult.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SentOtpPhone> call, Throwable t) {

                CommonUtils.dismissProgress();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return phoneOtpResult;
    }

//    matchVerificationToken


    private MutableLiveData<MatchTokenPojo> matchTokenDetails;

    public LiveData<MatchTokenPojo> getmatchToken(final Activity activity, String id, String token) {

        matchTokenDetails = new MutableLiveData<>();
        CommonUtils.showProgress(activity);

        apiInterface.matchVerificationToken(id, token).enqueue(new Callback<MatchTokenPojo>() {
            @Override
            public void onResponse(Call<MatchTokenPojo> call, Response<MatchTokenPojo> response) {

                CommonUtils.dismissProgress();
                if (response.body() != null) {
                    matchTokenDetails.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MatchTokenPojo> call, Throwable t) {

                CommonUtils.dismissProgress();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return matchTokenDetails;
    }

    public LiveData<LoginRegisterPojo> registerResults(final Activity activity, RequestBody name, RequestBody dob, RequestBody gender, RequestBody password, RequestBody id,RequestBody latitude,RequestBody longitude, MultipartBody.Part image) {
        registerDetails = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            apiInterface.registerUser(name, dob, gender, password, id,latitude,longitude,image).enqueue(new Callback<LoginRegisterPojo>() {
                @Override
                public void onResponse(Call<LoginRegisterPojo> call, Response<LoginRegisterPojo> response) {

                    if (response.body() != null) {
                        registerDetails.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<LoginRegisterPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        return registerDetails;
    }


    public LiveData<ResendOtpPojo> resendOtp(final Activity activity, String id) {
        resendOtpPojo = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {

            CommonUtils.showProgress(activity);
            apiInterface.resendOtp(id).enqueue(new Callback<ResendOtpPojo>() {
                @Override
                public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {

                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        resendOtpPojo.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        return resendOtpPojo;
    }

    public LiveData<LoginUserPojo> loginUserPojo(final Activity activity, String id,String password,String latitude,String longitude,String reg_id) {
        loginWithPass = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {

            CommonUtils.showProgress(activity);
            apiInterface.loginUser(id,password,latitude,longitude,reg_id).enqueue(new Callback<LoginUserPojo>() {
                @Override
                public void onResponse(Call<LoginUserPojo> call, Response<LoginUserPojo> response) {

                    CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        loginWithPass.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<LoginUserPojo> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        return loginWithPass;
    }


    public LiveData<Map> forgetPassResults (final Activity  activity,String phone)
    {

        forgetPass=new MutableLiveData<>();
        if(CommonUtils.isNetworkConnected(activity))
        {
            apiInterface.forgotPassword(phone).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if(response.body()!=null)
                    {
                        forgetPass.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return forgetPass;
    }

    public LiveData<Map> updatepassResults (final Activity activity,String phone,String password)
    {

        updatePassword=new MutableLiveData<>();
        if(CommonUtils.isNetworkConnected(activity))
        {
            CommonUtils.showProgress(activity);
            apiInterface.updatePass(phone, password).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissProgress();
                    if(response.body()!=null)
                    {
                        updatePassword.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return updatePassword;

    }

}
