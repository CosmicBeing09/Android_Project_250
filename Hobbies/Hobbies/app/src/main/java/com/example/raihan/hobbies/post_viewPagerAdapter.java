package com.example.raihan.hobbies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class post_viewPagerAdapter extends FragmentPagerAdapter {

    public post_viewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new normal_post_fragmant();
        } else {
            return new sell_post_fragment();

        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
