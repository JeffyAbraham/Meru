package com.example.meru;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


public class ChatApplication extends AppCompatActivity {
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_mainrf;
    EmojiconEditText emojiconEditText;
    ImageView emojiButton, submitButton;
    EmojIconActions emojIconActions;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChatApplication.this, HomeBottom.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_application);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Intent i = getIntent();
        final String receiverId = i.getStringExtra("reveierId");

        final DatabaseReference myRef = database.getReference("UserData");
        Query query = myRef.child("users");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (receiverId.equals(data.getKey())) {
                        TextView rec = findViewById(R.id.rec);

                        rec.append("" + data.child("username").getValue().toString());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        activity_mainrf = (RelativeLayout) findViewById(R.id.chatLay);

        emojiButton = (ImageView) findViewById(R.id.emoji_button);
        submitButton = (ImageView) findViewById(R.id.submit_button);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojicon_edit_text);
        emojIconActions = new EmojIconActions(getApplicationContext(), activity_mainrf, emojiconEditText, emojiButton);
        emojIconActions.ShowEmojIcon();




        
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().child("UserData").child("users").child(receiverId).child("Message").child(FirebaseAuth.getInstance().getUid()).push().setValue(new ChatMessage(emojiconEditText.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                emojiconEditText.setText("");
                emojiconEditText.requestFocus();
            }
        });


        displayChatMessage(receiverId);

    }

    private void displayChatMessage(String receiverId) {

        final List<ModelChatMessage>modelChatMessagesList=new ArrayList<>();


       final RecyclerView listOfMessage = (RecyclerView) findViewById(R.id.list_of_message);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listOfMessage.setLayoutManager(layoutManager);


        Query query = FirebaseDatabase.getInstance().getReference("UserData").child("users").child(receiverId).child("Message").child(FirebaseAuth.getInstance().getUid());
        Query query2=FirebaseDatabase.getInstance().getReference("UserData").child("users").child(FirebaseAuth.getInstance().getUid()).child("Message").child(receiverId);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ModelChatMessage modelChatMessage= dataSnapshot.getValue(ModelChatMessage.class);
                modelChatMessagesList.add(modelChatMessage);
                ListMessageAdapter listMessageAdapter=new ListMessageAdapter(modelChatMessagesList,getApplicationContext());
                listOfMessage.setAdapter(listMessageAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                ModelChatMessage modelChatMessage= dataSnapshot.getValue(ModelChatMessage.class);
                modelChatMessagesList.add(modelChatMessage);
              ListMessageAdapter listMessageAdapter=new ListMessageAdapter(modelChatMessagesList,getApplicationContext());
              listOfMessage.setAdapter(listMessageAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

