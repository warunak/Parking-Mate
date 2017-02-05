package com.example.warunakavinda.parkingmate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import javax.xml.transform.Result;

public class reroute extends FragmentActivity implements OnMapReadyCallback {

    // implements OnMapReadyCallback

   private GoogleMap mMap;
    double lat ;
    double lng ;
    int availability;
    int result;
    String sliit_lat;
    String sliit_lng;
    String slots_availability;
    TextToSpeech ttsobject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acreroute);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        ttsobject = new TextToSpeech(reroute.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status)
            {
            if (status == TextToSpeech.SUCCESS)
            {
                result = ttsobject.setLanguage(Locale.US);
                if (result==TextToSpeech.LANG_NOT_SUPPORTED ||result==TextToSpeech.LANG_MISSING_DATA)
                {
                    Toast.makeText(getApplicationContext(),"Not Supported In Your Drvice",Toast.LENGTH_SHORT).show();
                }
                else
                {
                   // speak();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Not Supported In Your Drvice",Toast.LENGTH_SHORT).show();
            }
            }
        });

        Intent intent = getIntent();
        sliit_lat = intent.getStringExtra("lat");
        sliit_lng = intent.getStringExtra("lng");
        slots_availability = intent.getStringExtra("ava");

        lat=Double.parseDouble(sliit_lat);
        lng=Double.parseDouble(sliit_lng);
        availability=Integer.parseInt(slots_availability);

        Toast.makeText(getApplicationContext()," "+slots_availability+" ",Toast.LENGTH_SHORT).show();
    }

    public void speak()
    {
        if (availability==0)
        {
            ttsobject.speak("There is no slots available. rerouting to another place", TextToSpeech.QUEUE_FLUSH, null);
        }

          else
        {
            if(availability==1) {
                ttsobject.speak("There is only " + slots_availability + " slot available", TextToSpeech.QUEUE_FLUSH, null);
            }

            else{
                ttsobject.speak("There is only " + slots_availability + " slots available", TextToSpeech.QUEUE_FLUSH, null);
            }

            }

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (ttsobject != null)
        {
            ttsobject.stop();
            ttsobject.shutdown();
        }
    }

    @Override
   public void onMapReady(GoogleMap googleMap)
    {
        if (availability==0)
        {
//            lat=6.915893;
//            lng=79.973039;
            mMap = googleMap;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.915893, 79.973039), 12.0f));
            mMap.addMarker(new MarkerOptions().position(new LatLng(6.915893, 79.973039)).title("Marker in SLIIT"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(6.915893, 79.973039)));
            setUpMapIfNeeded(googleMap);
        }
        else
        {

            mMap = googleMap;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12.0f));
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker in SLIIT"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
            setUpMapIfNeeded(googleMap);
        }
    }

    public void setUpMapIfNeeded(GoogleMap googleMap)
    {
        // Do a null check to confirm that we have not already instantiated the map.
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //ok

        mMap.setMyLocationEnabled(true);
        // Check if we were successful in obtaining the map.
        if (mMap != null)
        {

            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener()
            {
                @Override
                public void onMyLocationChange(Location arg0)
                {

                    CircleOptions circleSLIIT = new CircleOptions().center(new LatLng(lat,lng)).radius(1000).strokeColor(Color.BLUE);// In meters
                    // Get back the mutable Circle
                    Circle circle = mMap.addCircle(circleSLIIT);
                    //Current postion
                    LatLng current = new LatLng(arg0.getLatitude(), arg0.getLatitude());
                    Date date = new Date();
                    mMap.addMarker(new MarkerOptions()
                                    .title("Current Pos")
                                    .snippet(new Timestamp(date.getTime()).toString())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                    .position(current)
                    );
                    float[] distance = new float[2];
                    Location.distanceBetween(arg0.getLatitude(),arg0.getLongitude(),circle.getCenter().latitude,circle.getCenter().longitude,distance);

                    if ( distance[0] <= circle.getRadius())
                    {
                        speak();
                        //Toast.makeText(getApplicationContext(), "You are in Inside the Circle", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        //Toast.makeText(getApplicationContext(),"You are in outside the Circle",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

}
