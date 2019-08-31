package com.example.meru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddGroupActivity extends AppCompatActivity {

    final   FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        final EditText location=findViewById(R.id.GoogleLocation);
        TextView interest= findViewById(R.id.Interests);
        EditText group=findViewById(R.id.group);

        String Eventlocation= getIntent().getStringExtra("EventLocation");
        String Description= getIntent().getStringExtra("Description");

        String Group= getIntent().getStringExtra("Group");

        if(Eventlocation!="")
        {
            location.setText(Eventlocation);
        }
        if(Group!="")
        {

           group.setText(Group);
        }
        if(Description!="")
        {
            EditText description=findViewById(R.id.description);

            description.setText(Description);
        }


        final CircleImageView profilepic=findViewById(R.id.profile_image);




        try {
            Picasso.get().load("https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large").into(profilepic);

        }
        catch (Exception exception)
        {

            final DatabaseReference myRef = database.getReference("UserData");
            Query query=myRef.child("users");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data:dataSnapshot.getChildren()){
                        if(mAuth.getUid().equals(data.getKey()))
                        {
                            String url=data.child("Profilepic").getValue().toString();
                            Picasso.get().load(url).into(profilepic);

                        }



                    }

                    }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            //Picasso.get().load("https://content-static.upwork.com/uploads/2014/10/02123010/profilephoto_goodcrop.jpg").into(profilepic);
        }



        TextView cancel= (TextView)findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGroupActivity.this, HomeBottom.class);
                startActivity(intent);
            }
        });

        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText group=findViewById(R.id.group);
                EditText description=findViewById(R.id.description);


                Intent intent = new Intent(AddGroupActivity.this,Interest.class);
               startActivityForResult(intent,1);

            }
        });

    }

    public void showLocation(View v) {   //shows googlemaps location
        EditText group=findViewById(R.id.group);
        EditText description=findViewById(R.id.description);

        Intent intent = new Intent(this, PlacesGoogle.class);
        intent.putExtra("Group", group.getText().toString());
        intent.putExtra("Description",description.getText().toString());
        startActivity(intent);
    }
    public void PostDataFireBase(View v) { //writes data to firebase
        Toast.makeText(getApplicationContext(),"Button press",Toast.LENGTH_SHORT).show();
        EditText group=findViewById(R.id.group);
        EditText description=findViewById(R.id.description);
        TextView location=findViewById(R.id.GoogleLocation);
        TextView interets=findViewById(R.id.Interests);


        FireBaseUserData fireBaseUserData=new FireBaseUserData();

        fireBaseUserData.getUserInfoandPost(group.getText().toString(),description.getText().toString(),location.getText().toString(),interets.getText().toString());
        Intent i=new Intent(AddGroupActivity.this,HomeBottom.class);
        startActivity(i);
        //JoinedPeople.put(firebaseAuth.getCurrentUser().getUid(),firebaseAuth.getCurrentUser().getDisplayName());


      // Groups groups=new Groups(group.getText().toString(),firebaseAuth.getCurrentUser().getUid(),description.getText().toString(),location.getText().toString(),interets.getText().toString(),);
    }







    public void onActivityResult(int requestCode, int resultCode, Intent data) {                  //after data is sent
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("InterestList");
                TextView interests=findViewById(R.id.Interests);
                interests.setText(strEditText.replace("[", "") .replace("]", "").trim());
                interests.setTextColor(Color.BLACK);

            }

        }

    }



}
