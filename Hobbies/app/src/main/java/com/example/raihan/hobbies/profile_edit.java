package com.example.raihan.hobbies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class profile_edit extends AppCompatActivity {

    EditText name,address,phoneNo,occupation,hobby;
    String S_name,S_address,S_phoneNo,S_occupation,S_hobby;
    Button button;
    ImageView imageView;
    profile_info pn;
    DatabaseReference databaseReference;


    private static final int Gallery_Reques = 1;
    private Uri SelectImgaeUri = null;

    StorageReference imageStore = FirebaseStorage.getInstance().getReference("User_image");

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        name = (EditText)findViewById(R.id.Edit_name);
        address = (EditText)findViewById(R.id.Edit_Address);
        phoneNo = (EditText)findViewById(R.id.Edit_PhoneNo);
        occupation = (EditText)findViewById(R.id.Edit_Occupation);
        hobby = (EditText)findViewById(R.id.Edit_hobby);

        imageView = (ImageView)findViewById(R.id.Edit_registerImage);
        button = (Button)findViewById(R.id.Edit_submit);

        Intent intent = getIntent();
        pn =(profile_info) intent.getParcelableExtra("profile");

        name.setText(pn.getRegister_name());
        address.setText(pn.getAddress());
        phoneNo.setText(pn.getPhone_no());
        occupation.setText(pn.getOccupation());
        hobby.setText(pn.getHobby());
        try {
            Picasso.get().load(pn.getRegister_ImageUri()).fit().centerCrop().into(imageView);
        }catch (Exception e){}

        databaseReference = FirebaseDatabase.getInstance().getReference("Users_info").child(node);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog Dialog = new ProgressDialog(profile_edit.this);
                Dialog.setMessage("Processing");
                Dialog.show();

                S_name = name.getText().toString().trim();
                S_address = address.getText().toString().trim();
                S_phoneNo = phoneNo.getText().toString().trim();
                S_occupation = occupation.getText().toString().trim();
                S_hobby = hobby.getText().toString().trim();



                databaseReference.child("address").setValue(S_address);
                databaseReference.child("hobby").setValue(S_hobby);
                databaseReference.child("occupation").setValue(S_occupation);
                databaseReference.child("register_name").setValue(S_name);
                databaseReference.child("phone_no").setValue(S_phoneNo);


                if(SelectImgaeUri != null)
                {

                    StorageReference filePath = imageStore.child(node).child(SelectImgaeUri.getLastPathSegment());

                    filePath.putFile(SelectImgaeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final Uri  downloadUri = taskSnapshot.getDownloadUrl();
                            databaseReference.child("register_ImageUri").setValue(downloadUri.toString().trim());


                            final DatabaseReference ab = FirebaseDatabase.getInstance().getReference("Global_users");
                            ab.orderByChild("name").equalTo(node).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    ab.child(dataSnapshot.getKey()).child("global_imageUri").setValue(downloadUri.toString().trim());
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

                Dialog.dismiss();
                Toast.makeText(profile_edit.this,"Profile Updated",Toast.LENGTH_SHORT).show();

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
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
            imageView.setImageURI(SelectImgaeUri);
        }
    }

}
