package com.example.meru;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.List;



public class JoinedUserImageAdapter extends RecyclerView.Adapter<JoinedUserImageAdapter.ViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<String> itemsData;
    Context context;





    public JoinedUserImageAdapter(List<String> itemsData, Context context) {
        this.itemsData = itemsData;
        this.context=context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public JoinedUserImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imagerow, null);

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
                            Picasso.get().load(url).into(viewHolder.userImage);
                        }
                        catch(Exception exception)
                        {

                            Picasso.get().load("https://graph.facebook.com/" + data.child("FacebookId").getValue() + "/picture?type=large").into(viewHolder.userImage);

                        }





                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


















    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView userImage;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            userImage=(ImageView) itemLayoutView.findViewById(R.id.joineduserimage);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }




}
