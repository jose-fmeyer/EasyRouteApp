package com.easyrouteapp.event;

/**
 * Created by fernando on 11/08/2016.
 */
public class GeoCodeErrorEvent {

    private String message;
    private Throwable error;

    public GeoCodeErrorEvent(String message) {
        this.message = message;
    }

    public GeoCodeErrorEvent(String message, Throwable error) {
        this.message = message;
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
