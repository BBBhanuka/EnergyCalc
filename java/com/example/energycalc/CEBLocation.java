package com.example.energycalc;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.energycalc.databinding.ActivityCeblocationBinding;


import java.util.List;

public class CEBLocation extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 101;
    double latitude, longitude;
    private GoogleMap mMap;
    private ActivityCeblocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCeblocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (isLocationPermissionGranted()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } else {
            requestLocationPermission();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                // Update the marker on the map with the new location
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                //declare some locations of CEB Consumer care centers
                LatLng locationLatlng = new LatLng(6.840734914819275, 79.90048797757849);
                LatLng ceb_primary_substation = new LatLng(6.846179443982216, 79.86692499175776);
                LatLng consumer_Rathmalana = new LatLng(6.841485216030158, 79.86722066028028);
                LatLng bill_payment_center = new LatLng(6.865727027349849, 79.87737406303715);
                LatLng consumer_thalangama = new LatLng(6.894701041103509, 79.92496826958705);
                LatLng consumer_kesbawa = new LatLng(6.801642627558655, 79.9414477611501);


                // Add marker to map
                mMap.clear(); // Clear existing markers
                mMap.addMarker(new MarkerOptions().position(locationLatlng).title("CEB Consume-Care"));
                mMap.addMarker(new MarkerOptions().position(ceb_primary_substation).title("CEB Primary Substation"));
                mMap.addMarker(new MarkerOptions().position(consumer_Rathmalana).title("CEB Consume-Care Rathmalana"));
                mMap.addMarker(new MarkerOptions().position(bill_payment_center).title("Ceylon Electricity Board- Consumer Service Centre / Electricity Bill Payment Counter"));
                mMap.addMarker(new MarkerOptions().position(consumer_thalangama).title("CEB Thalangama Consumer Service Center"));
                mMap.addMarker(new MarkerOptions().position(consumer_kesbawa).title("Electricity Board - Kesbewa Consumer Services Center"));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {


            mMap = googleMap;
            mMap.setMyLocationEnabled(true); // Enable My Location button
            mMap.getUiSettings().setMyLocationButtonEnabled(true); // Enable My Location layer

            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    LatLng current_location = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(current_location).title("My Location"));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    mMap.getUiSettings().setCompassEnabled(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 15)); // Move camera to new location
                    mMap.getUiSettings().setCompassEnabled(true);

                    return true;
                }
            });
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
    }
    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }


}