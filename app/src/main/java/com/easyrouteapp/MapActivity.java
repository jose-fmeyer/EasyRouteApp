package com.easyrouteapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.easyrouteapp.event.GeoCodeErrorEvent;
import com.easyrouteapp.event.StartMapSearchEvent;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMapLongClickListener {
    private static final String TAG_LOG = "[MapActivity]";
    public static final String EXTRA_DEFAULT_MAP_NAME = "EXTRA_DEFAULT_MAP_NAME";
    private LatLng defaultCoordinates;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        EventBus.getDefault().register(MapActivity.this);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setDefaultCoordinates();
    }

    private void setDefaultCoordinates() {
        if(getIntent().getExtras() != null){
            this.defaultCoordinates = (LatLng) getIntent().getExtras().get(EXTRA_DEFAULT_MAP_NAME);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMapLoadedCallback(this);
    }

    private CameraUpdate getDefaultCameraUpdatePosition() {
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(defaultCoordinates).zoom(12);
        CameraPosition cameraPos = builder.build();
        return CameraUpdateFactory.newCameraPosition(cameraPos);
    }

    @Subscribe
    public void onLoadDataError(GeoCodeErrorEvent event) {
        Log.e(TAG_LOG, event.getError().getMessage(), event.getError());
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.msg_use_coordinates));
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.label_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                EventBus.getDefault().post(new StartMapSearchEvent(latLng));
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.label_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }

    @Override
    public void onMapLoaded() {
        if(defaultCoordinates != null){
            googleMap.animateCamera(getDefaultCameraUpdatePosition());
        }
    }
}
