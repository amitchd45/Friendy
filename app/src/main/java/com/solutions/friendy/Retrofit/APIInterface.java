package com.solutions.friendy.Retrofit;

import com.solutions.friendy.Models.AddUserMomentsPojo;
import com.solutions.friendy.Models.CheckSocialId;
import com.solutions.friendy.Models.CommentPostPojo;
import com.solutions.friendy.Models.GetIndustryListModel;
import com.solutions.friendy.Models.GetInterestListModel;
import com.solutions.friendy.Models.GetListCommentPostPojo;
import com.solutions.friendy.Models.GetPersonalityListModel;
import com.solutions.friendy.Models.GetProfileDataModel;
import com.solutions.friendy.Models.GetSlideUserProfileModelClass;
import com.solutions.friendy.Models.GetSwipeItemsModelClass;
import com.solutions.friendy.Models.GetSwipeStatusModelClass;
import com.solutions.friendy.Models.GetUpdateProfileModel;
import com.solutions.friendy.Models.GetUserFriendListModelClass;
import com.solutions.friendy.Models.GetUserSendContactList;
import com.solutions.friendy.Models.GetUserSettingModelClass;
import com.solutions.friendy.Models.LoginRegisterPojo;
import com.solutions.friendy.Models.LoginUserPojo;
import com.solutions.friendy.Models.MatchTokenPojo;
import com.solutions.friendy.Models.PurchagePlanPojo;
import com.solutions.friendy.Models.ResendOtpPojo;
import com.solutions.friendy.Models.SendContactInfoModelClass;
import com.solutions.friendy.Models.SendCrushPojo;
import com.solutions.friendy.Models.SentOtpPhone;
import com.solutions.friendy.Models.SocialLoginPojo;
import com.solutions.friendy.Models.SubscribePlanPojo;
import com.solutions.friendy.Models.UpdateDobModel;
import com.solutions.friendy.Models.UpdateNameModel;
import com.solutions.friendy.Models.UpdatePhonePojo;
import com.solutions.friendy.Models.UserMOmentsPojo;
import com.solutions.friendy.Models.UserSettingModelClass;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    @FormUrlEncoded
    @POST("sentOtpPhone")
    Call<SentOtpPhone> OtpPhone(@Field("phone") String phone,
                                @Field("reg_id") String reg_id,
                                @Field("device_type") String device_type,
                                @Field("latitude") String latitude,
                                @Field("longitude") String longitude);


    @FormUrlEncoded
    @POST("matchVerificationToken")
    Call<MatchTokenPojo> matchVerificationToken(@Field("id") String id,
                                                @Field("token") String token);

    @Multipart
    @POST("userRegiter")
    Call<LoginRegisterPojo> registerUser(@Part("name") RequestBody name,
                                         @Part("dob") RequestBody dob,
                                         @Part("gender") RequestBody gender,
                                         @Part("password") RequestBody password,
                                         @Part("id") RequestBody id,
                                         @Part("latitude") RequestBody latitude,
                                         @Part("longitude") RequestBody longitude,
                                         @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("resendVerificationToken")
    Call<ResendOtpPojo> resendOtp(@Field("id") String id);

    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginUserPojo> loginUser(@Field("id") String id,
                                  @Field("password") String password,
                                  @Field("latitude") String latitude,
                                  @Field("longitude") String longitude,
                                  @Field("reg_id") String reg_id);

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<Map> forgotPassword(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("updatePassword")
    Call<Map> updatePass(@Field("phone") String phone,
                         @Field("password") String password);


    @FormUrlEncoded
    @POST("getUsers")
    Call<GetSwipeItemsModelClass> getSwipeItems(@Field("user_id") String userId,
                                                @Field("latitude") String latitude,
                                                @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("userLikeReject")
    Call<GetSwipeStatusModelClass> getSwipeStatusUser(@Field("user_id") String user_id,
                                                      @Field("friend_id") String friend_id,
                                                      @Field("status") String status);


    @GET("getIndustry")
    Call<GetIndustryListModel> getIndustry();

    @FormUrlEncoded
    @POST("getPersonality")
    Call<GetPersonalityListModel> getPersonality(@Field("id") String id);

    @FormUrlEncoded
    @POST("getProfileInterests")
    Call<GetInterestListModel> getInterest(@Field("id") String id);

    //Todo Get user profile data

    @FormUrlEncoded
    @POST("getProfile")
    Call<GetProfileDataModel> getUserProfile(@Field("id") String id);


    //Todo Update user profile

    @Multipart
    @POST("updateUserProfileSection")
    Call<GetUpdateProfileModel> updateProfile(@Part MultipartBody.Part image,
                                              @Part MultipartBody.Part image1,
                                              @Part MultipartBody.Part image2,
                                              @Part MultipartBody.Part image3,
                                              @Part MultipartBody.Part image4,
                                              @Part MultipartBody.Part image5,
                                              @Part("about") RequestBody about,
                                              @Part("industry") RequestBody industry,
                                              @Part("company") RequestBody company,
                                              @Part("home_town") RequestBody home_town,
                                              @Part("my_personality") RequestBody my_personality,
                                              @Part("my_interests") RequestBody my_interests,
                                              @Part("my_note") RequestBody my_note,
                                              @Part("userId") RequestBody userId,
                                              @Part("address") RequestBody address);


    @FormUrlEncoded
    @POST("updateName")
    Call<UpdateNameModel> updateName(@Field("name") String name,
                                     @Field("userId") String userId);

    @FormUrlEncoded
    @POST("updateDOB")
    Call<UpdateDobModel> updateDob(@Field("dob") String dob,
                                   @Field("userId") String userId);

    @FormUrlEncoded
    @POST("getUserFriendList")
    Call<GetUserFriendListModelClass> getFriendList(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("slideUserProfile")
    Call<GetSlideUserProfileModelClass> getSlideUserProfile(@Field("id") String id);

    @FormUrlEncoded
    @POST("userCheckSocialId")
    Call<CheckSocialId> getCheckSocialId(@Field("social_id") String social_id,
                                         @Field("reg_id") String reg_id,
                                         @Field("device_type") String device_type);


    @FormUrlEncoded
    @POST("UserSocialLogin")
    Call<SocialLoginPojo> getSocialLogin(@Field("social_id") String social_id,
                                         @Field("reg_id") String reg_id,
                                         @Field("device_type") String device_type,
                                         @Field("username") String username,
                                         @Field("phone") String phone,
                                         @Field("image") String image,
                                         @Field("email") String email,
                                         @Field("loginType") String loginType);


    //Todo userSetting filter to show user

    @FormUrlEncoded
    @POST("userSettings")
    Call<UserSettingModelClass> getUserSetting(@Field("userId") String userId,
                                               @Field("automatic_distance") String automatic_distance,
                                               @Field("gender") String gender,
                                               @Field("age") String age,
                                               @Field("distance") String distance);


    @FormUrlEncoded
    @POST("getUserSettings")
    Call<GetUserSettingModelClass> getSettingData(@Field("userId") String userId);

    @Multipart
    @POST("addContactInfo")
    Call<SendContactInfoModelClass> getContactInfo(@Part("details") RequestBody details,
                                                   @Part("email") RequestBody email,
                                                   @Part MultipartBody.Part image,
                                                   @Part("contactInfo") RequestBody contactInfo);


    @FormUrlEncoded
    @POST("updatePhoneNumber")
    Call<UpdatePhonePojo> updatePhone(@Field("phone") String phone,
                                      @Field("userId") String userId);


    @FormUrlEncoded
    @POST("deleteAccount")
    Call<Map> deleteAccount(@Field("id") String id);

    @FormUrlEncoded
    @POST("planId")
    Call<PurchagePlanPojo> planBuy(@Field("userId") String userId,
                                   @Field("planId") String planId);

    @GET("subscribeplan")
    Call<SubscribePlanPojo> getSubscribePlan();

    @FormUrlEncoded
    @POST("getUserMovement")
    Call<UserMOmentsPojo> getMoments(@Field("userId") String userId);


    @Multipart
    @POST("addUserMovement")
    Call<AddUserMomentsPojo> UploadMomentsData(@Part("user_id") RequestBody user_id,
                                           @Part("title") RequestBody title,
                                           @Part("description") RequestBody description,
                                           @Part("location") RequestBody location,
                                           @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("userSendContact")
    Call<SendCrushPojo> UserSendCrush(@Field("userId") String userId,
                                      @Field("name") String name,
                                      @Field("phone") String phone);

    @FormUrlEncoded
    @POST("getUserSendContactList")
    Call<GetUserSendContactList> getCrushesList(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("getPostComment")
    Call<GetListCommentPostPojo> getListPostComment(@Field("postId") String postId);

    @FormUrlEncoded
    @POST("userCommentPost")
    Call<CommentPostPojo> postComment(@Field("userId") String userId,
                                      @Field("postId") String postId,
                                      @Field("comment") String comment);



}

