package com.example.raihan.hobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.raihan.hobbies.MainActivity.pi;

public class show_details_sale_post extends AppCompatActivity {

    TextView userNmae,location,petType,postType,price,postText;
    CircleImageView circleImageView;
    ImageButton messageButton,notificationButton;
    ImageView imageView;
    sell_post_object spo;
    DatabaseReference notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_sale_post);



        Intent intent = getIntent();
        spo =(sell_post_object) intent.getParcelableExtra("object");

        notification = FirebaseDatabase.getInstance().getReference("notification").child(spo.getUser());

        userNmae = (TextView) findViewById(R.id.Details_userName);
        location = (TextView) findViewById(R.id.Details_location);
        petType = (TextView)findViewById(R.id.Details_petType);
        postType = (TextView)findViewById(R.id.Details_postType);
        price = (TextView) findViewById(R.id.Details_price);
        postText = (TextView) findViewById(R.id.Details_post);

        messageButton = (ImageButton)findViewById(R.id.Details_messageButton);
        notificationButton = (ImageButton)findViewById(R.id.Details_notificationButton);

        circleImageView = (CircleImageView) findViewById(R.id.Details_userImage);
        imageView = (ImageView) findViewById(R.id.Details_post_image);

        userNmae.setText(spo.getUser());
        location.setText(spo.getLocation());
        petType.setText(spo.getPet_type());
        postType.setText(spo.getPost_type());
        price.setText(spo.getPrice());
        postText.setText(spo.getPost_text());

        try {
            Picasso.get().load(spo.getUser_imageUri()).fit().centerCrop().into(circleImageView);
            Picasso.get().load(spo.getImgaeUrl()).fit().centerCrop().into(imageView);
        }catch (Exception e){}

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int key = new Random().nextInt();
                String ref = String.valueOf(key);
                notification.push().setValue(new notification_object(pi.getUser_name(),pi.getRegister_ImageUri(),spo.getImgaeUrl(),ref));
                Toast.makeText(show_details_sale_post.this,"Notification sent",Toast.LENGTH_LONG).show();
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(show_details_sale_post.this,message_view.class);
                intent1.putExtra("friend",spo.getUser().trim());
                startActivity(intent1);
            }
        });

    }
}
