package com.solutions.friendy.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.solutions.friendy.Fragments.ChatsFragment;
import com.solutions.friendy.Fragments.MatchesFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public SectionsPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                MatchesFragment matchesFragment = new MatchesFragment();
                return matchesFragment;

            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;




            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "Matches";
            case 1:
                return "Chats";

            default:
                return null;
        }

    }
}
