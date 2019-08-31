package com.example.meru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileUpload extends AppCompatActivity {
    private StorageTask mUploadTask;
    ImageView choose;
    private Uri mImageUri;
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    final DatabaseReference myRef = database.getReference("UserData").child("users");
    final StorageReference sref=firebaseStorage.getReference("ProfileImage");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_upload);

        Button upload=findViewById(R.id.upploadProfilepic);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {

                    upoadFile();
                    Intent i=new Intent(ProfileUpload.this,HomeBottom.class);
                    startActivity(i);
                }
            }
        });


        choose=findViewById(R.id.pickProfilepic);
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
            Picasso.get().load(mImageUri).into(choose);

        }
    }
    private  String geFileExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));



    }
    private  void upoadFile()
    {
        if(mImageUri!=null)
        {


            final StorageReference fileReference=sref.child(mAuth.getUid()+"." +geFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String Url=uri.toString();
                            myRef.child(mAuth.getUid()).child("Profilepic").setValue(Url);



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


