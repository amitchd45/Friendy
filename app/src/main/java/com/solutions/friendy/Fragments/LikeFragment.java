package com.solutions.friendy.Fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.solutions.friendy.Adapters.PlanAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.PurchagePlanPojo;
import com.solutions.friendy.Models.SubscribePlanPojo;
import com.solutions.friendy.R;
import com.google.android.material.tabs.TabLayout;
import com.solutions.friendy.ViewModel.VmUserSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button btn_850_get_inst, btn_getInstance_access;
    private ViewPager viewPager;
    private TabLayout indicator;
    private int[] images;
    private String[] titles, descriptions;
    private LayoutInflater inflater;
    private ImageView iv_close;
    private View alertLayout, alertLayout2;
    private AlertDialog alertDialog2;
    private RelativeLayout alert_subscription_banner1, alert_subscription_banner2, alert_subscription_banner3;
    private TextView tv_offer_alert1, tv_offer_alert2, tv_offer_alert3,
            tv_period_alert1, tv_period_alert2, tv_period_alert3,
            tv_period_alert1_1, tv_period_alert2_1, tv_period_alert3_1,
            tv_price_alert1, tv_price_alert2, tv_price_alert3,
            tv_fullPrice_alert1, tv_fullPrice_alert2, tv_fullPrice_alert3;
    private VmUserSetting vmUserSetting;
    private String userId,planId;
    private List<SubscribePlanPojo.Detail> planList= new ArrayList<>();
    private RecyclerView rv_choosePlan;


    public LikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_like, container, false);
        vmUserSetting= ViewModelProviders.of(this).get(VmUserSetting.class);

        userId= App.getAppPreference().GetString("id");

        findIds(view);

        showPlan();

        return view;
    }

    private void findIds(View view) {
        inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.alert_slider_layout_2, null);

        btn_850_get_inst = view.findViewById(R.id.btn_850_get_inst);
        btn_850_get_inst.setOnClickListener(this);




//        setSlider(alertLayout2);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_getInstance_access:
                alertDialog2.dismiss();
                purchagePlan(planId);
                break;

            case R.id.iv_close:
                alertDialog2.dismiss();
                break;
            case R.id.btn_850_get_inst:
                registerWithIDs(alertLayout);
                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(getActivity());
                if (alertLayout.getParent() != null)
                    ((ViewGroup) alertLayout.getParent()).removeView(alertLayout);
                alertDialogBuilder2.setView(alertLayout);
                alertDialog2 = alertDialogBuilder2.create();
                alertDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                PlanAdapter planAdapter= new PlanAdapter(getActivity(), planList, new PlanAdapter.Select() {
                    @Override
                    public void planClick(String id) {

                        planId=id;

                    }
                });
                rv_choosePlan.setAdapter(planAdapter);

                alertDialog2.show();
                break;

            case R.id.alert_subscription_banner1:
                alert_subscription_banner1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_banner));
                alert_subscription_banner2.setBackgroundResource(0);
                alert_subscription_banner3.setBackgroundResource(0);

                tv_offer_alert1.setVisibility(View.VISIBLE);
                tv_offer_alert2.setVisibility(View.INVISIBLE);
                tv_offer_alert3.setVisibility(View.INVISIBLE);

                tv_period_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_period_alert1_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_price_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_fullPrice_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));


                tv_period_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_period_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_period_alert2_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_period_alert3_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_price_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_price_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_fullPrice_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_fullPrice_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                break;

            case R.id.alert_subscription_banner2:
                alert_subscription_banner2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_banner));
                alert_subscription_banner1.setBackgroundResource(0);
                alert_subscription_banner3.setBackgroundResource(0);

                tv_offer_alert1.setVisibility(View.INVISIBLE);
                tv_offer_alert2.setVisibility(View.VISIBLE);
                tv_offer_alert3.setVisibility(View.INVISIBLE);

                tv_period_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_period_alert2_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_price_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_fullPrice_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));

                tv_period_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_period_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_period_alert1_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_period_alert3_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_price_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_price_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_fullPrice_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_fullPrice_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                break;

            case R.id.alert_subscription_banner3:
                alert_subscription_banner3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_banner));
                alert_subscription_banner1.setBackgroundResource(0);
                alert_subscription_banner2.setBackgroundResource(0);

                tv_offer_alert3.setVisibility(View.VISIBLE);
                tv_offer_alert2.setVisibility(View.INVISIBLE);
                tv_offer_alert1.setVisibility(View.INVISIBLE);


                tv_period_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_period_alert3_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_price_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));
                tv_fullPrice_alert3.setTextColor(ContextCompat.getColor(getActivity(), R.color.appColor));


                tv_period_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_period_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_period_alert1_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_period_alert2_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_price_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_price_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                tv_fullPrice_alert1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
                tv_fullPrice_alert2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

                break;
        }
    }

    public void registerWithIDs(View v) {
//        btn_continue = v.findViewById(R.id.btn_continue);
//        btn_continue.setOnClickListener(this);

        rv_choosePlan = v.findViewById(R.id.rv_choosePlan);
        btn_getInstance_access = v.findViewById(R.id.btn_getInstance_access);
        btn_getInstance_access.setOnClickListener(this);
        iv_close = v.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
//
//        iv_close = v.findViewById(R.id.iv_close);
//        iv_close.setOnClickListener(this);
        alert_subscription_banner1 = v.findViewById(R.id.alert_subscription_banner1);
        alert_subscription_banner2 = v.findViewById(R.id.alert_subscription_banner2);
        alert_subscription_banner3 = v.findViewById(R.id.alert_subscription_banner3);

        //Banner  Offer TextViews
        tv_offer_alert1 = v.findViewById(R.id.tv_offer_alert1);
        tv_offer_alert2 = v.findViewById(R.id.tv_offer_alert2);
        tv_offer_alert3 = v.findViewById(R.id.tv_offer_alert3);

        //Banner Period TextViews
        tv_period_alert1 = v.findViewById(R.id.tv_period_alert1);
        tv_period_alert2 = v.findViewById(R.id.tv_period_alert2);
        tv_period_alert3 = v.findViewById(R.id.tv_period_alert3);

        tv_period_alert1_1 = v.findViewById(R.id.tv_period_alert1_1);
        tv_period_alert2_1 = v.findViewById(R.id.tv_period_alert2_1);
        tv_period_alert3_1 = v.findViewById(R.id.tv_period_alert3_1);

        //Banner Price TextViews
        tv_price_alert1 = v.findViewById(R.id.tv_price_alert1);
        tv_price_alert2 = v.findViewById(R.id.tv_price_alert2);
        tv_price_alert3 = v.findViewById(R.id.tv_price_alert3);

        //Banner Full Price TextViews
        tv_fullPrice_alert1 = v.findViewById(R.id.tv_fullPrice_alert1);
        tv_fullPrice_alert2 = v.findViewById(R.id.tv_fullPrice_alert2);
        tv_fullPrice_alert3 = v.findViewById(R.id.tv_fullPrice_alert3);

        alert_subscription_banner1.setOnClickListener(this);
        alert_subscription_banner2.setOnClickListener(this);
        alert_subscription_banner3.setOnClickListener(this);
    }

    private void showPlan() {

        vmUserSetting.planShow(getActivity()).observe(getActivity(), new Observer<SubscribePlanPojo>() {
            @Override
            public void onChanged(SubscribePlanPojo subscribePlanPojo) {
                if (subscribePlanPojo.getSuccess().equalsIgnoreCase("1")){
//                    Toast.makeText(getActivity(), ""+subscribePlanPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    planList=subscribePlanPojo.getDetails();

                }else {
                    Toast.makeText(getActivity(), ""+subscribePlanPojo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void purchagePlan(String planId) {

        vmUserSetting.planBuy(getActivity(),userId,planId).observe(LikeFragment.this, new Observer<PurchagePlanPojo>() {
            @Override
            public void onChanged(PurchagePlanPojo purchagePlanPojo) {
                if (purchagePlanPojo.getSuccess().equalsIgnoreCase("1")){
                    Toast.makeText(getActivity(), "You Purchased this plan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
