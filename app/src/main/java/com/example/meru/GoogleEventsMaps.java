package com.example.meru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class GoogleEventsMaps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_events_maps);


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

                Intent intent = new Intent();
                intent.putExtra("EventLocation",place.getName() );
                setResult(RESULT_OK, intent);
                onBackPressed();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("GoogleMessage", "An error occurred: " + status);
            }
        });



    }
}
