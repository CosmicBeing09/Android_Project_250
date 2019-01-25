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
import com.google.firebase.database.Query;

import java.util.ArrayList;

import static com.example.raihan.hobbies.MainActivity.node;


public class notification_preview extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private notification_adapter na;
    private DatabaseReference databaseReference;
    public ArrayList<notification_object> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) throws NullPointerException {
        super.onViewCreated(view, savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("notification").child(node);
        recyclerView = (RecyclerView) view.findViewById(R.id.notification_recycler);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        na = new notification_adapter(arrayList,getActivity());
        recyclerView.setAdapter(na);
        na.setOnItemCliclListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void OnMessageClick(int position) {

                Toast.makeText(getActivity(),arrayList.get(position).getUser_name().trim(),Toast.LENGTH_LONG).show();
               // DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("notification").child(node);
                Intent intent = new Intent(getActivity(),message_view.class);
                intent.putExtra("friend",arrayList.get(position).getUser_name().trim());
                startActivity(intent);

            }

            @Override
            public void OnDeleteClick(int position) {

            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                notification_object no = dataSnapshot.getValue(notification_object.class);
                arrayList.add(no);
                na.notifyDataSetChanged();
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


}
