package com.solutions.friendy.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.solutions.friendy.Adapters.BottonFragmentPagerAdapter;
import com.solutions.friendy.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottonSheetDialogOpenFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private View view;
    private ImageView iv_close;
    private ViewPager viewPager1;
    private CircleIndicator sliderDotspanel;
    private BottonFragmentPagerAdapter bottonFragmentPagerAdapter;
    private Button btn_get_VIP;


    public BottonSheetDialogOpenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_botton_sheet_dialog_open, container, false);

        findids(view);
        setUpSlider();
        return view;
    }

    private void setUpSlider() {
        bottonFragmentPagerAdapter = new BottonFragmentPagerAdapter(getChildFragmentManager());

        viewPager1.setAdapter(bottonFragmentPagerAdapter);

        sliderDotspanel.setViewPager(viewPager1);
        bottonFragmentPagerAdapter.notifyDataSetChanged();
    }

    private void findids(View view) {
        btn_get_VIP = view.findViewById(R.id.btn_get_VIP);
        btn_get_VIP.setOnClickListener(this);

        iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);

        viewPager1 = view.findViewById(R.id.viewPager1);
        sliderDotspanel = view.findViewById(R.id.SliderDots1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;

            case R.id.btn_get_VIP:
                Toast.makeText(getActivity(), "Coming soon...", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }
}
