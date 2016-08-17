package com.easyrouteapp;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easyrouteapp.async.ReverseGeocodingTask;
import com.easyrouteapp.event.GeoCodeErrorEvent;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private static final String TAG_LOG = "[MapActivity]";
    public static final String EXTRA_DEFAULT_MAP_NAME = "EXTRA_DEFAULT_MAP_NAME";
    private LatLng defaultCoordinates;
    private GoogleMap googleMap;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        EventBus.getDefault().register(MapActivity.this);
        progressBar = (ProgressBar) findViewById(R.id.progress_map);
        setDefaultCoordinates();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setOnMapLongClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultCoordinates, 12));
    }

    @Subscribe
    public void onLoadDataError(GeoCodeErrorEvent event) {
        Log.e(TAG_LOG, event.getError().getMessage(), event.getError());
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartRefresh(RefreshStartLoadingEvent event) {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStopRefresh(RefreshStopEvent event) {
        progressBar.setVisibility(View.GONE);
        finish();
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.msg_use_coordinates));
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.label_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                new ReverseGeocodingTask(getApplicationContext()).execute(latLng.latitude, latLng.longitude);
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
}
