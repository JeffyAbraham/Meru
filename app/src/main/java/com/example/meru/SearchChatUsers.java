package com.example.meru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchChatUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat_users);
        final EditText search=findViewById(R.id.searchUser);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.eventRecycleSearchUserList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);





        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Client client=new Client("71A6VCVSZ2","b6977c6f8abdb27b518d8e4a84ce0f15");
                Index index = client.getIndex("UserData");
                Query query = new Query(search.getText().toString())
                        .setAttributesToRetrieve("UserName")
                        .setHitsPerPage(50);
                index.searchAsync(query, new CompletionHandler() {
                    @Override
                    public void requestCompleted(JSONObject content, AlgoliaException error) {
                        try{

                            JSONArray hits=content.getJSONArray("hits");
                            List<String> list=new ArrayList<>();

                            for(int i=0;i<hits.length();i++)
                            {
                                JSONObject jsonObject=hits.getJSONObject(i);

                                String UserId=jsonObject.getString("objectID");
                                list.add(UserId);


                            }

                            SearchUserAdapter searchAdapter=new SearchUserAdapter(list,getApplicationContext());

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

