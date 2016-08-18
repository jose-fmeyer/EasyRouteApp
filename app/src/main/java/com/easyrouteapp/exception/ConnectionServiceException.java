package com.easyrouteapp.exception;

/**
 * Created by fernando on 11/08/2016.
 */
public class ConnectionServiceException extends Exception {

    public ConnectionServiceException(String message) {
        super(message);
    }

    public ConnectionServiceException(String message, Throwable e) {
        super(message, e);
    }

}
