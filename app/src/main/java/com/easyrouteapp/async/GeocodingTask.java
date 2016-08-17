package com.easyrouteapp.async;

import android.content.Context;
import android.os.AsyncTask;

import com.easyrouteapp.event.GeoCodeErrorEvent;
import com.easyrouteapp.event.GeoCodeLoadedEvent;
import com.easyrouteapp.event.ReverseGeoCodeLoadedEvent;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;
import com.easyrouteapp.helper.GeocoderHelper;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class GeocodingTask extends AsyncTask<String, Void, LatLng> {

    private Context context;

    public GeocodingTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        EventBus.getDefault().post(new RefreshStartLoadingEvent());
    }

    @Override
    protected LatLng doInBackground(String... params) {
        try {
            if(!GeocoderHelper.isGeocoderAvailable()){
                EventBus.getDefault().post(new GeoCodeErrorEvent("Geo Code Service is not enable."));
                return null;
            }
            double[] coordinates = GeocoderHelper.doGeocoding(context, params[0]);
            return new LatLng(coordinates[0], coordinates[1]);
        } catch (final IOException e) {
            EventBus.getDefault().post(new GeoCodeErrorEvent("Error on execute reverse geo code", e));
        }
        return null;
    }

    @Override
    protected void onPostExecute(LatLng address) {
        EventBus.getDefault().post(new GeoCodeLoadedEvent(address));
        EventBus.getDefault().post(new RefreshStopEvent());
    }
}
