package com.solutions.friendy.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cometchat.pro.constants.CometChatConstants;
import com.solutions.friendy.Activity.ChatListActivity;
import com.solutions.friendy.Adapters.FriendListAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.GetUserFriendListModelClass;
import com.solutions.friendy.R;
import com.solutions.friendy.ViewModel.SwipeItemsViewModelClass;

import java.util.ArrayList;
import java.util.List;

import constant.StringContract;
import screen.messagelist.CometChatMessageListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    private View view;
    private TextView tv_not_found;
    private SwipeItemsViewModelClass swipeItemsViewModelClass;
    private RecyclerView rv_friendList;
    private String user_id;
    private List<GetUserFriendListModelClass.Detail>listFriend = new ArrayList<>();


    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_matches, container, false);

        swipeItemsViewModelClass= ViewModelProviders.of(getActivity()).get(SwipeItemsViewModelClass.class);

        findIds(view);

        user_id= App.getAppPreference().GetString("id");

        getFriendListData();

        return view;
    }


    private void findIds(View view) {

        rv_friendList =view.findViewById(R.id.rv_friendList);
        tv_not_found =view.findViewById(R.id.tv_not_found);

    }

    private void getFriendListData() {

        swipeItemsViewModelClass.getUserFriendList(getActivity(),user_id).observe(getActivity(), new Observer<GetUserFriendListModelClass>() {
            @Override
            public void onChanged(GetUserFriendListModelClass getUserFriendListModelClass) {
                if (getUserFriendListModelClass.getSuccess().equalsIgnoreCase("1")){
                    Toast.makeText(getActivity(), getUserFriendListModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                    listFriend=getUserFriendListModelClass.getDetails();

                    FriendListAdapter friendListAdapter= new FriendListAdapter(getActivity(), listFriend, new FriendListAdapter.Select() {
                        @Override
                        public void Choose(int position,String name,String UID) {


                                Intent intent = new Intent(getActivity(), CometChatMessageListActivity.class);
                                intent.putExtra(StringContract.IntentStrings.UID,UID);
                                intent.putExtra(StringContract.IntentStrings.AVATAR, "");
                                intent.putExtra(StringContract.IntentStrings.STATUS,"");
                                intent.putExtra(StringContract.IntentStrings.NAME, name);
                                intent.putExtra(StringContract.IntentStrings.TYPE, CometChatConstants.RECEIVER_TYPE_USER);
                                startActivity(intent);

//                            Toast.makeText(getActivity(), ""+UID, Toast.LENGTH_SHORT).show();
//                            App.getSinlton().setUid(UID);
//                            startActivity(new Intent(getActivity(), ChatListActivity.class));

                        }
                    });

                    rv_friendList.setAdapter(friendListAdapter);
                }
                else if (getUserFriendListModelClass.getSuccess().equalsIgnoreCase("0")){
                    tv_not_found.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        getFriendListData();

    }
}
