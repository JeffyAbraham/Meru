package com.example.meru;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    LoginButton loginButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("UserData");
    private FirebaseAuth mAuth;
  CallbackManager callbackManager = CallbackManager.Factory.create();
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {

            Toast.makeText(getApplicationContext(),"MAINScreen",Toast.LENGTH_SHORT).show();
        }

    }










    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.login_button);
        TextView register = (TextView)findViewById(R.id.lnkRegister);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                 handleFacebookAccessToken(loginResult.getAccessToken());

                Intent intent = new Intent(MainActivity.this, HomeBottom.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();

            }
        });

    }

   private void handleFacebookAccessToken(AccessToken token) {
       Log.i("ACCTOKr",token.toString());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            final FirebaseUser user = mAuth.getCurrentUser();



                            Users users=new Users(Profile.getCurrentProfile().getName(),mAuth.getCurrentUser().getUid(),Profile.getCurrentProfile().getId());
                            myRef.child("users").child(user.getUid()).setValue(users);

                            Client client=new Client("71A6VCVSZ2","b6977c6f8abdb27b518d8e4a84ce0f15");
                            Index index=client.getIndex("UserData");
                            try {
                                Log.i("TRIED","WELLGUESS");
                                JSONObject object = new JSONObject()
                                        .put("Username",users.username).put("FacebookId",users.FacebookId);
                                index.addObjectAsync(object, mAuth.getUid(), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("MSG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void SignUser (View v)//Firebase Auth
    {
        EditText email=findViewById(R.id.LoginName);
        EditText password=findViewById(R.id.LoginPassword);
        String mailId=email.getText().toString().trim();
        String pwd=password.getText().toString();
        mAuth.signInWithEmailAndPassword(mailId, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                           Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                           Intent i=new Intent(getApplicationContext(),HomeBottom.class);
                           startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("K", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });




    }


}
