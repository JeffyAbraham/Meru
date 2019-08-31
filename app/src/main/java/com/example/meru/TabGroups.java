package com.example.meru;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TabGroups extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TabGroups.this, HomeBottom.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchevent);
        final EditText search=findViewById(R.id.searchEvent);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.eventRecycleSearchList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);





        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Client client=new Client("71A6VCVSZ2","b6977c6f8abdb27b518d8e4a84ce0f15");
                Index index = client.getIndex("EventName");
                Query query = new Query(search.getText().toString())
                        .setAttributesToRetrieve("EventName", "Description","Organizer","Date","Location","GroupName","objectID","EventId")
                        .setHitsPerPage(50);
                index.searchAsync(query, new CompletionHandler() {
                    @Override
                    public void requestCompleted(JSONObject content, AlgoliaException error) {
                     try{

                         JSONArray hits=content.getJSONArray("hits");
                         List<ModelEvent> list=new ArrayList<>();
                         List<String> groupId=new ArrayList<>();
                         List<String> EventId=new ArrayList<>();

                         for(int i=0;i<hits.length();i++)
                         {
                             JSONObject jsonObject=hits.getJSONObject(i);
                             String  EventI=jsonObject.getString("EventId");
                             String EventName=jsonObject.getString("EventName");
                             String GroupId=jsonObject.getString("objectID");
                             Log.i("JSON",GroupId);
                             String Organizer=jsonObject.getString("Organizer");
                             String Date=jsonObject.getString("Date");
                             String Location=jsonObject.getString("Location");
                             String Description=jsonObject.getString("Description");
                             String GroupName=jsonObject.getString("GroupName");
                             Map<String,String> GoingUsers=new HashMap<String, String>();
                             GoingUsers.put("S","S");




                             ModelEvent modelEvent=new ModelEvent(EventName,Organizer,Description,Location,GoingUsers,Date,GroupName);
                             list.add(modelEvent);
                             groupId.add(GroupId);
                             EventId.add(EventI);



                         }
                         Toast.makeText(getApplicationContext(),list.toString(),Toast.LENGTH_SHORT).show();
                       SearchAdapter searchAdapter=new SearchAdapter(list,getApplicationContext(),groupId,EventId);

                       recyclerView.setAdapter(searchAdapter);
                     }
                     catch (JSONException e)
                     {


                     }

                    }
                });
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });






        }
}