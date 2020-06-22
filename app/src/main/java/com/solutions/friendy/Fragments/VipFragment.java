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

import com.solutions.friendy.Adapters.AdapterShowVip;
import com.solutions.friendy.Adapters.AlertSliderAdapter;
import com.solutions.friendy.Adapters.BottonFragmentPagerAdapter;
import com.solutions.friendy.Adapters.PlanAdapter;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.PurchagePlanPojo;
import com.solutions.friendy.Models.SubscribePlanPojo;
import com.solutions.friendy.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.solutions.friendy.ViewModel.VmUserSetting;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class VipFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn_get_VIP;
    //    private LayoutInflater layoutInflater;
    private int a = 1;
    private ImageView iv_close;
    private RecyclerView rv_show_vip_plan;
    private AdapterShowVip adapterShowVip;
    private Integer[] image = {R.drawable.girl_1, R.drawable.girl_3, R.drawable.girl_2};

    private AlertDialog alertDialog;
    private RecyclerView rv_choosePlan;
    private Button btn_continue;
    private ViewPager viewPager;
    private TabLayout indicator;
    private int[] images;
    private String[] titles, descriptions;
    private LayoutInflater inflater;
    private View alertLayout, alertLayout2;
    private BottonFragmentPagerAdapter bottonFragmentPagerAdapter;
    private RelativeLayout alert_subscription_banner1, alert_subscription_banner2, alert_subscription_banner3,
            rl_exclusive_vip,rl_unlimited_like,rl_superlike,rl_unlimited_rewaind,rl_glob;
    private TextView tv_offer_alert1, tv_offer_alert2, tv_offer_alert3,
            tv_period_alert1, tv_period_alert2, tv_period_alert3,
            tv_period_alert1_1, tv_period_alert2_1, tv_period_alert3_1,
            tv_price_alert1, tv_price_alert2, tv_price_alert3,
            tv_fullPrice_alert1, tv_fullPrice_alert2, tv_fullPrice_alert3;

    private RelativeLayout bottom_sheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private ViewPager viewPager1;
    private CircleIndicator sliderDotspanel;
    private VmUserSetting vmUserSetting;
    private List<SubscribePlanPojo.Detail>planList= new ArrayList<>();
    private String userId,planId;

    public VipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vip, container, false);

        vmUserSetting= ViewModelProviders.of(this).get(VmUserSetting.class);

        userId= App.getAppPreference().GetString("id");

        findIds(view);

        showPlan();

        return view;
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

    private void findIds(View view) {

        viewPager1 = view.findViewById(R.id.viewPager1);
        sliderDotspanel = view.findViewById(R.id.SliderDots1);

        inflater = getLayoutInflater();
        alertLayout = inflater.inflate(R.layout.alert_slider_layout, null);

        setSlider(alertLayout);
//        setSlider(alertLayout2);
//        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        rv_show_vip_plan=view.findViewById(R.id.rv_show_vip_plan);

        bottom_sheet = view.findViewById(R.id.bottom_sheet);
//
        btn_get_VIP = view.findViewById(R.id.btn_get_VIP);
        btn_get_VIP.setOnClickListener(this);

        rl_exclusive_vip = view.findViewById(R.id.rl_exclusive_vip);
        rl_exclusive_vip.setOnClickListener(this);

        rl_unlimited_like = view.findViewById(R.id.rl_unlimited_like);
        rl_unlimited_like.setOnClickListener(this);

        rl_superlike = view.findViewById(R.id.rl_superlike);
        rl_superlike.setOnClickListener(this);

        rl_unlimited_rewaind = view.findViewById(R.id.rl_unlimited_rewaind);
        rl_unlimited_rewaind.setOnClickListener(this);

        rl_glob = view.findViewById(R.id.rl_glob);
        rl_glob.setOnClickListener(this);
//
//        adapterShowVip= new AdapterShowVip(getActivity());
//        rv_show_vip_plan.setAdapter(adapterShowVip);
    }

//    private void bottomsheet() {
//
//        bottomSheetBehavior= BottomSheetBehavior.from(bottom_sheet);
//        if(bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        }else {
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        }
//
//    }


//    private void VipDialogOpen(final View view) {
//
//        final View confirmdailog = layoutInflater.inflate(R.layout.vip_plan, null);
//        final Dialog dailogbox = new Dialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
//        dailogbox.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//        dailogbox.setCancelable(false);
//        dailogbox.setContentView(confirmdailog);
//
//        LinearLayout ll_twelvemonth = confirmdailog.findViewById(R.id.ll_twelvemonth);
//        TextView tv_one = confirmdailog.findViewById(R.id.tv_one);
//        TextView tv_twelve = confirmdailog.findViewById(R.id.tv_twelve);
//        TextView tv_month_1 = confirmdailog.findViewById(R.id.tv_month_1);
//        TextView tv_money_mol = confirmdailog.findViewById(R.id.tv_money_mol);
//        TextView tv_money = confirmdailog.findViewById(R.id.tv_money);
//
//
//        LinearLayout ll_threemonth = confirmdailog.findViewById(R.id.ll_threemonth);
//        TextView tv_two = confirmdailog.findViewById(R.id.tv_two);
//        TextView tv_three = confirmdailog.findViewById(R.id.tv_three);
//        TextView tv_month_2 = confirmdailog.findViewById(R.id.tv_month_2);
//        TextView tv_money_mol_1 = confirmdailog.findViewById(R.id.tv_money_mol_1);
//        TextView tv_money_320 = confirmdailog.findViewById(R.id.tv_money_320);
//
//        LinearLayout ll_onemonth = confirmdailog.findViewById(R.id.ll_onemonth);
//        TextView tv_one_month = confirmdailog.findViewById(R.id.tv_one_month);
//        TextView tv_month_3 = confirmdailog.findViewById(R.id.tv_month_3);
//        TextView tv_money_mol_3 = confirmdailog.findViewById(R.id.tv_money_mol_3);
//
//        IndefinitePagerIndicator indefinitePagerIndicator=confirmdailog.findViewById(R.id.recyclerview_pager_indicator);
//
//        if (a == 1) {
//            ll_twelvemonth.setBackgroundResource(R.drawable.stroke_bg);
//            tv_one.setVisibility(View.VISIBLE);
//            tv_twelve.setTextColor(Color.parseColor("#fc5068"));
//            tv_month_1.setTextColor(Color.parseColor("#fc5068"));
//            tv_money_mol.setTextColor(Color.parseColor("#fc5068"));
//            tv_money.setTextColor(Color.parseColor("#fc5068"));
//
//        }
//
//        confirmdailog.findViewById(R.id.btn_Step_continue_with_pay).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view1) {
//                Toast toast = Toast.makeText(getActivity(), R.string.comming_soon, Toast.LENGTH_SHORT);
//                ViewGroup group = (ViewGroup) toast.getView();
//                TextView messageTextView = (TextView) group.getChildAt(0);
//                messageTextView.setTextSize(15);
//                toast.show();
//
//                dailogbox.dismiss();
//
//            }
//        });
//        confirmdailog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view1) {
//                dailogbox.dismiss();
//
//            }
//        });
//        confirmdailog.findViewById(R.id.ll_twelvemonth).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view1) {
//                a = 2;
//                ll_twelvemonth.setBackgroundResource(R.drawable.stroke_bg);
//                tv_one.setVisibility(View.VISIBLE);
//                tv_twelve.setTextColor(Color.parseColor("#fc5068"));
//                tv_month_1.setTextColor(Color.parseColor("#fc5068"));
//                tv_money_mol.setTextColor(Color.parseColor("#fc5068"));
//                tv_money.setTextColor(Color.parseColor("#fc5068"));
//
//                if (a == 2) {
//                    ll_threemonth.setBackgroundResource(R.drawable.strock_white);
//                    tv_two.setVisibility(View.GONE);
//                    tv_three.setTextColor(Color.parseColor("#000000"));
//                    tv_month_2.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol_1.setTextColor(Color.parseColor("#000000"));
//                    tv_money_320.setTextColor(Color.parseColor("#000000"));
//
//                    ll_onemonth.setBackgroundResource(R.drawable.strock_white);
//                    tv_one_month.setTextColor(Color.parseColor("#000000"));
//                    tv_month_3.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol_3.setTextColor(Color.parseColor("#000000"));
//                }
//
//            }
//        });
//
//        confirmdailog.findViewById(R.id.ll_threemonth).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                a = 4;
//
//                ll_threemonth.setBackgroundResource(R.drawable.stroke_bg);
//                tv_two.setVisibility(View.VISIBLE);
//                tv_three.setTextColor(Color.parseColor("#fc5068"));
//                tv_month_2.setTextColor(Color.parseColor("#fc5068"));
//                tv_money_mol_1.setTextColor(Color.parseColor("#fc5068"));
//                tv_money_320.setTextColor(Color.parseColor("#fc5068"));
//
//                if (a == 4) {
//                    ll_onemonth.setBackgroundResource(R.drawable.strock_white);
//                    tv_one_month.setTextColor(Color.parseColor("#000000"));
//                    tv_month_3.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol_3.setTextColor(Color.parseColor("#000000"));
//
//                    ll_twelvemonth.setBackgroundResource(R.drawable.strock_white);
//                    tv_one.setVisibility(View.GONE);
//                    tv_twelve.setTextColor(Color.parseColor("#000000"));
//                    tv_month_1.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol.setTextColor(Color.parseColor("#000000"));
//                    tv_money.setTextColor(Color.parseColor("#000000"));
//
//
//                }
//
//
//            }
//        });
//
//        confirmdailog.findViewById(R.id.ll_onemonth).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                a = 3;
//
//                ll_onemonth.setBackgroundResource(R.drawable.stroke_bg);
//                tv_one_month.setTextColor(Color.parseColor("#fc5068"));
//                tv_month_3.setTextColor(Color.parseColor("#fc5068"));
//                tv_money_mol_3.setTextColor(Color.parseColor("#fc5068"));
//
//                if (a == 3) {
//                    ll_twelvemonth.setBackgroundResource(R.drawable.strock_white);
//                    tv_one.setVisibility(View.GONE);
//                    tv_twelve.setTextColor(Color.parseColor("#000000"));
//                    tv_month_1.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol.setTextColor(Color.parseColor("#000000"));
//                    tv_money.setTextColor(Color.parseColor("#000000"));
//
//
//                    ll_threemonth.setBackgroundResource(R.drawable.strock_white);
//                    tv_two.setVisibility(View.GONE);
//                    tv_three.setTextColor(Color.parseColor("#000000"));
//                    tv_month_2.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol_1.setTextColor(Color.parseColor("#000000"));
//                    tv_money_320.setTextColor(Color.parseColor("#000000"));
//                }
//
//
//            }
//        });
//
//        dailogbox.show();
//
//        rv_show_vip_plan=confirmdailog.findViewById(R.id.rv_show_vip_plan);
//        adapterShowVip= new AdapterShowVip(getActivity(),image);
//        rv_show_vip_plan.setAdapter(adapterShowVip);
//
//        indefinitePagerIndicator.attachToRecyclerView(rv_show_vip_plan);
//
//
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_exclusive_vip:
//                bottomsheet();

                dialogFrag();
//                BottonSheetDialogOpenFragment bottonSheetDialogOpenFragment = new BottonSheetDialogOpenFragment();
//                bottonSheetDialogOpenFragment.show(getActivity().getSupportFragmentManager(), bottonSheetDialogOpenFragment.getTag());
                break;

                case R.id.rl_unlimited_like:
                    dialogFrag();

                break;

                case R.id.rl_superlike:
                    dialogFrag();

                break;

                case R.id.rl_unlimited_rewaind:
                    dialogFrag();

                break;
                case R.id.rl_glob:
                    dialogFrag();

                break;

            case R.id.iv_close:
                alertDialog.dismiss();
                break;
            case R.id.btn_continue:
                alertDialog.dismiss();
                purchagePlan(planId);
                break;

            case R.id.btn_get_VIP:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                registerWithIDs(alertLayout);
                if (alertLayout.getParent() != null)
                    ((ViewGroup) alertLayout.getParent()).removeView(alertLayout);
                alertDialogBuilder.setView(alertLayout);
                alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCancelable(true);


                PlanAdapter planAdapter= new PlanAdapter(getActivity(), planList, new PlanAdapter.Select() {
                    @Override
                    public void planClick(String id) {

                        planId=id;

                    }
                });
                rv_choosePlan.setAdapter(planAdapter);
                alertDialog.show();
                break;

            case R.id.alert_subscription_banner1:
                alert_subscription_banner1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_banner));
                alert_subscription_banner2.setBackgroundResource(0);
                alert_subscription_banner3.setBackgroundResource(0);

                tv_offer_alert1.setVisibility(View.VISIBLE);
                tv_offer_alert2.setVisibility(View.INVISIBLE);
//                tv_offer_alert3.setVisibility(View.INVISIBLE);

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
//                tv_offer_alert3.setVisibility(View.INVISIBLE);

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

//                tv_offer_alert3.setVisibility(View.VISIBLE);
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

    private void purchagePlan(String planId) {

        vmUserSetting.planBuy(getActivity(),userId,planId).observe(VipFragment.this, new Observer<PurchagePlanPojo>() {
            @Override
            public void onChanged(PurchagePlanPojo purchagePlanPojo) {
                if (purchagePlanPojo.getSuccess().equalsIgnoreCase("1")){
                    Toast.makeText(getActivity(), "You Purchased this plan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dialogFrag() {
        BottonSheetDialogOpenFragment bottonSheetDialogOpenFragment = new BottonSheetDialogOpenFragment();
        bottonSheetDialogOpenFragment.show(getActivity().getSupportFragmentManager(), bottonSheetDialogOpenFragment.getTag());

    }

    public void registerWithIDs(View v) {

        rv_choosePlan = v.findViewById(R.id.rv_choosePlan);
        btn_continue = v.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);

        iv_close = v.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
        alert_subscription_banner1 = v.findViewById(R.id.alert_subscription_banner1);
        alert_subscription_banner2 = v.findViewById(R.id.alert_subscription_banner2);
        alert_subscription_banner3 = v.findViewById(R.id.alert_subscription_banner3);

        //Banner  Offer TextViews
        tv_offer_alert1 = v.findViewById(R.id.tv_offer_alert1);
        tv_offer_alert2 = v.findViewById(R.id.tv_offer_alert2);
//        tv_offer_alert3 = v.findViewById(R.id.tv_offer_alert3);

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

    public void setSlider(View v) {
        viewPager = v.findViewById(R.id.slider_viewPager_alert);
        indicator = v.findViewById(R.id.slider_indicator_alert);
        images = new int[]{
                R.drawable.badge,
                R.drawable.like,
                R.drawable.super_like,
                R.drawable.rewind,
                R.drawable.global
        };
        titles = new String[]{"Exclusive Badge", "Unlimited Likes", "5 Free Super Likes A Day", "Unlimited Rewinds", "Swipe Around The Globe"};
        descriptions = new String[]{"You're 5x more likely to get a match",
                "Like as much as you like",
                "You're 5x more likely to get a match",
                "Go back and swipe again",
                "Location roaming to anywhere"};

        AlertSliderAdapter sliderAdapter = new AlertSliderAdapter(getActivity(), images, titles, descriptions);

        viewPager.setAdapter(sliderAdapter);
        indicator.setupWithViewPager(viewPager, true);


    }
}
