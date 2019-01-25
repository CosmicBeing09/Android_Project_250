package com.example.raihan.hobbies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.raihan.hobbies.MainActivity.node;

public class edit_sell_post extends AppCompatActivity {

    EditText postText,location,price;
    ImageView postImage;
    ImageButton deleteButton;
    Button submit;
    sell_post_object spo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sell_post);

        Intent intent = getIntent();
        spo = intent.getParcelableExtra("object");

        postText = findViewById(R.id.edit_sell_post_text);
        location = findViewById(R.id.edit_sell_address);
        price = findViewById(R.id.edit_sell_price);
        postImage = findViewById(R.id.edit_sellImage);
        deleteButton = findViewById(R.id.deleteSellPost);
        submit = findViewById(R.id.edit_sell_submit);

        Picasso.get().load(spo.getImgaeUrl()).fit().centerCrop().into(postImage);
        location.setText(spo.getLocation());
        price.setText(spo.getPrice());
        postText.setText(spo.getPost_text());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference ab = FirebaseDatabase.getInstance().getReference("Sale_post").child(node);
                ab.orderByChild("post_text").equalTo(spo.getPost_text()).addChildEventListener(
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

                final DatabaseReference bc = FirebaseDatabase.getInstance().getReference("global_sale_post").child(spo.getPet_type().trim());
                bc.orderByChild("post_text").equalTo(spo.getPost_text()).addChildEventListener(new ChildEventListener() {
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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                spo.setLocation(location.getText().toString().trim());
                spo.setPrice(price.getText().toString().trim());
                spo.setPost_text(postText.getText().toString().trim());


                final DatabaseReference ab = FirebaseDatabase.getInstance().getReference("Sale_post").child(node);
                ab.orderByChild("post_text").equalTo(spo.getPost_text()).addChildEventListener(
                        new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                ab.child(dataSnapshot.getKey()).setValue(spo);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );



                final DatabaseReference bc = FirebaseDatabase.getInstance().getReference("global_sale_post").child(spo.getPet_type().trim());
                bc.orderByChild("post_text").equalTo(spo.getPost_text()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        bc.child(dataSnapshot.getKey()).setValue(spo);
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
