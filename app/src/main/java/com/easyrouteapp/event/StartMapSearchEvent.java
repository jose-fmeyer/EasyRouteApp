package com.easyrouteapp.event;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by fernando on 14/08/2016.
 */
public class StartMapSearchEvent {

    private LatLng coordinates;

    public StartMapSearchEvent(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }
}
