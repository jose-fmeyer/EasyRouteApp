package com.easyrouteapp.async;

import android.content.Context;
import android.os.AsyncTask;

import com.easyrouteapp.event.GeoCodeErrorEvent;
import com.easyrouteapp.event.ReverseGeoCodeLoadedEvent;
import com.easyrouteapp.event.RefreshStartLoadingEvent;
import com.easyrouteapp.event.RefreshStopEvent;
import com.easyrouteapp.helper.GeocoderHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ReverseGeocodingTask extends AsyncTask<Double, Void, String> {

    private Context context;

    public ReverseGeocodingTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        EventBus.getDefault().post(new RefreshStartLoadingEvent());
    }

    @Override
    protected String doInBackground(Double... params) {
        try {
            return GeocoderHelper.doReverseGeocoding(context, params[0], params[1]);
        } catch (final IOException e) {
            EventBus.getDefault().post(new GeoCodeErrorEvent("Error on execute reverse geo code", e));
        }
        return null;
    }

    @Override
    protected void onPostExecute(String address) {
        EventBus.getDefault().post(new RefreshStopEvent());
        EventBus.getDefault().post(new ReverseGeoCodeLoadedEvent(address));
    }
}
