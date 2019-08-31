package com.example.meru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.util.Log.i;

public class EventPage extends AppCompatActivity {
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventpage);
        Intent i = getIntent();
        final DatabaseReference joinedGroupRef=database.getReference("UserData").child("users");
        final ModelEvent modelEvent = (ModelEvent) i.getSerializableExtra("ModelEvent");
        final String groupId=i.getStringExtra("GroupId");
        final String eventId=i.getStringExtra("EventId");
        final DatabaseReference myRef = database.getReference("GroupCoverImage");
        final DatabaseReference gref=database.getReference("JoinedGroup");
        final DatabaseReference grefdata=database.getReference("GroupData");
        final DatabaseReference eventref=database.getReference("Events");
        final ImageView imageView=findViewById(R.id.eventImage);

        final  TextView Date=findViewById(R.id.date);
        final  TextView Location=findViewById(R.id.locateEvent);
        Date.setText(modelEvent.Date);
        Location.setText(modelEvent.Location);
        TextView GroupnameName=findViewById(R.id.GroupName1);
        GroupnameName.setText(modelEvent.GroupName);
        TextView EventName=findViewById(R.id.EventName1);
        EventName.setText(modelEvent.EventName);
        GoogleMapFragment fragment=new GoogleMapFragment();
        Bundle args= new Bundle();
        args.putString("Location",modelEvent.Location);
        args.putString("EventName",modelEvent.EventName);
        final Button JoinEvent=findViewById(R.id.JoinEvent);
        final RecyclerView recyclerView=findViewById(R.id.joinedUsers);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Query query1=  eventref.child(groupId).child(eventId).child("GoingUsers");
          final   List<String> image =new ArrayList<>();

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        for(DataSnapshot data:dataSnapshot.getChildren())
                        {

                                image.add(data.getKey());



                        }

                        JoinedUserImageAdapter joinedUserImageAdapter=new JoinedUserImageAdapter(image,getApplicationContext()) ;
                        recyclerView.setAdapter(joinedUserImageAdapter);










            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       Query query2=eventref.child(groupId).child(eventId).child("GoingUsers");
       query2.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot data:dataSnapshot.getChildren())
               {
                   if(data.getKey().equals(mauth.getUid()))
                   {

                       JoinEvent.setVisibility(View.GONE);
                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        Query query=gref.child(groupId).child(mauth.getUid());




        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {






                         if(dataSnapshot.exists())
                         {

                              Log.i("Equals",mauth.getUid());
                                 JoinEvent.setText("Join this event");
                                 JoinEvent.setAllCaps(false);
                                 JoinEvent.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    JoinEvent.setText("You have joined this event");
                                    joinedGroupRef.child(mauth.getUid()).child("Joiners").child(groupId).child("JoinedEvents").child(eventId).setValue(modelEvent.EventName);


                                 eventref.child(groupId).child(eventId).child("GoingUsers").child(mauth.getUid()).setValue(mauth.getUid());

                            }


                        });







                }
                         else
                         {
                             JoinEvent.setText("Join the group to access event");
                             JoinEvent.setAllCaps(false);
                             JoinEvent.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {

                                     Query query2=grefdata;
                                     query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                             for (DataSnapshot data : dataSnapshot.getChildren()) {


                                                 String Uid = data.getKey();
                                                 if (groupId.equals(Uid)) {

                                                     String GroupName = data.child("GroupName").getValue().toString();
                                                     String Description = data.child("Description").getValue().toString();
                                                     String Organizer = data.child("Organizer").getValue().toString();
                                                     String OrganizerId = data.child("OrganizerId").getValue().toString();
                                                     String Interests = data.child("Interests").getValue().toString();
                                                     String Location = data.child("Location").getValue().toString();


                                                     Groups groups = new Groups(GroupName, Organizer, Description, Location, Interests, OrganizerId);


                                                     Intent intent = new Intent(EventPage.this, IndividualGroup.class);

                                                     String GroupId = groupId;
                                                     intent.putExtra("GroupData", groups);
                                                     intent.putExtra("GroupId", GroupId);
                                                     startActivity(intent);


                                                 }
                                             }
                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError databaseError) {

                                         }
                                     });



                                 }
                             });

                         }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Fire",databaseError.toString());


            }

        });



              fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.gmaps, fragment).commit();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot data:dataSnapshot.getChildren()){


                    if(groupId.equals(data.getKey())) {
                        Picasso.get().load(data.getValue().toString()).into(imageView);

                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                i("Fire",databaseError.toString());

            }
        });


    }

}
