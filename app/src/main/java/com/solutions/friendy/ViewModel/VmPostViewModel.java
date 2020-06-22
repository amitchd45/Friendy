package com.solutions.friendy.ViewModel;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solutions.friendy.Models.CommentPostPojo;
import com.solutions.friendy.Models.GetListCommentPostPojo;
import com.solutions.friendy.Retrofit.APIClient;
import com.solutions.friendy.Retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VmPostViewModel extends ViewModel {

    private APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    //get Comment List

    private MutableLiveData<GetListCommentPostPojo> getListCommentPostPojoMutableLiveData;
    public LiveData<GetListCommentPostPojo> ListComment(final Activity activity, String postId) {
        getListCommentPostPojoMutableLiveData = new MutableLiveData<>();

//        CommonUtils.showProgress(activity);

        apiInterface.getListPostComment(postId).enqueue(new Callback<GetListCommentPostPojo>() {
            @Override
            public void onResponse(Call<GetListCommentPostPojo> call, Response<GetListCommentPostPojo> response) {

                if (response.body() != null) {
//                    CommonUtils.dismissProgress();
                    getListCommentPostPojoMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetListCommentPostPojo> call, Throwable t) {
//                CommonUtils.dismissProgress();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return getListCommentPostPojoMutableLiveData;
    }

    private MutableLiveData<CommentPostPojo> commentPostPojoMutableLiveData;
    public LiveData<CommentPostPojo> postComment(final Activity activity,String userId,String postId,String comment) {
        commentPostPojoMutableLiveData = new MutableLiveData<>();

//        CommonUtils.showProgress(activity);

        apiInterface.postComment(userId, postId,comment).enqueue(new Callback<CommentPostPojo>() {
            @Override
            public void onResponse(Call<CommentPostPojo> call, Response<CommentPostPojo> response) {

                if (response.body() != null) {
//                    CommonUtils.dismissProgress();
                    commentPostPojoMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommentPostPojo> call, Throwable t) {
//                CommonUtils.dismissProgress();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return commentPostPojoMutableLiveData;
    }

}
