package com.example.raihan.hobbies;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.example.raihan.hobbies.MainActivity.node;

public class profile_edit extends AppCompatActivity {

    EditText name,address,phoneNo,occupation,hobby;
    String S_name,S_address,S_phoneNo,S_occupation,S_hobby;
    Button button;
    ImageView imageView;
    profile_info pn;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Picasso.get().load(pn.getRegister_ImageUri()).fit().centerCrop().into(imageView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users_info").child(node);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            }
        });

    }
}
