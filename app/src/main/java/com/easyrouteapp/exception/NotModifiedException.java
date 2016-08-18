package com.easyrouteapp.exception;

/**
 * Created by fernando on 11/08/2016.
 */
public class NotModifiedException extends ConnectionServiceException {

    public NotModifiedException(String message) {
        super(message);
    }

    public NotModifiedException(String message, Throwable e) {
        super(message, e);
    }

}
