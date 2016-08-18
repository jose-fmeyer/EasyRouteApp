package com.easyrouteapp.exception;

/**
 * Created by fernando on 11/08/2016.
 */
public class ForbiddenException extends ConnectionServiceException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable e) {
        super(message, e);
    }

}
