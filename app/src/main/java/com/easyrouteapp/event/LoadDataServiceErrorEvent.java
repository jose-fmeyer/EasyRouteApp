package com.easyrouteapp.event;

/**
 * Created by fernando on 11/08/2016.
 */
public class LoadDataServiceErrorEvent {

    private String message;
    private Throwable error;

    public LoadDataServiceErrorEvent(String message, Throwable error) {
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
