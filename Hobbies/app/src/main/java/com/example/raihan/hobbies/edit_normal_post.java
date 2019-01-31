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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.example.raihan.hobbies.MainActivity.node;

public class edit_normal_post extends AppCompatActivity {

    EditText postEditText;
    ImageButton deletePost;
    ImageView postImage;
    Button submitButton;
    normal_post_object npo;

    DatabaseReference post,globalPost;

    private static final int Gallery_Reques = 1;
    private Uri SelectImgaeUri = null;

    StorageReference imageStore = FirebaseStorage.getInstance().getReference("Post Image");

    @Override
    protected void onCreate(Bundle savedInstanceState)throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_normal_post);

        Intent intent = getIntent();
        npo = intent.getParcelableExtra("object");

        post = FirebaseDatabase.getInstance().getReference("post").child(node);
        globalPost = FirebaseDatabase.getInstance().getReference("global_post");

        postEditText = findViewById(R.id.edit_normal_post_text);


        deletePost = findViewById(R.id.deleteNormalPost);
        postImage = findViewById(R.id.edit_normal_post_Image);
        submitButton = findViewById(R.id.edit_normal_post_submit);

        Picasso.get().load(npo.getImgaeUrl()).fit().centerCrop().into(postImage);
        postEditText.setText(npo.getPost_text());

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_post();
                Toast.makeText(edit_normal_post.this,"Post Deleted",Toast.LENGTH_LONG).show();
            }

        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog Dialog = new ProgressDialog(edit_normal_post.this);
                Dialog.setMessage("Processing");
                Dialog.show();

                if(SelectImgaeUri != null)
                {

                    StorageReference filePath = imageStore.child(SelectImgaeUri.getLastPathSegment());

                    filePath.putFile(SelectImgaeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final Uri  downloadUri = taskSnapshot.getDownloadUrl();

                            final DatabaseReference ab = FirebaseDatabase.getInstance().getReference("post").child(node);
                            ab.orderByChild("hint").equalTo(npo.getHint()).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    ab.child(dataSnapshot.getKey()).child("post_text").setValue(postEditText.getText().toString().trim());
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

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            final DatabaseReference bc = FirebaseDatabase.getInstance().getReference("global_post");
                            bc.orderByChild("hint").equalTo(npo.getHint()).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    bc.child(dataSnapshot.getKey()).child("post_text").setValue(postEditText.getText().toString().trim());
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


                }

                else
                {
                    update();
                }

                Dialog.dismiss();
                Toast.makeText(edit_normal_post.this,"Post Updated",Toast.LENGTH_LONG).show();
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


    void delete_post()
    {
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

    void update()
    {
        final DatabaseReference ab = FirebaseDatabase.getInstance().getReference("post").child(node);
        ab.orderByChild("hint").equalTo(npo.getHint()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ab.child(dataSnapshot.getKey()).child("post_text").setValue(postEditText.getText().toString().trim());

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

        final DatabaseReference bc = FirebaseDatabase.getInstance().getReference("global_post");
        bc.orderByChild("hint").equalTo(npo.getHint()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                bc.child(dataSnapshot.getKey()).child("post_text").setValue(postEditText.getText().toString().trim());


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Reques && resultCode == RESULT_OK)
        {
            assert data != null;
            SelectImgaeUri = data.getData();
            postImage.setImageURI(SelectImgaeUri);
        }

    }
}
