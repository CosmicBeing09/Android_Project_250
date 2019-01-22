package com.example.raihan.hobbies;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.security.spec.ECField;

public class mapInfo implements GoogleMap.InfoWindowAdapter{

    private Context context;

    public  mapInfo(Context context)
    {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        try {
            View view = ((Activity) context).getLayoutInflater()
                    .inflate(R.layout.activity_map_info, null);

            TextView name = view.findViewById(R.id.mapInfoText1);
            TextView phone_no = view.findViewById(R.id.mapInfoText2);
            ImageView imageView = view.findViewById(R.id.mapInfoImage);

            global_profile_info gpi = (global_profile_info) marker.getTag();

            Picasso.get().load(gpi.getGlobal_imageUri()).into(imageView);
            name.setText(gpi.getName());
            phone_no.setText(gpi.getPhone_no());

            return view;
        }catch (Exception e){}
        return null;
    }
}
