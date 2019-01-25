package com.example.raihan.hobbies;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

public class manage_post_viewPager_adapter extends FragmentPagerAdapter {
    public manage_post_viewPager_adapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new manage_normal_post_fragment();
        } else {
            return new manage_sell_post_fragment();

        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
