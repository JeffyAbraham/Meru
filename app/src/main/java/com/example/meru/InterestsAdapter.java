package com.example.meru;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mDataListFull;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    InterestsAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mDataListFull=new ArrayList<>(mData);
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.interestitem, parent, false);

        view.setMinimumWidth(1000);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myTextView.setText(mData.get(position));











    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
       System.out.println("Filter"+"LIstststtstst");
        return filteredlist;
    }

    // stores and recycles views as they are scrolled off screen





    private  Filter filteredlist=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<String> filtredList=new ArrayList<>();
           if(constraint==null|| constraint.length()==0)
           {
               filtredList.addAll(mDataListFull);
           }
           else
           {
               String filterPattern=constraint.toString().toLowerCase();
               for(String item:mDataListFull)
               {
                   if(item.toLowerCase().toLowerCase().startsWith(filterPattern))
                   {
                       filtredList.add(item);
                   }
               }
           }
           FilterResults results=new FilterResults();
           results.values=filtredList;
           return  results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
               mData= new ArrayList<>();
                System.out.println(filterResults.values);
                mData.addAll((List)filterResults.values );
                notifyDataSetChanged();



        }
    };
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myTextView=view.findViewById(R.id.info_text);
            myTextView.setBackgroundResource(R.drawable.onclickback);
            myTextView.setTextColor(Color.WHITE);

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {

        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

