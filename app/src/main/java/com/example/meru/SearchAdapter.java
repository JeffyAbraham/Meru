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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.util.Log.i;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<ModelEvent> itemsData;
    Context context;
    private List<String> groupId;
    private List<String> eventId;





    public SearchAdapter(List<ModelEvent> itemsData, Context context, List<String> groupId, List<String> eventId) {
        this.itemsData = itemsData;
        this.context=context;
        this.groupId=  groupId;
        this.eventId=eventId;
     Log.i("ITEM",itemsData.toString());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchadapter, null);

        // create ViewHolder


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {



        String date=itemsData.get(position).Date;
        try {
            Date datee=new SimpleDateFormat("dd/MM/YY").parse(date);
            viewHolder.Date.setText(datee.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.Location.setText(itemsData.get(position).Location.toString());
        viewHolder.GroupTitle.setText(itemsData.get(position).GroupName.toString());
        viewHolder.EventTitle.setText(itemsData.get(position).EventName.toString());
        viewHolder.EventTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,EventPage.class);
                ModelEvent modelEvent=itemsData.get(position);
                String GroupId=groupId.get(position);
                intent.putExtra("ModelEvent",modelEvent);
                intent.putExtra("GroupId",GroupId);
                intent.putExtra("EventId",eventId.get(position));
                context.startActivity(intent);

            }
        });


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView EventTitle;
        public TextView GroupTitle;
        public TextView Location;
        public TextView Date;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            EventTitle = (TextView) itemLayoutView.findViewById(R.id.EventName3);
            GroupTitle=itemLayoutView.findViewById(R.id.GroupName3);
            Location=itemLayoutView.findViewById(R.id.locationEvent);
            Date=itemLayoutView.findViewById(R.id.datevent);



        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }




}
