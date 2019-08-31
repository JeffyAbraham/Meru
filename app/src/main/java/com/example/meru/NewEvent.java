package com.example.meru;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class NewEvent extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public  ArrayList<Groups> list = new ArrayList<Groups>();
    public  ArrayList<String> GroupId=new ArrayList<String>();
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_event, container, false);

        viewPager = rootView.findViewById(R.id.pager2);

        TabGroup2Adapter mAdapter = new TabGroup2Adapter(getChildFragmentManager());
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(mAdapter);





















        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.JoinedGroupsRecycle);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);



        final DatabaseReference myRef = database.getReference("UserData");
        Query query=myRef.child("users").child(mAuth.getUid()).child("Joiners");



        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data:dataSnapshot.getChildren()){

                    
                    
                    GroupId.add(data.getKey());
                    String GroupName=data.child("GroupName").getValue().toString();
                    String Description=data.child("Description").getValue().toString();
                    String Organizer=data.child("Organizer").getValue().toString();
                    String OrganizerId=data.child("OrganizerId").getValue().toString();
                    String Interests=data.child("Interests").getValue().toString();
                    String Location=data.child("Location").getValue().toString();


                    Groups groups=new Groups(GroupName,Organizer,Description,Location,Interests,OrganizerId);

                    list.add(groups);
                 

                }
                MyAdapter mAdapter = new MyAdapter(list,getContext(),GroupId);

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView addGroups = (TextView) getView().findViewById(R.id.NewGroup);
        addGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddGroupActivity.class);
                startActivity(intent);
            }
        });
    }
}