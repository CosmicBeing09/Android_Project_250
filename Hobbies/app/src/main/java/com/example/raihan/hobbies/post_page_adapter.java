package com.example.raihan.hobbies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class post_page_adapter extends FragmentPagerAdapter {

    int no_of_tabs;
    public post_page_adapter(FragmentManager fm,int no_of_tabs) {
        super(fm);
        this.no_of_tabs = no_of_tabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                normal_post_fragmant npf = new normal_post_fragmant();
                return npf;

            case 1:
                sell_post_fragment spf = new sell_post_fragment();
                return spf;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return no_of_tabs;
    }
}
