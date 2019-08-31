package com.example.meru;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.util.Log.i;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
   private ArrayList<Groups> itemsData;
   Context context;
    private ArrayList<String> groupId;




    public MyAdapter(ArrayList<Groups> itemsData, Context context, ArrayList<String> groupId) {
        this.itemsData = itemsData;
        this.context=context;
        this.groupId=groupId;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joinedgroupsrow, null);

        // create ViewHolder


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {





        final DatabaseReference myRef = database.getReference("GroupCoverImage");





        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot data:dataSnapshot.getChildren()){

                    if(groupId.get(position).equals(data.getKey())) {
                        Picasso.get().load(data.getValue().toString()).into(viewHolder.groupImage);
                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                i("Fire",databaseError.toString());

            }
        });















        viewHolder.txtViewTitle.setText(itemsData.get(position).GroupName);
        viewHolder.txtViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,IndividualGroup.class);
                Groups groupsData=itemsData.get(position);
                String GroupId=groupId.get(position);
                intent.putExtra("GroupData",groupsData);
                intent.putExtra("GroupId",GroupId);


                context.startActivity(intent);
            }
        });


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView groupImage;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.GroupName);
            groupImage=(ImageView) itemLayoutView.findViewById(R.id.joinedgroupImage);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }




}
