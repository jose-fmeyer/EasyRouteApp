package com.easyrouteapp.event;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by fernando on 12/08/2016.
 */
public class GeoCodeLoadedEvent {

    private LatLng latLng;

    public GeoCodeLoadedEvent(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
