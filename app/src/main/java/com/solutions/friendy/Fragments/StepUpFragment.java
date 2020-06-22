package com.solutions.friendy.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solutions.friendy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepUpFragment extends Fragment {

    private View view;
    private Button btn_step_up_now;
    private LayoutInflater layoutInflater;
    private int a,b=0;


    public StepUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_step_up, container, false);
        find(view);
        return view;
    }

    private void find(View view) {
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        btn_step_up_now=view.findViewById(R.id.btn_step_up_now);
        btn_step_up_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step_up_Dialog(v);
            }
        });
    }

    private void step_up_Dialog(final View view) {

        final View confirmdailog = layoutInflater.inflate(R.layout.get_step_up_get_now, null);
        final Dialog dailogbox = new Dialog(getContext(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        dailogbox.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        dailogbox.setCancelable(false);
        dailogbox.setContentView(confirmdailog);

        LinearLayout ll_twelvemonth1 = confirmdailog.findViewById(R.id.ll_twelvemonth1);
        TextView tv_one1 = confirmdailog.findViewById(R.id.tv_one1);
        TextView tv_twelve1 = confirmdailog.findViewById(R.id.tv_twelve1);
        TextView tv_month_11 = confirmdailog.findViewById(R.id.tv_month_11);
        TextView tv_money1 = confirmdailog.findViewById(R.id.tv_money1);


        LinearLayout ll_threemonth2 = confirmdailog.findViewById(R.id.ll_threemonth2);
        TextView tv_two2 = confirmdailog.findViewById(R.id.tv_two2);
        TextView tv_three2 = confirmdailog.findViewById(R.id.tv_three2);
        TextView tv_month_22 = confirmdailog.findViewById(R.id.tv_month_22);
//        TextView tv_money_mol_12 = confirmdailog.findViewById(R.id.tv_money_mol_12);
        TextView tv_money_3202 = confirmdailog.findViewById(R.id.tv_money_3202);

        LinearLayout ll_onemonth3 = confirmdailog.findViewById(R.id.ll_onemonth3);
        TextView tv_one_month3 = confirmdailog.findViewById(R.id.tv_one_month3);
        TextView tv_month_33 = confirmdailog.findViewById(R.id.tv_month_33);
        TextView tv_money_mol_33 = confirmdailog.findViewById(R.id.tv_money_mol_33);


        if (b == 0) {
            ll_twelvemonth1.setBackgroundResource(R.drawable.stroke_bg);
            tv_one1.setVisibility(View.VISIBLE);
            tv_twelve1.setTextColor(Color.parseColor("#fc5068"));
            tv_month_11.setTextColor(Color.parseColor("#fc5068"));
//            tv_money_mol.setTextColor(Color.parseColor("#fc5068"));
            tv_money1.setTextColor(Color.parseColor("#fc5068"));

        }

        confirmdailog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                dailogbox.dismiss();

            }
        });
        confirmdailog.findViewById(R.id.btn_Step_continue_with_pay1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Toast toast = Toast.makeText(getActivity(), R.string.comming_soon, Toast.LENGTH_SHORT);
                ViewGroup group = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(15);
                toast.show();

                dailogbox.dismiss();

            }
        });
        confirmdailog.findViewById(R.id.ll_twelvemonth1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                a = 2;
                ll_twelvemonth1.setBackgroundResource(R.drawable.stroke_bg);
                tv_one1.setVisibility(View.VISIBLE);
                tv_twelve1.setTextColor(Color.parseColor("#fc5068"));
                tv_month_11.setTextColor(Color.parseColor("#fc5068"));
//                tv_money_mol.setTextColor(Color.parseColor("#fc5068"));
                tv_money1.setTextColor(Color.parseColor("#fc5068"));

                if (a == 2) {
                    ll_threemonth2.setBackgroundResource(R.drawable.strock_white);
                    tv_two2.setVisibility(View.GONE);
                    tv_three2.setTextColor(Color.parseColor("#000000"));
                    tv_month_22.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol_12.setTextColor(Color.parseColor("#000000"));
                    tv_money_3202.setTextColor(Color.parseColor("#000000"));

                    ll_onemonth3.setBackgroundResource(R.drawable.strock_white);
                    tv_one_month3.setTextColor(Color.parseColor("#000000"));
                    tv_month_33.setTextColor(Color.parseColor("#000000"));
                    tv_money_mol_33.setTextColor(Color.parseColor("#000000"));
                }

            }
        });

        confirmdailog.findViewById(R.id.ll_threemonth2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = 4;

                ll_threemonth2.setBackgroundResource(R.drawable.stroke_bg);
                tv_two2.setVisibility(View.VISIBLE);
                tv_three2.setTextColor(Color.parseColor("#fc5068"));
                tv_month_22.setTextColor(Color.parseColor("#fc5068"));
//                tv_money_mol_12.setTextColor(Color.parseColor("#fc5068"));
                tv_money_3202.setTextColor(Color.parseColor("#fc5068"));

                if (a == 4) {
                    ll_onemonth3.setBackgroundResource(R.drawable.strock_white);
                    tv_one_month3.setTextColor(Color.parseColor("#000000"));
                    tv_month_33.setTextColor(Color.parseColor("#000000"));
                    tv_money_mol_33.setTextColor(Color.parseColor("#000000"));

                    ll_twelvemonth1.setBackgroundResource(R.drawable.strock_white);
                    tv_one1.setVisibility(View.GONE);
                    tv_twelve1.setTextColor(Color.parseColor("#000000"));
                    tv_month_11.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol.setTextColor(Color.parseColor("#000000"));
                    tv_money1.setTextColor(Color.parseColor("#000000"));


                }


            }
        });

        confirmdailog.findViewById(R.id.ll_onemonth3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = 3;

                ll_onemonth3.setBackgroundResource(R.drawable.stroke_bg);
                tv_one_month3.setTextColor(Color.parseColor("#fc5068"));
                tv_month_33.setTextColor(Color.parseColor("#fc5068"));
                tv_money_mol_33.setTextColor(Color.parseColor("#fc5068"));

                if (a == 3) {
                    ll_twelvemonth1.setBackgroundResource(R.drawable.strock_white);
                    tv_one1.setVisibility(View.GONE);
                    tv_twelve1.setTextColor(Color.parseColor("#000000"));
                    tv_month_11.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol.setTextColor(Color.parseColor("#000000"));
                    tv_money1.setTextColor(Color.parseColor("#000000"));


                    ll_threemonth2.setBackgroundResource(R.drawable.strock_white);
                    tv_two2.setVisibility(View.GONE);
                    tv_three2.setTextColor(Color.parseColor("#000000"));
                    tv_month_22.setTextColor(Color.parseColor("#000000"));
//                    tv_money_mol_12.setTextColor(Color.parseColor("#000000"));
                    tv_money_3202.setTextColor(Color.parseColor("#000000"));
                }


            }
        });


        dailogbox.show();

    }

}
