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

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.util.Log.i;


public class RecycleGroupAdapter extends RecyclerView.Adapter<RecycleGroupAdapter.ViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<ModelEvent> itemsData;
    private List<String> eventId;
    Context context;
    String groupId;




    public RecycleGroupAdapter(List<ModelEvent> itemsData, Context context, String groupId, List<String> eventId) {
        this.itemsData = itemsData;
        this.context=context;
        this.eventId=eventId;
        this.groupId=groupId;
        Log.i("SIZER",Integer.toString(itemsData.size()));

    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eventgrouprow, null);

        // create ViewHolder


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.txtViewTitle.setText(itemsData.get(position).EventName);
        viewHolder.txtViewLoca.setText(itemsData.get(position).Location);
        String date=itemsData.get(position).Date;
        try {
            Date datee=new SimpleDateFormat("dd/MM/YY").parse(date);
            viewHolder.eventDate.setText(datee.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.txtViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,EventPage.class);
                ModelEvent modelEvent=itemsData.get(position);

                String GroupId=groupId;
                intent.putExtra("ModelEvent",modelEvent);
                intent.putExtra("GroupId",GroupId);
                intent.putExtra("EventId",eventId.get(position));
                context.startActivity(intent);






            }
        });


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public TextView txtViewLoca;
        public TextView eventDate;




        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.EventName2);
            txtViewLoca = (TextView) itemLayoutView.findViewById(R.id.EventLoca);
            eventDate=(TextView)itemLayoutView.findViewById(R.id.EventDate);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }




}
