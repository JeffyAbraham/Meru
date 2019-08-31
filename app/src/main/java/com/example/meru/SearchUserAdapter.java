package com.example.meru;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<String> itemsData;
    Context context;






    public SearchUserAdapter(List<String> itemsData, Context context) {
        this.itemsData = itemsData;
        this.context=context;

        Log.i("ITEM",itemsData.toString());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchuser, null);

        // create ViewHolder


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {



        final DatabaseReference myRef = database.getReference("UserData");
        Query query=myRef.child("users");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    if(itemsData.get(position).equals(data.getKey()))
                    {
                        try {
                            String url=data.child("Profilepic").getValue().toString();
                            Picasso.get().load(url).into(viewHolder.Profilepic);
                        }
                        catch(Exception exception)
                        {

                            Picasso.get().load("https://graph.facebook.com/" + data.child("FacebookId").getValue() + "/picture?type=large").into(viewHolder.Profilepic);

                        }


                        viewHolder.UserName.setText(data.child("username").getValue().toString());


                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        viewHolder.UserName.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    Intent i=new Intent(context,ChatApplication.class);
                    i.putExtra("reveierId",itemsData.get(position));
                    context.startActivity(i);
                    }
                }
        );


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView UserName;
        public ImageView Profilepic;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
           UserName = (TextView) itemLayoutView.findViewById(R.id.UserName);
           Profilepic=itemLayoutView.findViewById(R.id.profilepicUser);


        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }




}

