package com.example.raihan.hobbies;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static String node;
    android.support.v4.app.Fragment fragment1 = null;
    android.support.v4.app.Fragment fragment = null;
    android.support.v4.app.Fragment fragment2 = null;


    private CircleImageView imageView;
    private normal_post_view_adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private DatabaseReference databaseReference,profileInfo,profileDatabase;
    private RelativeLayout content_main;
    private TextView name,location;
    TabLayout tabLayout;
    public static String user_location;
    public static profile_info pi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileInfo = FirebaseDatabase.getInstance().getReference("Users_info");

        Intent i = getIntent();
        node = i.getStringExtra("user").trim();



        content_main = (RelativeLayout) findViewById(R.id.content_main);


        final ArrayList<normal_post_object> arrayList = new ArrayList<>();



        recyclerView = (RecyclerView) findViewById(R.id.post_recycler);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new normal_post_view_adapter(arrayList,this);



        databaseReference = FirebaseDatabase.getInstance().getReference().child("global_post");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                normal_post_object ob = dataSnapshot.getValue(normal_post_object.class);
                arrayList.add(ob);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(adapter);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileDatabase = FirebaseDatabase.getInstance().getReference("Users_info");
//                profileDatabase.child(node).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        profile_info pi = dataSnapshot.getValue(profile_info.class);
//                        user_location = pi.getAddress().trim();
//                        Toast.makeText(MainActivity.this,user_location,Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

                user_location = pi.getAddress().trim();


                Intent intent = new Intent(MainActivity.this,locate_user.class);
                startActivity(intent);
                //intent.putExtra("location",user_location);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        imageView = (CircleImageView) headerView.findViewById(R.id.imageView);
        name = (TextView) headerView.findViewById(R.id.header_name);
        location = (TextView) headerView.findViewById(R.id.header_location);


        profileInfo.child(node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pi = dataSnapshot.getValue(profile_info.class);
                Picasso.get().load(pi.getRegister_ImageUri()).fit().into(imageView);
                name.setText(pi.getRegister_name());
                location.setText(pi.getAddress());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment1 = new user_profile();
                if(fragment1!=null)
                {

                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.screenArea,fragment1).addToBackStack("Tag");
                    Bundle bundle = new Bundle();
                    bundle.putString("user",node);
                    fragment1.setArguments(bundle);
                   // content_main.setVisibility(View.INVISIBLE);
                    ft.commit();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                }

            }
        });


//        profile_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,user_profile.class);
//                startActivity(intent);
//            }
//        });







    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.



        int id = item.getItemId();

       if (id == R.id.nav_gallery) {

//           fragment = new sell_post_fragment();
//           Bundle bundle = new Bundle();
//           bundle.putString("user",node);
//           fragment.setArguments(bundle);

           Intent intent = new Intent(MainActivity.this,add_post_master.class);
           startActivity(intent);
//           intent.putExtra("user",node);


        } else if (id == R.id.nav_slideshow) {

            //fragment2 = new rescue_call_fragment();


        } else if (id == R.id.nav_manage) {

           fragment = new notification_preview();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

           Intent intent = new Intent(MainActivity.this,message_view.class);
           startActivity(intent);
        }
//        else if(id == R.id.imageView)
//        {
//            fragment1 = new user_profile();
//        }
//        if(fragment1!=null)
//        {
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.replace(R.id.screenArea,fragment1);
//            ft.commit();
//        }

        if(fragment!=null)
        {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screenArea,fragment).addToBackStack("Tag");
            ft.commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }
//        if(fragment2!=null)
//        {
//            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//            android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.replace(R.id.screenArea,fragment2).addToBackStack("tag");
//            ft.commit();
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            drawer.closeDrawer(GravityCompat.START);
//        }

        return true;
    }


}
