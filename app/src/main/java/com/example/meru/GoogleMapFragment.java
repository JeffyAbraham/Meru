package com.example.meru;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GoogleMapFragment extends Fragment  {

    GoogleMap mGooglMap;
    MapView mapView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_google_map, container, false);

        if(Geocoder.isPresent()){
            final String Location=getArguments().getString("Location");
            final String EventName=getArguments().getString("EventName");
            try {



                String location = Location;
                Geocoder gc = new Geocoder(getContext());
                List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects

                final List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                    }
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(ll.get(0).latitude,ll.get(0).longitude))
                                    .title(Location)
                                    .snippet(EventName));

                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(ll.get(0).latitude,ll.get(0).longitude)));


                        }
                    });
                }
            } catch (IOException e) {
                // handle the exception
            }
        }


        return rootView;
    }




}
