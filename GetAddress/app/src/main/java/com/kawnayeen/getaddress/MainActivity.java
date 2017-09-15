package com.kawnayeen.getaddress;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    Marker marker;
    Toolbar addressBar;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressBar = findViewById(R.id.toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        MarkerOptions selectedLocation = new MarkerOptions().position(googleMap.getCameraPosition().target).title("Selected Location");
        marker = googleMap.addMarker(selectedLocation);
        googleMap.setOnCameraMoveListener(() -> marker.setPosition(this.googleMap.getCameraPosition().target));
        googleMap.setOnCameraIdleListener(this::getLocation);
    }

    private void getLocation() {
        if (geocoder == null) {
            geocoder = new Geocoder(this, Locale.getDefault());
        }
        LatLng currentLocation = this.googleMap.getCameraPosition().target;
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 2); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String addressStr = "";
            if (addresses.size() == 0) {
                addressStr = "No address found";
            } else {
                for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                    if (i == 0) {
                        addressStr = addresses.get(0).getAddressLine(i);
                    } else {
                        addressStr += ", " + addresses.get(0).getAddressLine(i);
                    }
                }
                if (addresses.get(0).getMaxAddressLineIndex() > 1) {
                    addressStr = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1);
                } else {
                    addressStr = addresses.get(0).getAddressLine(0);
                }
            }
            addressBar.setTitle(addressStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
