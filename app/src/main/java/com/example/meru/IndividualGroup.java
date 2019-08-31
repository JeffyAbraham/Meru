package com.example.meru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.util.Log.i;

public class IndividualGroup extends AppCompatActivity {
    Button choose;
    ImageView coverImage;
    Button upload;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private Uri mImageUri;
    private StorageTask mUploadTask;
    TextView Groupname;
    TextView Location;
    TextView Description;
    ImageView OrganizerImage;
    Spinner  settings;
    Button join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Intent i = getIntent();
      final Groups groups = (Groups) i.getSerializableExtra("GroupData");
      final String groupId=i.getStringExtra("GroupId");
        final DatabaseReference myRef = database.getReference("GroupCoverImage");
        final DatabaseReference userRef=database.getReference("JoinedGroup").child(groupId);
        final DatabaseReference UserRef = database.getReference("UserData");
        final DatabaseReference groupRef=database.getReference("GroupData");
        final DatabaseReference joinedGroupRef=database.getReference("JoinedGroup");
        final DatabaseReference EventRef=database.getReference("Events").child(groupId);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouppage);
        Groupname=findViewById(R.id.GroupNamePage);
        Description=findViewById(R.id.descriptionSingle);
        Description.setText(groups.Description);
        Location=findViewById(R.id.location);
        Location.setText(groups.Location);
        Groupname.setText(groups.GroupName);
        upload=findViewById(R.id.uploadImage);
        choose=findViewById(R.id.pickImage);
        coverImage=findViewById(R.id.CoverImage);
        OrganizerImage=findViewById(R.id.organizerImage);
        settings=findViewById(R.id.planets_spinner);
        try {
            join=findViewById(R.id.jo);
            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    joinedGroupRef.child(groupId).child(mAuth.getUid()).setValue(mAuth.getCurrentUser().getEmail());
                    UserRef.child("users").child(mAuth.getUid()).child("Joiners").child(groupId).setValue(groups);
                    join.setText("you are now member of this group");
                    join.setAllCaps(false);
                }
            });
        }
        catch (Exception e)
        {

        }


     groupRef.child(groupId);

//remove ne
       groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot das:dataSnapshot.getChildren())
               {
                    
                   if(das.child("OrganizerId").getValue().equals(mAuth.getUid()))
                   {

                       Spinner spinner=findViewById(R.id.planets_spinner);
                       spinner.setVisibility(View.VISIBLE);
                   }

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.EventRecycle);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);





        EventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<ModelEvent> list=new ArrayList<>();
                List<String> eventId=new ArrayList<>();

                for (DataSnapshot data:dataSnapshot.getChildren()){



                    Map<String,String> GoingUsers=new HashMap<String, String>();

                    String EventName=data.child("EventName").getValue().toString();
                    String Organizer=data.child("Organizer").getValue().toString();
                    String Date=data.child("Date").getValue().toString();
                    String Location=data.child("Location").getValue().toString();
                    String Descritption=data.child("Description").getValue().toString();
                    String GroupName=data.child("GroupName").getValue().toString();

                     for(DataSnapshot dataSnapshot1:data.child("GoingUsers").getChildren())
                     {
                         GoingUsers.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
                         eventId.add(data.getKey());

                     }

                    ModelEvent modelEvent=new ModelEvent(EventName,Organizer,Descritption,Location,GoingUsers,Date,GroupName);



                    list.add(modelEvent);
                    Log.i("VND",list.toString());
                    //ModelEvent modelEvent=ModelEvent();



                }
                RecycleGroupAdapter mAdapter = new RecycleGroupAdapter(list,getApplicationContext(),groupId,eventId);

                recyclerView.setAdapter(mAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                i("Fire",databaseError.toString());

            }
        });








        try {
            Picasso.get().load("https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large").into(OrganizerImage);

        }
        catch (Exception exception)
        {
            Picasso.get().load("https://content-static.upwork.com/uploads/2014/10/02123010/profilephoto_goodcrop.jpg").into(OrganizerImage);
        }
        storageReference= FirebaseStorage.getInstance().getReference("GroupCoverImage");
        databaseReference= FirebaseDatabase.getInstance().getReference("GroupCoverImage");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot data:dataSnapshot.getChildren()){

                    if(groupId.equals(data.getKey())) {
                        Picasso.get().load(data.getValue().toString()).into(coverImage);
                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                i("Fire",databaseError.toString());

            }
        });
        userRef.orderByKey().equalTo(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    Log.i("JoinedGroups", dataSnapshot.toString());
                    View a=findViewById(R.id.pickImage);
                        a.setVisibility(View.VISIBLE);
                    View c=findViewById(R.id.uploadImage);
                    c.setVisibility(View.VISIBLE);


                    View b = findViewById(R.id.jo);
                    b.setVisibility(View.GONE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("JOINED",databaseError.toString());

            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
          settings.setAdapter(adapter);
          settings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(i==1)
                    {
                        Intent intent=new Intent(IndividualGroup.this,EventHost.class);
                        intent.putExtra("GroupData",groups);
                        intent.putExtra("GroupId",groupId);
                        startActivity(intent);

                    }


              }

              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {

              }
          });













        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {

                    upoadFile(groupId);
                }
            }
        });



        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseFile();
            }
        });

    }

    private void chooseFile() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }
    @Override
    protected  void  onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && resultCode==RESULT_OK&& data !=null && data.getData()!=null)
        {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(coverImage);

        }
    }
    private  String geFileExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));



    }
    private  void upoadFile(final String groupId)
    {
        if(mImageUri!=null)
        {


            final StorageReference fileReference=storageReference.child(groupId+"." +geFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                            String Url=uri.toString();
                            databaseReference.child(groupId).setValue(Url);



                            }
                        });

                }


            });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No file selected",Toast.LENGTH_SHORT).show();
        }
    }
}
