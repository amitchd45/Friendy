package com.solutions.friendy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.solutions.friendy.R;

public class ExplorActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_moments, rl_secret_crush;
    private TextView tv_title;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explor);

        init();
        setUp();
    }

    private void setUp() {
        rl_moments.setOnClickListener(this);
        rl_secret_crush.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    private void init() {
        rl_moments = findViewById(R.id.rl_moments);
        rl_secret_crush = findViewById(R.id.rl_secret_crush);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("Explore");
        iv_back = findViewById(R.id.iv_back);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_moments:
                startActivity(new Intent(ExplorActivity.this, UserMomentsActivity.class));
                break;

            case R.id.rl_secret_crush:
                startActivity(new Intent(ExplorActivity.this, SecretActivity.class));
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}