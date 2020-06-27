package com.solutions.friendy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.omninos.util_data.CommonUtils;
import com.solutions.friendy.App;
import com.solutions.friendy.Fragments.OtpFragment;
import com.solutions.friendy.Fragments.OtpFragmentDirections;
import com.solutions.friendy.Models.MatchTokenPojo;
import com.solutions.friendy.Models.ResendOtpPojo;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;
import com.solutions.friendy.ViewModel.VMRegisterUser;

import java.util.Locale;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {
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
    private Activity activity=OtpActivity.this;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        findIds();

        if (checkUserExist.equalsIgnoreCase("0")) {
            tv_password.setVisibility(View.VISIBLE);
        }
        userphone = App.getSinlton().getPhone();
        id = App.getSinlton().getId();
        backOtp = App.getSinlton().getOtp();
        Toast.makeText(activity, "OTP : " + backOtp, Toast.LENGTH_LONG).show();
        otp();
        startTimer();
    }

    private void matchToken() {

        vmRegisterUser.getmatchToken(activity, id, backOtp).observe(OtpActivity.this, new Observer<MatchTokenPojo>() {
            @Override
            public void onChanged(MatchTokenPojo matchToken) {
                if (matchToken.getSuccess().equalsIgnoreCase("1")) {

//                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    startActivity(new Intent(OtpActivity.this,RegisterActivity.class));
                    Toast.makeText(activity, "Verify Your otp", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(activity, matchToken.getMessage(), Toast.LENGTH_SHORT).show();
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
                        CommonUtils.showProgress(activity);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtils.dismissProgress();
                                App.getAppPreference().SaveString(AppConstants.TOKEN,"1");
                                startActivity(new Intent(OtpActivity.this,HomeActivity.class));
                            }
                        }, 1000);


                    } else {
                        matchToken();
                    }

                } else {
                    Toast.makeText(activity, "otp mismatch", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void findIds() {
        tv_number_show = findViewById(R.id.tv_number_show);
        tv_number_show.setText(App.getSinlton().getShowUser_number());
        ll_resend = findViewById(R.id.ll_resend);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        otpTextView = findViewById(R.id.otp_view);

        tv_password = findViewById(R.id.tv_password);
        tv_password.setOnClickListener(this);

        tv_title = findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        tv_title.setText(R.string.verificationCode);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_resend_code = findViewById(R.id.tv_resend_code);
        tv_resend_code.setOnClickListener(this);

        tv_verification = findViewById(R.id.tv_text);
        tv_verification.setText(R.string.verificationCode);

//        btn_continue = findViewById(R.id.btn_continue);
//        btn_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                activity.onBackPressed();
                break;

            case R.id.tv_password:
                startActivity(new Intent(OtpActivity.this,PasswordActivity.class));
                break;

            case R.id.tv_resend_code:

                resendOTP();

                break;
        }
    }

    private void resendOTP() {
        vmRegisterUser.resendOtp(activity, id).observe(OtpActivity.this, new Observer<ResendOtpPojo>() {
            @Override
            public void onChanged(ResendOtpPojo resendOtpPojo) {
                if (resendOtpPojo.getSuccess().equalsIgnoreCase("1")) {
                    backOtp = (resendOtpPojo.getDetails());
                    Toast.makeText(activity, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity, resendOtpPojo.getDetails(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}