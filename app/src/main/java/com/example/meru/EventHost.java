package com.example.meru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.util.Log.i;

public class EventHost extends AppCompatActivity {



    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final DatabaseReference myRef = database.getReference("GroupCoverImage");

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_host);
        Intent i = getIntent();
        final EditText eventName=findViewById(R.id.EventName);
        final EditText eventDescritpion=findViewById(R.id.Eventdescription);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        final TextView eventLocation=findViewById(R.id.eventLocation);

        final Groups groups = (Groups) i.getSerializableExtra("GroupData");
        final String groupId=i.getStringExtra("GroupId");
        TextView name=findViewById(R.id.ParentG);
        name.setText(groups.GroupName);
        final DatabaseReference eventRef=database.getReference("Events").child(groupId);
        final ImageView groupLogo=findViewById(R.id.joinedgroupImage);





        databaseReference= FirebaseDatabase.getInstance().getReference("GroupCoverImage");


        Button eventcreator=findViewById(R.id.eventcreator);
        eventcreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Event has been created",Toast.LENGTH_LONG).show();
                Map<String,String> GoingUsers=new HashMap<String, String>();
                GoingUsers.put(groups.OrganizerId,groups.OrganizerId);
                final ModelEvent modelEvent=new ModelEvent(eventName.getText().toString(),groups.OrganizerId,eventDescritpion.getText().toString(),eventLocation.getText().toString(),GoingUsers,mDisplayDate.getText().toString(),groups.GroupName);
               final String EvenID=UUID.randomUUID().toString();
                eventRef.child(EvenID).setValue(modelEvent);
                Thread thread = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {

                            Client client=new Client("71A6VCVSZ2","b6977c6f8abdb27b518d8e4a84ce0f15");
                            Index index=client.getIndex("EventName");
                            JSONObject object = new JSONObject()
                                    .put("EventId",EvenID)
                                    .put("EventName", modelEvent.EventName)
                                    .put("Location", modelEvent.Location)
                                    .put("Description",modelEvent.Description)
                                    .put("ImageLinke",groupLogo)
                                    .put("Date",modelEvent.Date)
                                    .put("GoingUsers",modelEvent.GoingUsers).put("GroupName",modelEvent.GroupName).put("Organizer",modelEvent.Organizer)
                                    ;

                            index.addObjectAsync(object, groupId, null);

                        } catch (Exception e) {

                        }
                    }
                });
                thread.start();

                final DatabaseReference joinedGroupRef=database.getReference("UserData").child("users");
                joinedGroupRef.child(mAuth.getUid()).child("Joiners").child(groupId).child("JoinedEvents").child(EvenID).setValue(modelEvent.EventName);
                Intent i=new Intent(EventHost.this,EventPage.class);
                i.putExtra("ModelEvent",modelEvent);
                i.putExtra("GroupId",groupId);
                i.putExtra("EventId",EvenID);
                startActivity(i);


            }



        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot data:dataSnapshot.getChildren()){


                    if(groupId.equals(data.getKey())) {
                        Picasso.get().load(data.getValue().toString()).into(groupLogo);

                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                i("Fire",databaseError.toString());

            }
        });













        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EventHost.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
    }

    public void showLocation(View v) {   //shows googlemaps location

        Intent intent = new Intent(EventHost.this, GoogleEventsMaps.class);
        startActivityForResult(intent, 1);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {                  //after data is sent
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("EventLocation");
                TextView eventLocation=findViewById(R.id.eventLocation);
                eventLocation.setText(strEditText);


            }

        }

    }
}

