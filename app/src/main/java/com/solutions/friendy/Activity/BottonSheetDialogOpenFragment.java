package com.solutions.friendy.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.CommentPostPojo;
import com.solutions.friendy.Models.GetListCommentPostPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VmPostViewModel;

import java.util.ArrayList;
import java.util.List;


public class BottonSheetDialogOpenFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private View view;
//    private AdapterComment adapterComment;
    private RecyclerView rv_comment_list;
    private EditText et_comment;
    private ImageView iv_send;
    private String strComment;
    private List<GetListCommentPostPojo.Detail> commentLists = new ArrayList<>();
    private VmPostViewModel vmPostViewModel;
    private ProgressBar progress_circular;

    public BottonSheetDialogOpenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_botton_sheet_dialog_open2, container, false);

        vmPostViewModel = ViewModelProviders.of(this).get(VmPostViewModel.class);

        init(view);
        setUp();
//        getComments();

        return view;
    }

//    private void getComments() {
//
//        vmPostViewModel.ListComment(getActivity(), App.getAppPreference().GetString(AppConstants.COMT_POST_ID)).observe(getActivity(), new Observer<GetListCommentPostPojo>() {
//            @Override
//            public void onChanged(GetListCommentPostPojo getListCommentPostPojo) {
//                if (getListCommentPostPojo.getSuccess().equalsIgnoreCase("1")) {
//                    Toast.makeText(getActivity(), getListCommentPostPojo.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    commentLists = getListCommentPostPojo.getDetails();
//
//                    adapterComment = new AdapterComment(getActivity(), commentLists, new AdapterComment.Select() {
//                        @Override
//                        public void deleteComment(final int position, String commentId) {
//                            vmPostViewModel.commentDelete(getActivity(), commentId).observe(BottonSheetDialogOpenFragment.this, new Observer<DeleteCommentsPojo>() {
//                                @Override
//                                public void onChanged(DeleteCommentsPojo deleteCommentsPojo) {
//                                    if (deleteCommentsPojo.getSuccess().equalsIgnoreCase("1")) {
//                                        Toast.makeText(getActivity(), deleteCommentsPojo.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                        commentLists.remove(position);
//                                        rv_comment_list.removeViewAt(position);
//                                        adapterComment.notifyItemRemoved(position);
//                                        adapterComment.notifyItemRangeChanged(position, commentLists.size());
//
//                                    } else {
//                                        Toast.makeText(getActivity(), deleteCommentsPojo.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//                            });
//
//                        }
//                    });
//                    rv_comment_list.setAdapter(adapterComment);
//                    progress_circular.setVisibility(View.GONE);
//
//                } else {
//                    progress_circular.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), getListCommentPostPojo.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void setUp() {
        iv_send.setOnClickListener(this);
    }

    private void init(View view) {
        progress_circular = view.findViewById(R.id.progress_circular);
        rv_comment_list = view.findViewById(R.id.rv_comment_list);
        et_comment = view.findViewById(R.id.et_comment);
        iv_send = view.findViewById(R.id.iv_send);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_send:
                validate();
                break;
        }
    }

    private void validate() {
        strComment = et_comment.getText().toString().trim();

        if (strComment.isEmpty()) {
            et_comment.setError("Enter your comment");
        } else {
            commentPost();
            et_comment.getText().clear();
        }
    }

    private void commentPost() {
        vmPostViewModel.postComment(getActivity(), App.getAppPreference().GetString("id"), App.getAppPreference().GetString(AppConstants.COMT_POST_ID), strComment).observe(BottonSheetDialogOpenFragment.this, new Observer<CommentPostPojo>() {
            @Override
            public void onChanged(CommentPostPojo commentPostPojo) {
                if (commentPostPojo.getSuccess().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), commentPostPojo.getMessage(), Toast.LENGTH_SHORT).show();
//                    App.getAppPreference().SaveString(AppConstants.POST_SEND_STATUS, "1");

//                    getComments();

                }
            }
        });

    }
}