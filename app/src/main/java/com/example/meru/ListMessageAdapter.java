package com.example.meru;

import android.content.Context;
import android.content.Intent;
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

public class ListMessageAdapter extends RecyclerView.Adapter<ListMessageAdapter.ViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<ModelChatMessage> itemsData;
    Context context;




    public ListMessageAdapter(List<ModelChatMessage> itemsData, Context context) {
        this.itemsData = itemsData;
        this.context=context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, null);

        // create ViewHolder


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        viewHolder.txtViewTitle.setText(itemsData.get(position).getMessageText());
        viewHolder.textViewUser.setText(itemsData.get(position).getMessageUser());

    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public TextView textViewUser;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.message_text);
            textViewUser=itemLayoutView.findViewById(R.id.message_user);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }




}

