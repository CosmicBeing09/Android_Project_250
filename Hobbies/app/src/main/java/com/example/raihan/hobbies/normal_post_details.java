package com.example.raihan.hobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.raihan.hobbies.MainActivity.node;
import static com.example.raihan.hobbies.MainActivity.pi;

public class normal_post_details extends AppCompatActivity {

    normal_post_object npo;
    ImageView postImage;
    CircleImageView user_image;
    TextView user_name,post_text;
    EditText comment;
    ImageView send;
    String temp = null;

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private comment_adapter ca;

    public ArrayList<comment_object> arrayList = new ArrayList<>();

    DatabaseReference ref1,ref2;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_post_details);

        Intent intent = getIntent();
        npo = intent.getParcelableExtra("object");

        ref1 = FirebaseDatabase.getInstance().getReference("comment").child(npo.getHint());
        ref2 = FirebaseDatabase.getInstance().getReference("comment").child(npo.getHint());

        postImage = findViewById(R.id.show_details_post_image);
        user_image = findViewById(R.id.show_details_userImage);
        user_name = findViewById(R.id.show_details_userName);
        post_text = findViewById(R.id.show_details_post_text);
        comment = findViewById(R.id.commentEditText);
        send = findViewById(R.id.commentSendButton);

        user_name.setText(npo.getUser());
        post_text.setText(npo.getPost_text());

        Picasso.get().load(npo.getImgaeUrl()).fit().centerCrop().into(postImage);
        Picasso.get().load(npo.getUser_imageUri()).fit().centerCrop().into(user_image);

        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ca = new comment_adapter(arrayList,this);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = comment.getText().toString().trim();

                if (!temp.equals("")) {

                    comment_object co = new comment_object(temp,pi.getUser_name(),pi.getRegister_ImageUri());
                     ref1.push().setValue(co);

                }
                comment.setText("");
            }
        });

        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                comment_object co = dataSnapshot.getValue(comment_object.class);
                arrayList.add(co);
                ca.notifyDataSetChanged();
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
        recyclerView.setAdapter(ca);




    }
}
