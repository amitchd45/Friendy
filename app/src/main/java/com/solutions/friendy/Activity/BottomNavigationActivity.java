package com.solutions.friendy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.solutions.friendy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {

    private MorphBottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        getFriendy();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_friendy:
                        getFriendy();
                        break;

                }
                return true;
            }
        });
    }

    private void loadData(int first_nav) {
        NavHostFragment finalHost = NavHostFragment.create(first_nav);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, finalHost)
                .setPrimaryNavigationFragment(finalHost) // this is the equivalent to app:defaultNavHost="true"
                .commit();
    }

    private void getFriendy() {

        loadData(R.navigation.nav_friendy);
    }
}
