package com.kawnayeen.maptypes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button mapBtn;
    Button satelliteBtn;
    Button hybridBtn;
    boolean isMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapBtn = findViewById(R.id.mapBtn);
        satelliteBtn = findViewById(R.id.satelliteBtn);
        hybridBtn = findViewById(R.id.hybridBtn);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapBtn.setOnClickListener(v -> {
            if (isMapReady)
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        });
        satelliteBtn.setOnClickListener(v -> {
            if (isMapReady)
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        });
        hybridBtn.setOnClickListener(v -> {
            if (isMapReady)
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        isMapReady = true;
        LatLng cefaloDhaka = new LatLng(23.752629, 90.375684);
        CameraPosition target = CameraPosition.builder().target(cefaloDhaka).zoom(13).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }
}
