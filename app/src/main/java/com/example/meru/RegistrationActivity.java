package com.example.meru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    final   FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("UserData");
    String password;
    String username;




    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        TextView login = findViewById(R.id.lnkLogin);

        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public void RegUser (View v)
    {   final EditText username=findViewById(R.id.RegUser);
        EditText email=findViewById(R.id.LoginName);
        EditText password=findViewById(R.id.LoginPassword);
          String mailid=email.getText().toString();

          Toast.makeText(getApplicationContext(),mailid,Toast.LENGTH_SHORT).show();

        Log.i("FFFF",mailid);
        mAuth.createUserWithEmailAndPassword(mailid.trim(),password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Hey", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Users users=new Users(username.getText().toString(),user.getUid(),"NONE");

                            myRef.child("users").child(user.getUid()).setValue(users);
                            Client client=new Client("71A6VCVSZ2","b6977c6f8abdb27b518d8e4a84ce0f15");
                            Index index=client.getIndex("UserData");
                            try {
                                Log.i("TRIED","WELLGUESS");
                                JSONObject object = new JSONObject()
                                        .put("Username",users.username).put("FacebookId","NONE");
                                index.addObjectAsync(object, mAuth.getUid(), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                           Intent i=new Intent(getApplicationContext(),ProfileUpload.class);

                           startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("fail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Faileed",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


}