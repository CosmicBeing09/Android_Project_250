package com.example.raihan.hobbies;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class add_post_master extends AppCompatActivity {

    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    Fragment normalPost,salePost;
    private String[] pageTitle = {"Add Post", "Add Sell Post"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_master);

//        Intent intent = getIntent();
//        node = intent.getStringExtra("user");

//        normalPost = new normal_post_fragmant();
//        salePost = new sell_post_fragment();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("user",node);
//
//        normalPost.setArguments(bundle);
//        salePost.setArguments(bundle);



        viewPager = (ViewPager)findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < 2; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }

        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set viewpager adapter
        post_viewPagerAdapter pagerAdapter = new post_viewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
