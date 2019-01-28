package com.example.raihan.hobbies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.example.raihan.hobbies.MainActivity.node;

public class edit_sell_post extends AppCompatActivity {

    EditText postText,location,price;
    ImageView postImage;
    ImageButton deleteButton;
    Button submit;
    sell_post_object spo;

    private static final int Gallery_Reques = 1;
    private Uri SelectImgaeUri = null;

    StorageReference imageStore = FirebaseStorage.getInstance().getReference("Sale Post Image");



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

                Toast.makeText(edit_sell_post.this,"Post Deleted",Toast.LENGTH_LONG).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog Dialog = new ProgressDialog(edit_sell_post.this);
                Dialog.setMessage("Updating...");
                Dialog.show();

                StorageReference filePath = imageStore.child(SelectImgaeUri.getLastPathSegment());
                filePath.putFile(SelectImgaeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final Uri downloadUri = taskSnapshot.getDownloadUrl();

                        final DatabaseReference ab = FirebaseDatabase.getInstance().getReference("Sale_post").child(node);
                        ab.orderByChild("post_text").equalTo(spo.getPost_text()).addChildEventListener(
                                new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                        ab.child(dataSnapshot.getKey()).child("price").setValue(price.getText().toString().trim());
                                        ab.child(dataSnapshot.getKey()).child("location").setValue(location.getText().toString().trim());
                                        ab.child(dataSnapshot.getKey()).child("post_text").setValue(postText.getText().toString().trim());
                                        ab.child(dataSnapshot.getKey()).child("imgaeUrl").setValue(downloadUri.toString());

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
                                bc.child(dataSnapshot.getKey()).child("price").setValue(price.getText().toString().trim());
                                bc.child(dataSnapshot.getKey()).child("location").setValue(location.getText().toString().trim());
                                bc.child(dataSnapshot.getKey()).child("post_text").setValue(postText.getText().toString().trim());
                                bc.child(dataSnapshot.getKey()).child("imgaeUrl").setValue(downloadUri.toString());
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

                Dialog.dismiss();
                Toast.makeText(edit_sell_post.this,"Updated",Toast.LENGTH_LONG).show();
            }
        });


        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Reques);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Reques && resultCode == RESULT_OK)
        {
            SelectImgaeUri = data.getData();
            postImage.setImageURI(SelectImgaeUri);
        }
    }
}
