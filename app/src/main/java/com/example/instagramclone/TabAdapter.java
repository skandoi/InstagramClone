package com.example.instagramclone;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
//    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//    }

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }


    //    @NonNull

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabProfile();
            case 1:
                return new TabUsers();
            case 2:
                return new TabSharePicture();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Profile";
            case 1:
                return "Users";
            case 2:
                return "Share Picture";
            default:
                return null;
        }
    }
}
