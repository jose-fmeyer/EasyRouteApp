package com.easyrouteapp.event;

/**
 * Created by fernando on 12/08/2016.
 */
public class ReverseGeoCodeLoadedEvent {

    private String address;

    public ReverseGeoCodeLoadedEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
