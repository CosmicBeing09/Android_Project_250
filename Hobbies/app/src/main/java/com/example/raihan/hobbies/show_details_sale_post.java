package com.example.raihan.hobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class show_details_sale_post extends AppCompatActivity {

    TextView userNmae,location,petType,postType,price,postText;
    CircleImageView circleImageView;
    ImageView imageView;
    sell_post_object spo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_sale_post);

        Intent intent = getIntent();
        spo =(sell_post_object) intent.getParcelableExtra("object");

        userNmae = (TextView) findViewById(R.id.Details_userName);
        location = (TextView) findViewById(R.id.Details_location);
        petType = (TextView)findViewById(R.id.Details_petType);
        postType = (TextView)findViewById(R.id.Details_postType);
        price = (TextView) findViewById(R.id.Details_price);
        postText = (TextView) findViewById(R.id.Details_post);

        circleImageView = (CircleImageView) findViewById(R.id.Details_userImage);
        imageView = (ImageView) findViewById(R.id.Details_post_image);

        userNmae.setText(spo.getUser());
        location.setText(spo.getLocation());
        petType.setText(spo.getPet_type());
        postType.setText(spo.getPost_type());
        price.setText(spo.getPrice());
        postText.setText(spo.getPost_text());

        Picasso.get().load(spo.getUser_imageUri()).fit().centerCrop().into(circleImageView);
        Picasso.get().load(spo.getImgaeUrl()).fit().centerCrop().into(imageView);

    }
}
