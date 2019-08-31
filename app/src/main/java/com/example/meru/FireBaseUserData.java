package com.example.meru;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class FireBaseUserData {
    final   FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public void getUserInfoandPost(final String GroupName,final String Description, final String Location, final String Interests)
    {

        final DatabaseReference myRef = database.getReference("UserData");
        final DatabaseReference groupRef=database.getReference("GroupData");
        final DatabaseReference joinedGroupRef=database.getReference("JoinedGroup");

        Query query=myRef.child("users");


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){


                         String Uid=data.getKey();
                            if(mAuth.getUid().equals(Uid))
                            {

                                String username=data.child("username").getValue().toString();


                                String GroupId=UUID.randomUUID().toString();
                                Groups groups=new Groups(GroupName,username,Description,Location,Interests,Uid);

                                groupRef.child(GroupId).setValue(groups);
                                joinedGroupRef.child(GroupId).child(Uid).setValue(username);
                                myRef.child("users").child(data.getKey()).child("Joiners").child(GroupId).setValue(groups);


                                Log.i("DRAF",Uid);










                            }



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Fire",databaseError.toString());

            }
        });




    }








}
