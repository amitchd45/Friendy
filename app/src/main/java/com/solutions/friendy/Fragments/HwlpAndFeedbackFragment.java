package com.solutions.friendy.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutions.friendy.Adapters.AdapterGuide;
import com.solutions.friendy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HwlpAndFeedbackFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_title;
    private ImageView iv_back;
    private RecyclerView rv_guide;
    private AdapterGuide adapterGuide;



    public HwlpAndFeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hwlp_and_feedback, container, false);

        findIds(view);
        setUp();

        return view;
    }

    private void setUp() {

        adapterGuide =new AdapterGuide(getActivity(), new AdapterGuide.Select() {
            @Override
            public void Choose(int position) {
                NavDirections navDirections =HwlpAndFeedbackFragmentDirections.actionHwlpAndFeedbackFragmentToHowToHelpFragment();
                Navigation.findNavController(view).navigate(navDirections);
            }
        });
        rv_guide.setAdapter(adapterGuide);

    }

    private void findIds(View view) {

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("Help & FeedBack");

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        rv_guide =view.findViewById(R.id.rv_guide);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }

    }
}
