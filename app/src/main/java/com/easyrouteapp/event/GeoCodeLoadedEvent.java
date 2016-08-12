package com.easyrouteapp.event;

/**
 * Created by fernando on 12/08/2016.
 */
public class GeoCodeLoadedEvent {

    private String address;

    public GeoCodeLoadedEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
