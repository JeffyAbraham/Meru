package com.example.meru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class PlacesGoogle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_google);
        final String Group=getIntent().getExtras().getString("Group");
        final String Description=getIntent().getExtras().getString("Description");


        String apiKey="AIzaSyDdRTwseRbn0bic8G3-3DXYH0Q4jYIapoY";
        Places.initialize(getApplicationContext(), apiKey);

                AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
                autocompleteFragment.getView().setBackgroundColor(Color.rgb(216, 27, 96));
                autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
                autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        // TODO: Get info about the selected place.
                        Log.i("GoogleMessage", "Place: " + place.getName() + ", " + place.getId());
                        Intent intent=new Intent(PlacesGoogle.this,AddGroupActivity.class).putExtra("EventLocation",place.getName());
                        intent.putExtra("Group",Group);
                        intent.putExtra("Description",Description);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onError(Status status) {
                        // TODO: Handle the error.
                        Log.i("GoogleMessage", "An error occurred: " + status);
                    }
                });




        }

    }
