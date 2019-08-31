package com.example.meru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interest extends AppCompatActivity implements InterestsAdapter.ItemClickListener{

    ArrayList<String> Templist=new ArrayList<String>();
    InterestsAdapter adapter;
    List<String> data = Arrays.asList("Arts", "Language", "Paintings","Adventure","Food & Drinks","Babies","Football","Web Development","Hiking","Wine & Dine","Learn a new language","Music","Salsa","Middle East","Indian Culture","Expats","Running","Cycling","Workout","Calisthenics");


    // set up the RecyclerView
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        recyclerView=findViewById(R.id.RecycleInterest);

        final SearchView searchItem=findViewById(R.id.searchItem);
        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(s==null) {

                    List<String> datas = Arrays.asList(".");
                    callrecycle(datas);
                }
               else
                {
                    callrecycle(data);
                    adapter.getFilter().filter(s);
                }

                return false;
            }
        });
        TextView interest= findViewById(R.id.selector);
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"SELECTED",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("InterestList",Templist.toString() );
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        });


    }

    private void callrecycle(List<String> datas) {
        int numberOfColumns = 6;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new InterestsAdapter(this, datas);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        Templist.add(adapter.getItem(position));
        System.out.println(Templist);
        TextView intrestlist=findViewById(R.id.interestList);
        intrestlist.append("  "+adapter.getItem(position));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toast.makeText(getApplicationContext(),"MENUCREWTE",Toast.LENGTH_SHORT).show();
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.intrestmenu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);

        return true;

    }
}

