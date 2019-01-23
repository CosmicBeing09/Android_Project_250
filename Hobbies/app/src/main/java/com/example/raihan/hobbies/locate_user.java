package com.example.raihan.hobbies;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.raihan.hobbies.MainActivity.node;

public class locate_user extends FragmentActivity implements OnMapReadyCallback {

    public String user_location,search_location,radius;
    private EditText searchRadius;
    public static final int REQUEST_LOCATION_PERMISSION = 1;
    DatabaseReference profileDatabase;
    public Toolbar toolbar;
    String location;
    private GoogleMap mMap;
    ArrayList<global_profile_info> user_arrayList = new ArrayList<>();
    DatabaseReference mDatabase;
    Button map_button;
    List<Address> distances = new ArrayList<>();
    Address hostAddress;
    public static String fragment_petType = null;
    Fragment postPreviewFragment;


    private ImageButton imageButton;
    private EditText search;
    private ToggleButton toggleButton;
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_user);

        toggleButton = (ToggleButton) findViewById(R.id.preview_toggle);
        search = (EditText) findViewById(R.id.search_pet);
        imageButton = (ImageButton) findViewById(R.id.search_imageButton);
        searchRadius = (EditText)findViewById(R.id.search_radius);

//        map_button = (Button) findViewById(R.id.map_button);

        mDatabase = FirebaseDatabase.getInstance().getReference("global_sale_post");
        profileDatabase = FirebaseDatabase.getInstance().getReference("Users_info");
        radius = searchRadius.getText().toString().trim();

        profileDatabase.child(node).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                profile_info pi = dataSnapshot.getValue(profile_info.class);
                user_location = pi.getAddress().trim();
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

//        Intent intent = getIntent();
//        user_location = intent.getStringExtra("user_location").toString().trim();
//        radius = intent.getStringExtra("radius").toString().trim();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final String[] searchType = new String[1];

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchType[0] = search.getText().toString().trim();
                fragment_petType = searchType[0];

                if(radius==null)
                {
                    radius = "20";
                }


                mDatabase.child(searchType[0]).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        sell_post_object cpo = dataSnapshot.getValue(sell_post_object.class);
                        Geocoder geocoder = new Geocoder(locate_user.this);
                        List<Address> addressList = null;
                        MarkerOptions markerOptions = new MarkerOptions();
                        assert cpo != null;
                        search_location = cpo.getLocation();

                        Toast.makeText(locate_user.this, searchType[0].toString().trim(), Toast.LENGTH_SHORT).show();

                        try {
                            addressList = geocoder.getFromLocationName(search_location, 6);
                            if (addressList != null) {
                                for (int i = 0; i < addressList.size(); i++) {
                                    Address userAddress = addressList.get(i);

                                    LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                    distances.add(addressList.get(0));
                                    float results[] = new float[100];
                                    Location.distanceBetween(userAddress.getLatitude(), userAddress.getLongitude(), hostAddress.getLatitude(), hostAddress.getLongitude(), results);

                                    if (results[0] / 1000 <= Float.valueOf(radius)) {

                                        mapInfo map_info = new mapInfo(locate_user.this);
                                        mMap.setInfoWindowAdapter(map_info);

                                        markerOptions.position(latLng);
                                        markerOptions.title(search_location);
                                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                                        Marker m = mMap.addMarker(markerOptions);
                                        m.setTag(cpo);
                                        m.showInfoWindow();


                                        mMap.addMarker(markerOptions);
                                    }

                                }

                            }

                        } catch (Exception e) {
                        }


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

            }
        });

toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



       if(b==true)
       {
           postPreviewFragment = new sale_post_preview_fragment();
           if(postPreviewFragment!=null)
           {
               android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
               android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
               ft.replace(R.id.map,postPreviewFragment).addToBackStack("tag");
               ft.commit();
           }

       }
       else {
           android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
           android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
           ft.remove(postPreviewFragment).addToBackStack("tag").commit();




       }
    }
});


    }


    private void getLocation(){

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) throws NullPointerException {

//        mMap = googleMap;
//
//        final LatLng[] latLng = new LatLng[1];
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        final CircleOptions circleOptions = new CircleOptions();
//        final double[] latitude = new double[1];
//        final double[] longitude = new double[1];
//        final Marker[] marker = new Marker[1];
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]
//                            {android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_LOCATION_PERMISSION);
//        }
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//
//                latitude[0] = location.getLatitude();
//                longitude[0] = location.getLongitude();
//                Toast.makeText(locate_user.this,String.valueOf(latitude[0]),Toast.LENGTH_LONG).show();
//                Geocoder geocoder = new Geocoder(getApplicationContext());
//                try {
//                    List<Address> addresses =
//                            geocoder.getFromLocation(latitude[0], longitude[0], 1);
//                    String result = addresses.get(0).getLocality()+":";
//                    result += addresses.get(0).getCountryName();
//                    latLng[0] = new LatLng(latitude[0], longitude[0]);
//
//
//                    if (marker[0] != null){
//                        marker[0].remove();
//                        marker[0] = mMap.addMarker(new MarkerOptions().position(latLng[0]).title(result));
//                        mMap.setMaxZoomPreference(20);
//                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15f));
//                    }
//                    else{
//                        marker[0] = mMap.addMarker(new MarkerOptions().position(latLng[0]).title(result));
//                        mMap.setMaxZoomPreference(20);
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude[0], longitude[0]),15f));
//                    }
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
////        circleOptions.center(latLng[0]);
////        circleOptions.radius(Float.valueOf(radius)*1000);
////        circleOptions.strokeColor(Color.CYAN);
////        circleOptions.fillColor(0x4D000080);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude[0], longitude[0]),15f));
//       // mMap.addCircle(circleOptions);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//



        Geocoder geocoder = new Geocoder(locate_user.this);
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();
        CircleOptions circleOptions = new CircleOptions();
        mMap = googleMap;
        try{
            addressList = geocoder.getFromLocationName(user_location,10);

            if(addressList!=null)
            {
                for (int i=0;i<addressList.size();i++)
                {
                    hostAddress = addressList.get(i);

                    LatLng latLng = new LatLng(hostAddress.getLatitude(),hostAddress.getLongitude());

                    markerOptions.position(latLng);
                    markerOptions.title(user_location);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                    circleOptions.center(latLng);
                    circleOptions.radius(Float.valueOf(radius)*1000);
                    circleOptions.strokeColor(Color.CYAN);
                    circleOptions.fillColor(0x4D000080);

                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    mMap.addCircle(circleOptions);


                }

              addressList.add(addressList.get(0));

            }

        }catch (Exception e){}

    }
}
