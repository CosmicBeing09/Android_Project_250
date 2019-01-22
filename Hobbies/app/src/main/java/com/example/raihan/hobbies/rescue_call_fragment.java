package com.example.raihan.hobbies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



public class rescue_call_fragment extends Fragment implements normal_post_fragmant.OnFragmentInteractionListener,sell_post_fragment.OnFragmentInteractionListener {

    EditText users_location,radius;
    Button see_user;

    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rescue_fragmant,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = (TabLayout)view.findViewById(R.id.post_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Normal Post"));
        tabLayout.addTab(tabLayout.newTab().setText("Sell Post"));

        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.post_page_viewer);
        final post_page_adapter ppa = new post_page_adapter(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(ppa);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
//
//        users_location = (EditText) view.findViewById(R.id.rescue_location);
//        see_user = (Button) view.findViewById(R.id.rescue_button);
//        radius = (EditText) view.findViewById(R.id.radius);
//
//        see_user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               String location = users_location.getText().toString();
//               String Radius = radius.getText().toString();
//                Intent intent = new Intent(getActivity(),locate_user.class);
//                intent.putExtra("user_location",location);
//                intent.putExtra("radius",Radius);
//                startActivity(intent);
//
//            }
//        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
