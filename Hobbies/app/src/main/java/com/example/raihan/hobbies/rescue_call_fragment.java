package com.example.raihan.hobbies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



public class rescue_call_fragment extends Fragment {

    EditText users_location;
    Button see_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rescue_fragmant,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        users_location = (EditText) view.findViewById(R.id.rescue_location);
        see_user = (Button) view.findViewById(R.id.rescue_button);

        see_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String location = users_location.getText().toString();
                Intent intent = new Intent(getActivity(),locate_user.class);
                intent.putExtra("user_location",location);
                startActivity(intent);

            }
        });


    }
}
