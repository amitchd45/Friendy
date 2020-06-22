package com.solutions.friendy.Fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.omninos.util_data.CommonUtils;
import com.solutions.friendy.App;
import com.solutions.friendy.Models.MatchTokenPojo;
import com.solutions.friendy.Models.ResendOtpPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.Retrofit.StringContract;
import com.solutions.friendy.ViewModel.VMRegisterUser;

import java.util.Locale;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtpFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "OtpFragment";

    private View view;
    private ImageView iv_back;
    private TextView tv_title, tv_password, tv_resend_code, tv_verification, tv_number_show;
    private OtpTextView otpTextView;
    private String backOtp, id, userphone, checkUserExist = App.getSinlton().getCheckUserExist();
    private VMRegisterUser vmRegisterUser;
    private LinearLayout ll_resend;
    private static final long START_TIME_IN_MILLIS = 30000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private LinearLayout otp_layout;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    public OtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_otp, container, false);


        vmRegisterUser = ViewModelProviders.of(getActivity()).get(VMRegisterUser.class);

        findIds(view);

        if (checkUserExist.equalsIgnoreCase("0")) {
            tv_password.setVisibility(View.VISIBLE);
        }

        userphone = App.getSinlton().getPhone();
        id = App.getSinlton().getId();
        backOtp = App.getSinlton().getOtp();

        Toast.makeText(getActivity(), "OTP : " + backOtp, Toast.LENGTH_LONG).show();


        otp();
        startTimer();

        return view;
    }

    private void matchToken() {

        vmRegisterUser.getmatchToken(getActivity(), id, backOtp).observe(getActivity(), new Observer<MatchTokenPojo>() {
            @Override
            public void onChanged(MatchTokenPojo matchToken) {
                if (matchToken.getSuccess().equalsIgnoreCase("1")) {

//                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);


                    NavDirections navDirections = OtpFragmentDirections.actionOtpFragmentToRegisterFragment();
                    Navigation.findNavController(view).navigate(navDirections);
                    Toast.makeText(getActivity(), "Verify Your otp", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getContext(), matchToken.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mTextViewCountDown.setVisibility(View.GONE);
                tv_resend_code.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void otp() {
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {

                if (backOtp.equals(otp)) {
                    if (checkUserExist.equalsIgnoreCase("0")) {
                        CommonUtils.showProgress(getActivity());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtils.dismissProgress();

                                App.getAppPreference().SaveString(AppConstants.TOKEN,"1");
                                NavDirections navDirections=OtpFragmentDirections.actionOtpFragmentToHomePageFragment();
                                Navigation.findNavController(view).navigate(navDirections);
                            }
                        }, 1000);


                    } else {
                        matchToken();
                    }

                } else {
                    Toast.makeText(getActivity(), "otp mismatch", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void findIds(View view) {
        tv_number_show = view.findViewById(R.id.tv_number_show);
        tv_number_show.setText(App.getSinlton().getShowUser_number());
        ll_resend = view.findViewById(R.id.ll_resend);

        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);

        otpTextView = view.findViewById(R.id.otp_view);

        tv_password = view.findViewById(R.id.tv_password);
        tv_password.setOnClickListener(this);

        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        tv_title.setText(R.string.verificationCode);

        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_resend_code = view.findViewById(R.id.tv_resend_code);
        tv_resend_code.setOnClickListener(this);

        tv_verification = view.findViewById(R.id.tv_text);
        tv_verification.setText(R.string.verificationCode);

//        btn_continue = view.findViewById(R.id.btn_continue);
//        btn_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;

            case R.id.tv_password:
                NavDirections navDirections = OtpFragmentDirections.actionOtpFragmentToPasswordFragment();
                Navigation.findNavController(view).navigate(navDirections);
                break;

            case R.id.tv_resend_code:

                resendOTP();

                break;
        }
    }

    private void resendOTP() {
        vmRegisterUser.resendOtp(getActivity(), id).observe(OtpFragment.this, new Observer<ResendOtpPojo>() {
            @Override
            public void onChanged(ResendOtpPojo resendOtpPojo) {
                if (resendOtpPojo.getSuccess().equalsIgnoreCase("1")) {
                    backOtp = (resendOtpPojo.getDetails());
                    Toast.makeText(getActivity(), resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), resendOtpPojo.getDetails(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
