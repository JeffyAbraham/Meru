package com.example.meru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class JoinedEvnts extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_joined_events, container, false);
     final    RecyclerView recyclerView=rootView.findViewById(R.id.joinedEvents);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);



        final DatabaseReference myRef = database.getReference("UserData");
        Query query=myRef.child("users").child(mAuth.getUid()).child("Joiners");


        final List<String> EventId=new ArrayList<>();
        final List<String> GroupId=new ArrayList<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Log.i("Evenit",data.toString());

                    GroupId.add(data.getKey());


                      for(DataSnapshot dataSnapshot1:data.child("JoinedEvents").getChildren())

                       EventId.add(dataSnapshot1.getKey());





                }

               JoinedEventsadapter mAdapter = new JoinedEventsadapter(EventId,getContext(),GroupId);

                recyclerView.setAdapter(mAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Fire",databaseError.toString());

            }
        });









        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event


}
