package com.example.raihan.hobbies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class id_preview_fragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private id_previewAdapter ipA;
    private DatabaseReference databaseReference;
    public ArrayList<global_profile_info> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.id_preview_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        databaseReference = FirebaseDatabase.getInstance().getReference("Global_users");
        recyclerView = (RecyclerView) view.findViewById(R.id.id_preview_recycler);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ipA = new id_previewAdapter(arrayList,getActivity());

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                global_profile_info gpi = dataSnapshot.getValue(global_profile_info.class);
                arrayList.add(gpi);
                ipA.notifyDataSetChanged();

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
        recyclerView.setAdapter(ipA);
        ipA.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                final global_profile_info gpi = arrayList.get(position);
                Toast.makeText(getActivity(),gpi.getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),message_view.class);
                intent.putExtra("friend",gpi.getName());
                startActivity(intent);
            }
        });


    }
}
