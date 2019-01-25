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

import static com.example.raihan.hobbies.locate_user.fragment_petType;

public class sale_post_preview_fragment extends Fragment implements ItemClickListener {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private post_preview_adapter ppa;
    private DatabaseReference databaseReference;
    public ArrayList<sell_post_object> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sale_post_preview_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle!=null)
        {
            arrayList = bundle.getParcelableArrayList("arrayList");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("global_sale_post");
        recyclerView = (RecyclerView) view.findViewById(R.id.sale_post_preview_recycler);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ppa = new post_preview_adapter(arrayList,getActivity());


        recyclerView.setAdapter(ppa);
        ppa.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
   final sell_post_object spo = arrayList.get(position);
    Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_LONG).show();
       Intent i = new Intent(getActivity(),show_details_sale_post.class);
       i.putExtra("object",spo);
      startActivity(i);
    }
}
