package com.solutions.friendy.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.solutions.friendy.BottomSheet.ExclusiveBadgeFragment;
import com.solutions.friendy.BottomSheet.SuperLikeFragment;
import com.solutions.friendy.BottomSheet.SwipeAroundFragment;
import com.solutions.friendy.BottomSheet.UnlimitedLikeFragment;
import com.solutions.friendy.BottomSheet.UnlimitedRewindsFragment;


public class BottonFragmentPagerAdapter extends FragmentPagerAdapter {
    public BottonFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public BottonFragmentPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ExclusiveBadgeFragment exclusiveBadgeFragment = new ExclusiveBadgeFragment();
                return exclusiveBadgeFragment;

            case 1:
                UnlimitedLikeFragment unlimitedLikeFragment = new UnlimitedLikeFragment();
                return unlimitedLikeFragment;

            case 2:
                SuperLikeFragment superLikeFragment = new SuperLikeFragment();
                return superLikeFragment;

            case 3:
                UnlimitedRewindsFragment unlimitedRewindsFragment = new UnlimitedRewindsFragment();
                return unlimitedRewindsFragment;

            case 4:
                SwipeAroundFragment swipeAroundFragment = new SwipeAroundFragment();
                return swipeAroundFragment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//
//            case 0:
//                return "Chats";
//            case 1:
//                return "Matches";
//
//            default:
//                return null;
//        }
//
//    }
}
