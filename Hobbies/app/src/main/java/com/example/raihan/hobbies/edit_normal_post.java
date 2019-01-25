package com.example.raihan.hobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.example.raihan.hobbies.MainActivity.node;

public class edit_normal_post extends AppCompatActivity {

    EditText postEditText;
    ImageButton deletePost;
    ImageView postImage;
    Button submitButton;
    normal_post_object npo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_normal_post);

        Intent intent = getIntent();
        npo = intent.getParcelableExtra("object");

        postEditText = findViewById(R.id.edit_normal_post_text);
        deletePost = findViewById(R.id.deleteNormalPost);
        postImage = findViewById(R.id.edit_normal_post_Image);
        submitButton = findViewById(R.id.edit_normal_post_submit);

        Picasso.get().load(npo.getImgaeUrl()).fit().centerCrop().into(postImage);
        postEditText.setText(npo.getPost_text());

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final DatabaseReference ab = FirebaseDatabase.getInstance().getReference("post").child(node);
                ab.orderByChild("hint").equalTo(npo.getHint()).addChildEventListener(
                        new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                ab.child(dataSnapshot.getKey()).setValue(null);
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
                        }
                );

               final DatabaseReference bc = FirebaseDatabase.getInstance().getReference("global_post");
                bc.orderByChild("hint").equalTo(npo.getHint()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        bc.child(dataSnapshot.getKey()).setValue(null);
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


    }
}
