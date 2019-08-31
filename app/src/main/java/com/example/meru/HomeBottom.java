package com.example.meru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


public class HomeBottom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_bottom);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //ImageView userpicture = (ImageView)findViewById(R.id.profilepic);


        Fragment fragment=new NewEvent();
        fragmentTransaction.replace(R.id.Event,fragment);
        fragmentTransaction.commit();
      BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNav);
     bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

             switch ((menuItem.getItemId()))
             {
                 case R.id.notification:
                 {

                    showNotification();
                     break;
                 }
                 case R.id.home:
                 {
                    showHome();
                     break;
                 }
                 case  R.id.search:
                 {
                     Intent i=new Intent(HomeBottom.this,TabGroups.class);
                     startActivity(i);
                     break;
                 }

                 case R.id.message:
                 {
                   Intent intent=new Intent(HomeBottom.this,SearchChatUsers.class);
                   startActivity(intent);
                   break;
                 }
             }
                    return true;
         }
     });


    }

    //
    void showHome()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment=new NewEvent();
        fragmentTransaction.replace(R.id.Event,fragment);
        fragmentTransaction.commit();
    }
    void  showNotification()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment=new All();
        fragmentTransaction.replace(R.id.Event,fragment);
        fragmentTransaction.commit();
    }

}
