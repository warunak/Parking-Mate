package com.example.warunakavinda.parkingmate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class rerout_to_eng extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rerout_to_eng);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    double lat = 6.915906;
    double lng = 79.973070;
    LatLng SLIIT_eng = new LatLng(lat,lng);

    @Override
    public void onMapReady(GoogleMap googleMap)
    {


        mMap = googleMap;
        // Add a marker in SLIIT

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SLIIT_eng, 12.0f));
        mMap.addMarker(new MarkerOptions().position(SLIIT_eng).title("Marker in SLIIT"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SLIIT_eng));
        setUpMapIfNeeded(googleMap);
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

                    CircleOptions circleSLIIT = new CircleOptions().center(SLIIT_eng).radius(1000).strokeColor(Color.BLUE);// In meters
                    // Get back the mutable Circle
                    Circle circle = mMap.addCircle(circleSLIIT);

                    mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                    float[] distance = new float[2];
                    Location.distanceBetween(arg0.getLatitude(),arg0.getLongitude(),circle.getCenter().latitude,circle.getCenter().longitude,distance);
                    if ( distance[0] <= circle.getRadius())
                    {
                        System.out.println("INside");
                        Toast.makeText(getApplicationContext(), "You are in Inside the Circle", Toast.LENGTH_SHORT).show();
                        //sp.Speech();




                    }
                    else
                    {
                        System.out.println("OUTside");
                        Toast.makeText(getApplicationContext(),"You are in outside the Circle",Toast.LENGTH_SHORT).show();
                        //sliitpark slp = new sliitpark();

                    }
                }
            });

        }

    }


}
