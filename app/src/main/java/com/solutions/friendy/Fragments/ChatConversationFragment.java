package com.solutions.friendy.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.solutions.friendy.Adapters.SectionsPagerAdapter;
import com.solutions.friendy.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatConversationFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title;
    private ImageView iv_back;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private EditText et_moments;
    private int i=1;


    public ChatConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat_conversation, container, false);

        findIds(view);


        return view;
    }

    private void findIds(View view) {

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        et_moments = view.findViewById(R.id.et_moments);
        et_moments.setOnClickListener(this);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Conversation");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.et_moments:
                NavDirections navDirections = ChatConversationFragmentDirections.actionChatConversationFragmentToMomentsFragment();
                Navigation.findNavController(view).navigate(navDirections);
                break;
        }
    }
}
