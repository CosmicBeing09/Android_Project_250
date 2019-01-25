package com.example.raihan.hobbies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class register extends AppCompatActivity  implements View.OnClickListener{

    private EditText inputName, inputPassword,inputAddress,inputPhone,inputUserName,inputHobby,inputOccupation;
    private Button signup;
    private FirebaseAuth mAuth;
    private String name,password,address,phoneNo,user_name,occcupation,hobby;
    private ImageView user_image;
    private static final int Gallery_Reques = 1;
    private Uri SelectImgaeUri = null;



    DatabaseReference gDatabase = FirebaseDatabase.getInstance().getReference("Global_users");
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users_info");
    StorageReference imageStore = FirebaseStorage.getInstance().getReference("User_image");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_image = (ImageView) findViewById(R.id.registerImage);

        inputName = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        inputAddress = (EditText) findViewById(R.id.Address);
        inputUserName = (EditText) findViewById(R.id.register_name);
        inputHobby = (EditText) findViewById(R.id.hobby);
        inputOccupation = (EditText) findViewById(R.id.Occupation);
        inputPhone = (EditText) findViewById(R.id.PhoneNo);

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Reques);
            }
        });

        mAuth = FirebaseAuth.getInstance();


        signup = findViewById(R.id.sign_up_button);
        signup.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.sign_up_button){
            name = inputName.getText().toString().trim();
            password = inputPassword.getText().toString().trim();
            address = inputAddress.getText().toString().trim();
            phoneNo = inputPhone.getText().toString().trim();
            user_name = inputUserName.getText().toString().trim();
            occcupation = inputOccupation.getText().toString().trim();
            hobby = inputHobby.getText().toString().trim();




            if (TextUtils.isEmpty(user_name)) {
                Toast.makeText(getApplicationContext(), "Enter user name!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(address)) {
                Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                return;
            }


            final ProgressDialog Dialog = new ProgressDialog(register.this);
            Dialog.setMessage("Doing something...");
            Dialog.show();


            mAuth.createUserWithEmailAndPassword(user_name+"@gmail.com", password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(register.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(register.this, login.class);
                                i.putExtra("Type", "Driver");
                                i.putExtra("Id", user_name);
                                startActivity(i);

                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                Dialog.dismiss();
                            }

                            // ...
                        }
                    });

            startPosting();


        }
    }

    public void startPosting()
    {
        if(SelectImgaeUri != null)
        {

            StorageReference filePath = imageStore.child(user_name).child(SelectImgaeUri.getLastPathSegment());

            filePath.putFile(SelectImgaeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    profile_info pn = new profile_info(downloadUri.toString(),name,address,phoneNo,user_name,hobby,occcupation);
                    mDatabase.child(user_name).setValue(pn);


                    global_profile_info gpi = new global_profile_info(name,address,phoneNo,downloadUri.toString());
                    gDatabase.push().setValue(gpi);


                }
            });
        }
        else if(SelectImgaeUri == null)
        {
            profile_info pn = new profile_info(null,name,address,phoneNo,user_name,hobby,occcupation);
            mDatabase.push().child(user_name).setValue(pn);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gallery_Reques && resultCode == RESULT_OK)
        {
            SelectImgaeUri = data.getData();
            user_image.setImageURI(SelectImgaeUri);
        }
    }

}
