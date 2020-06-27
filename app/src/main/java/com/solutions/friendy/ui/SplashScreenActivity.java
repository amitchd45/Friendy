package com.solutions.friendy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;

import com.solutions.friendy.App;
import com.solutions.friendy.Fragments.SplashFragmentDirections;
import com.solutions.friendy.R;
import com.solutions.friendy.Retrofit.AppConstants;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    if (App.getAppPreference().GetString(AppConstants.TOKEN).equalsIgnoreCase("1")) {

                    } else {
                        startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                        finish();
                    }

                } catch (InterruptedException e) {
                }
            }
        });
        thread.start();
    }
}