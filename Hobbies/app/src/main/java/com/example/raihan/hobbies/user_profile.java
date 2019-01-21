package com.example.raihan.hobbies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_profile extends Fragment {

    TextView name,address,phone_no,user_name,hobby,occupation;
    String node;
    DatabaseReference pDatabase;
    CircleImageView circleImageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle!=null)
        {
            node = bundle.getString("user").trim();
        }


        circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);

        name = (TextView) view.findViewById(R.id.profile_name);
        address = (TextView) view.findViewById(R.id.profile_address);
        phone_no = (TextView)view.findViewById(R.id.profile_phoneNo);
        user_name =(TextView)view.findViewById(R.id.profile_userName);
        hobby = (TextView) view.findViewById(R.id.profile_hobby);
        occupation = (TextView)view.findViewById(R.id.profile_occupation);

        pDatabase = FirebaseDatabase.getInstance().getReference("Users_info");

        pDatabase.child(node).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile_info pn = dataSnapshot.getValue(profile_info.class);
               Picasso.get().load(pn.getRegister_ImageUri()).into(circleImageView);
                name.setText(pn.getRegister_name());
                address.setText(pn.getAddress());
                phone_no.setText(pn.getPhone_no());
                user_name.setText(pn.getUser_name());
                hobby.setText(pn.getHobby());
                occupation.setText(pn.getOccupation());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
