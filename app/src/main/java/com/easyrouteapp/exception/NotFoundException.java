package com.easyrouteapp.exception;

/**
 * Created by fernando on 11/08/2016.
 */
public class NotFoundException extends ConnectionServiceException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable e) {
        super(message, e);
    }

}
