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
    public View getInfoContents(Marker marker) throws NullPointerException {

        try {
            View view = ((Activity) context).getLayoutInflater()
                    .inflate(R.layout.activity_map_info, null);

            TextView name = view.findViewById(R.id.mapInfoText1);
            TextView phone_no = view.findViewById(R.id.mapInfoText2);
            ImageView imageView = view.findViewById(R.id.mapInfoImage);

            sell_post_object spo = (sell_post_object) marker.getTag();

            try {
                Picasso.get().load(spo.getImgaeUrl()).into(imageView);
            }catch (Exception e){}

            name.setText(spo.getUser());
            phone_no.setText(spo.getPet_type());

            return view;
        }catch (Exception e){}

        return null;
    }
}
