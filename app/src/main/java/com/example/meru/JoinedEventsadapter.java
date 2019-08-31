package com.example.meru;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.i;

public class JoinedEventsadapter extends RecyclerView.Adapter<JoinedEventsadapter.ViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<String> itemsData;
    Context context;
    private List<String> groupId;




    public JoinedEventsadapter(List<String> itemsData, Context context, List<String> groupId) {
        this.itemsData = itemsData;
        this.context=context;
        this.groupId=groupId;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public JoinedEventsadapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joinedeventsrow, null);

        // create ViewHolder


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {




        final DatabaseReference myRef = database.getReference("Events");





        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot data:dataSnapshot.getChildren()){

                    if(groupId.get(position).equals(data.getKey())) {


                        for(DataSnapshot dataSnapshot1:data.getChildren())
                        {
                                Log.i("RECK",data.toString());

                                viewHolder.txtViewTitle.setText(dataSnapshot1.child("GroupName").getValue().toString());
                                viewHolder.txtViewEvent.setText(dataSnapshot1.child("EventName").getValue().toString());
                                viewHolder.location.setText(dataSnapshot1.child("Location").getValue().toString());


                        }

                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                i("Fire",databaseError.toString());

            }
        });






    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public TextView txtViewEvent;
        public TextView location;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.joinedeventGroupname);
            txtViewEvent=itemLayoutView.findViewById(R.id.joinedeventName);
            location=itemLayoutView.findViewById(R.id.joinedlocation);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }




}
