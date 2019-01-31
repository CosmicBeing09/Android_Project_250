package com.example.raihan.hobbies;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.raihan.hobbies.MainActivity.node;
import static com.example.raihan.hobbies.MainActivity.pi;

public class message_view extends AppCompatActivity {

    private List<chat_message_object> msgList = new ArrayList<>();
    private msg_adapter ma;
    private String user_name;
    private String rev_user_name;
    private RecyclerView recyclerView;
    private EditText messageET;
    private ImageView sendButton;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        Intent intent = getIntent();
        String temp = intent.getStringExtra("friend");

        user_name = node +","+temp;
        rev_user_name = temp+","+node;

        recyclerView = (RecyclerView)findViewById(R.id.MessageRecyclerView);
        messageET = (EditText)findViewById(R.id.MessageEditText);
        sendButton = (ImageView) findViewById(R.id.MessageSendButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("chat_message");
        mDatabase = FirebaseDatabase.getInstance().getReference("chat_message");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageET.getText().toString().trim();
                hideKeyboardwithoutPopulate(message_view.this);
                if (!message.equals("")) {

                    chat_message_object chatMessage = new chat_message_object(message,node, pi.getRegister_ImageUri());
                    databaseReference.child(user_name).push().setValue(chatMessage);
                    databaseReference.child(rev_user_name).push().setValue(chatMessage);
                }
                messageET.setText("");
            }
        });

        ma = new msg_adapter(msgList,node);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ma);
        recyclerView.scrollToPosition(ma.getItemCount()-1);

        try {


            mDatabase.child(user_name).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    msgList.add(dataSnapshot.getValue(chat_message_object.class));
                    ma.notifyDataSetChanged();
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
        }catch (Exception e){}






    }

    public static void hideKeyboardwithoutPopulate(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
