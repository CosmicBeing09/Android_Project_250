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

import static com.example.raihan.hobbies.MainActivity.node;

public class manage_normal_post_fragment extends Fragment implements ItemClickListener {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private normal_post_view_adapter npva;
    private DatabaseReference databaseReference;
    public ArrayList<normal_post_object> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manage_normal_post_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) throws NullPointerException {
        super.onViewCreated(view, savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("post").child(node);
        recyclerView = (RecyclerView) view.findViewById(R.id.manage_normal_post_recycler);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
         npva = new normal_post_view_adapter(arrayList,getActivity());

         databaseReference.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 normal_post_object npo =dataSnapshot.getValue(normal_post_object.class);
                 arrayList.add(npo);
                 npva.notifyDataSetChanged();
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
         recyclerView.setAdapter(npva);
         npva.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        final normal_post_object npo = arrayList.get(position);
        Intent i = new Intent(getActivity(),edit_normal_post.class);
        i.putExtra("object",npo);
        startActivity(i);
    }
}
